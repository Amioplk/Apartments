package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PieceWiseLinearValueFunction implements PartialValueFunction<Double> {
	
	private SortedMap<Double, Double> map; // K : value, V : grades
	private final static Logger LOGGER = LoggerFactory.getLogger(LinearValueFunction.class);
	
	public PieceWiseLinearValueFunction(Map<Double, Double> parameters) {
		boolean grade0 = false;
		boolean grade1 = false;
		for(Double v : parameters.values()) {
			if(v < 0 || v > 1) {
				LOGGER.error("The grade given to each value has to be between 0 and 1.");
				throw new IllegalArgumentException();
			}
			if(v == 0) grade0 = true;
			if(v == 1) grade1 = true;
		}
		if(!grade0 || !grade1) {
			LOGGER.error("There has to be a value associated to the grade 0, and a value associated to the grade 1.");
			throw new IllegalArgumentException();
		}
		if(!CheckValues()) {
			LOGGER.error("A grade cannot be greater than another if its value associated is lower.");
			throw new IllegalArgumentException();
		}
		map = new ConcurrentSkipListMap<Double, Double>(parameters);
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
		Double minValue = map.firstKey();
		Double maxValue = map.lastKey();
		if(objectiveData <= minValue) {
			return 0d;
		}
		if(objectiveData >= maxValue) {
			return 1d;
		}
		
		Double[] keys = (Double[]) map.keySet().toArray();
		for(int i = 0; i < keys.length - 1; i++) {
			if(objectiveData == keys[i]) return map.get(keys[i]);
			if(objectiveData > keys[i] && objectiveData <= keys[i+1]) {
				minValue = keys[i];
				maxValue = keys[i+1];
				break;
			}
		}
		// il reste à calculer avec la formule qu'on avait trouvé.
		return 0;
	}

	@Override
	public Double apply(Double t) {
		// TODO Auto-generated method stub
		return null;
	}

}
