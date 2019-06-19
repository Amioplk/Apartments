package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableSortedMap;

/**
 * @author Amioplk, alexisperdereau
 * A class which enables to get a utility whose type is different between 0 and 1
 * Does only work with ? extends PartialValueFunction<Double>
 */
public class MultiPartialValueFunction implements PartialValueFunction<Double> {
	
	/**
	 * Represents the PartialValueFunctions used from the key
	 * A map of PartialValueFunctions 
	 * Must have a size which is the size of map - 1
	 */
	protected SortedMap<Double, PartialValueFunction<Double>> partials;
	protected ImmutableSortedMap<Double, Double> map;
	private final static Logger LOGGER = LoggerFactory.getLogger(PieceWiseLinearValueFunction.class);
	
	/**
	 * Builder of the MultiPartialValueFunction
	 * 
	 * @param parameters is the dictionary of all values along with the grades
	 *                   associated to them, In this map, if the collection of keys
	 *                   was sorted, then the collection of grades would also have
	 *                   to be sorted. There has to be a value associated to the
	 *                   grade 0, and another value associated to the grade 1.
	 */
	public MultiPartialValueFunction(Map<Double, Double> parameters, SortedMap<Double, PartialValueFunction<Double>> partials) {

		setPartials(partials);
		
		if (!parameters.containsValue(0d) || !parameters.containsValue(1d)) {
			throw new IllegalArgumentException("The value associated to the grade 0 or 1 is missing.");
		}

		Stream<Double> error = parameters.values().stream().filter(v -> v > 1d || v < 0d);
		if (error.count() > 0) {
			throw new IllegalArgumentException("The grades have to be between 0 and 1.");
		}

		map = ImmutableSortedMap.copyOf(parameters);
		if (!Comparators.isInOrder(this.map.values(), Comparator.naturalOrder())) {
			throw new IllegalArgumentException(
					"A grade cannot be greater than another if its value associated is lower.");
		}
		LOGGER.info("The map of data has been successfully instantiated.");
	}
	
	public SortedMap<Double, PartialValueFunction<Double>> getPartials() {
		return partials;
	}

	public void setPartials(SortedMap<Double, PartialValueFunction<Double>> partials) {
		this.partials = partials;
	}

	@Override
	public double getSubjectiveValue(Double objectiveData) throws IllegalArgumentException {
		
		Preconditions.checkNotNull(this.partials);
		Verify.verify(this.partials.size() == map.size() - 1);
		Verify.verify(map.size() >= 2); // Then partials.size() >= 1
		
		if (objectiveData <= map.firstKey()) {
			return 0d;
		}
		if (objectiveData >= map.lastKey()) {
			return 1d;
		}
		
		Double minKey = map.floorEntry(objectiveData).getKey();
		
		PartialValueFunction<Double> pvf = partials.getOrDefault(minKey, new ConstantValueFunction<Double>(0));
		
		return pvf.getSubjectiveValue(objectiveData);
	
	}

	@Override
	public Double apply(Double t) {
		return this.getSubjectiveValue(t);
	}
	
}