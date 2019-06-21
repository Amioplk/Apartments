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
import com.google.common.collect.Range;

/**
 * @author Amioplk, alexisperdereau
 * A class which enables to get a utility whose type is different between 0 and 1
 * Does only work with PartialValueFunction<Double>, not PartialValueFunction<T>
 */
public class MultiPartialValueFunction implements PartialValueFunction<Double> {
	
	/**
	 * Represents the PartialValueFunctions used in the key Range
	 * A map of PartialValueFunctions
	 */
	protected SortedMap<Range<Double>, PartialValueFunction<Double>> partials;
	/**
	 * Represents the subjectiveValues of the bounds
	 */
	protected ImmutableSortedMap<Double, Double> map;
	private final static Logger LOGGER = LoggerFactory.getLogger(PieceWiseLinearValueFunction.class);
	
	/**
	 * Constuctor of the MultiPartialValueFunction
	 * 
	 * @param parameters is the dictionary of all values along with the grades
	 *                   associated to them, In this map, if the collection of keys
	 *                   was sorted, then the collection of grades would also have
	 *                   to be sorted. There has to be a value associated to the
	 *                   grade 0, and another value associated to the grade 1.
	 */
	public MultiPartialValueFunction(Map<Double, Double> parameters, SortedMap<Range<Double>, PartialValueFunction<Double>> partials) {

		Preconditions.checkNotNull(partials);
		Preconditions.checkArgument(partials.size() >= 1);
		
		if(partials.comparator().compare(Range.closed(0d,1d), Range.closed(1d,2d)) >= 0) {
			throw new IllegalArgumentException("The comparator of the SortedMap is not valid for Range<Double,Double>.");
		}

		this.partials = partials;
		
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
		
		Verify.verify(partials.size() < map.size());
		
		LOGGER.info("The map of data has been successfully instantiated.");
	}
	
	public SortedMap<Range<Double>, PartialValueFunction<Double>> getPartials() {
		return partials;
	}

	public void setPartials(SortedMap<Range<Double>, PartialValueFunction<Double>> partials) {
		
		if(partials.firstKey().lowerEndpoint() != this.map.firstKey()) {
			LOGGER.debug("The partials could not be set correctly, missing data.");
			throw new IllegalArgumentException("The PartialValueFunction type used in the first interval is missing.");
		}
		if(partials.lastKey().upperEndpoint() != this.map.lastKey()) {
			LOGGER.debug("The partials could not be set correctly, missing data.");
			throw new IllegalArgumentException("The PartialValueFunction type used in the last interval is missing.");
		}
		
		this.partials = partials;
	}

	@Override
	public double getSubjectiveValue(Double objectiveData) {
		
		Preconditions.checkNotNull(this.partials);
		Verify.verify(map.size() >= 2);
		
		if (objectiveData <= map.firstKey()) {
			return 0d;
		}
		if (objectiveData >= map.lastKey()) {
			return 1d;
		}
		
		Double minKey = map.floorEntry(objectiveData).getKey();
		Double maxKey = map.ceilingEntry(objectiveData).getKey();
		
		PartialValueFunction<Double> partialValueFunction = new ConstantValueFunction<>();
		for(Range<Double> range: partials.keySet()) {
			if(range.contains(maxKey) && range.contains(minKey)) {
				partialValueFunction = partials.get(range);
			}
		}

		return partialValueFunction.getSubjectiveValue(objectiveData);
	
	}

	@Override
	public Double apply(Double t) {
		return this.getSubjectiveValue(t);
	}
	
}
