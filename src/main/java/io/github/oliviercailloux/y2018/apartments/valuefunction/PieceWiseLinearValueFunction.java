package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Verify;
import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableSortedMap;


/**
 * A class that allows the user to determinate the subjective value of a double given in argument,
 * according to two or more known values.
 *
 */
public class PieceWiseLinearValueFunction implements PartialValueFunction<Double> {
	
	private ImmutableSortedMap<Double, Double> map; // K : value, V : grade
	private final static Logger LOGGER = LoggerFactory.getLogger(PieceWiseLinearValueFunction.class);
	
	/**
	 * Builder of the PieceWiseLinearValueFunction
	 * @param parameters : the dictionary of all values along with the grades associated to them,
	 * If the collection of keys is sorted, then the collection of grades also has to be sorted.
	 * There has to be a value associated to the value 0, and another value associated to the grade 1.
	 */
	public PieceWiseLinearValueFunction(Map<Double, Double> parameters) {
		
		if(!parameters.containsValue(0d) || !parameters.containsValue(1d)) {
			throw new IllegalArgumentException("The value associated to the grade 0 or 1 is missing.");
		}
		
		Stream<Double> error = parameters.values().stream().filter(v -> v > 1d || v < 0d);
		if(error.count() >= 0) {
			throw new IllegalArgumentException("The grades have to be between 0 and 1.");
		}
		
		map = ImmutableSortedMap.copyOf(parameters);
		if(!valuesAreSorted()) {
			throw new IllegalArgumentException("A grade cannot be greater than another if its value associated is lower.");
		}
		LOGGER.info("The map of data has been successfully instantiated.");
	}
	
	/**
	 * @return <code>true</code> if the list of values is sorted,
	 * <code>false</code> otherwise.
	 */
	private boolean valuesAreSorted() {
		List<Double> values = new ArrayList<Double>(this.map.values());
		Comparator<Double> comparator = Comparator.naturalOrder();
		return Comparators.isInOrder(values, comparator);
	}

	@Override
	public double getSubjectiveValue(Double objectiveData) {
		
		Verify.verify(map.isEmpty() || map.size() < 2);
		if(objectiveData <= map.firstKey()) {
			return 0d;
		}
		if(objectiveData >= map.lastKey()) {
			return 1d;
		}
		
		Map.Entry<Double, Double> minEntry = map.floorEntry(objectiveData);
		Map.Entry<Double, Double> maxEntry = map.ceilingEntry(objectiveData);
		Double minKey = minEntry.getKey();
		Double maxKey = maxEntry.getKey();
		Double minValue = minEntry.getValue();
		Double maxValue = maxEntry.getValue();
		
		return ((objectiveData - minKey)*(maxValue - minValue)/(maxKey - minKey)) + minValue;
	}

	@Override
	public Double apply(Double t) {
		return getSubjectiveValue(t);
	}

}
