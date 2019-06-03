package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A class that allows the user to determinate the subjective value of a double given in argument,
 * according to two or more known values.
 *
 */
public class PieceWiseLinearValueFunction implements PartialValueFunction<Double> {
	
	private SortedMap<Double, Double> map; // K : value, V : grade
	private final static Logger LOGGER = LoggerFactory.getLogger(PieceWiseLinearValueFunction.class);
	
	/**
	 * Builder of the PieceWiseLinearValueFunction
	 * @param parameters : the dictinary of all values along with the grades associated to them,
	 * If the difference of two values is positive, then the difference of their grades also has
	 * to be positive.
	 * THere has to be a value associated to the value 0, and another value associated to the grade 1.
	 */
	public PieceWiseLinearValueFunction(Map<Double, Double> parameters) {
		boolean grade0 = false;
		boolean grade1 = false;
		for(Double v : parameters.values()) {
			if(v < 0 || v > 1) {
				LOGGER.error("The grade given to each value has to be between 0 and 1.");
				throw new IllegalArgumentException("The grades have to be between 0 and 1.");
			}
			if(v == 0) grade0 = true;
			if(v == 1) grade1 = true;
		}
		if(!grade0 || !grade1) {
			LOGGER.error("There has to be a value associated to the grade 0, and a value associated to the grade 1.");
			throw new IllegalArgumentException("The value associated to the grade 0 or 1 is missing.");
		}
		map = new ConcurrentSkipListMap<Double, Double>(parameters);
		if(!CheckValues()) {
			LOGGER.error("A grade cannot be greater than another if its value associated is lower.");
			throw new IllegalArgumentException("A grade cannot be greater than another if its value associated is lower.");
		}
		LOGGER.info("The map of data has been successfully instatiated.");
	}
	
	/**
	 * @return <code>false</code> if it finds two pairs P1 and P2 where P1.key > P2.key and P1.value < P2.value,
	 * <code>true</code> otherwise.
	 */
	private boolean CheckValues() {
		double min = 0;
		for(double d : this.map.values()) {
			if(d < min) {
				return false;
			}
			else {
				min = d;
			}
		}
		return true;
	}

	@Override
	public double getSubjectiveValue(Double objectiveData) throws IllegalArgumentException {
		if(map.isEmpty()) {
			throw new IllegalArgumentException();
		}
		Double minKey = map.firstKey();
		Double maxKey = map.lastKey();
		if(objectiveData <= minKey) {
			return 0d;
		}
		if(objectiveData >= maxKey) {
			return 1d;
		}
		
		Object[] keys = map.keySet().toArray();
		for(int i = 0; i < keys.length - 1; i++) {
			minKey = (Double) keys[i];
			maxKey = (Double) keys[i+1];
			if(objectiveData == minKey) return map.get(minKey);
			if(objectiveData > minKey && objectiveData <= maxKey) break;
		}
		Double minValue = map.get(minKey);
		Double maxValue = map.get(maxKey);
		return ((objectiveData - minKey)*(maxValue - minValue)/(maxKey - minKey)) + minValue;
	}

	@Override
	public Double apply(Double t) {
		return getSubjectiveValue(t);
	}
}
