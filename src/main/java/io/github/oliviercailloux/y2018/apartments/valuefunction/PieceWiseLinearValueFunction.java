package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.common.base.Verify;

/**
 * A class that allows the user to determinate the subjective value of a double
 * given in argument, according to two or more known values.

 * 
 */
public class PieceWiseLinearValueFunction extends MultiPartialValueFunction {

	/**
	 * Builder of the PieceWiseLinearValueFunction
	 * 
	 * @param parameters is the dictionary of all values along with the grades
	 *                   associated to them, In this map, if the collection of keys
	 *                   was sorted, then the collection of grades would also have
	 *                   to be sorted. There has to be a value associated to the
	 *                   grade 0, and another value associated to the grade 1.
	 */
	public PieceWiseLinearValueFunction(Map<Double, Double> parameters) {
		
		super(parameters, new ConcurrentSkipListMap<Double,PartialValueFunction<Double>>());
		
		ConcurrentSkipListMap<Double,PartialValueFunction<Double>> linears = new ConcurrentSkipListMap<Double,PartialValueFunction<Double>>();
		
		for(Double d : map.keySet()) {
			linears.put(d,new LinearValueFunction(d,map.ceilingKey(d).doubleValue()));
		}
		
		setPartials(linears);
		
	}

	@Override
	public double getSubjectiveValue(Double objectiveData) {

		Verify.verify(map.size() >= 2);

		if (objectiveData <= map.firstKey()) {
			return 0d;
		}
		if (objectiveData >= map.lastKey()) {
			return 1d;
		}

		Map.Entry<Double, Double> minEntry = map.floorEntry(objectiveData);
		Map.Entry<Double, Double> maxEntry = map.ceilingEntry(objectiveData);
		Double minKey = minEntry.getKey();
		Double maxKey = maxEntry.getKey();
		Double minValue = minEntry.getValue();
		Double maxValue = maxEntry.getValue();

		return ((objectiveData - minKey) * (maxValue - minValue) / (maxKey - minKey)) + minValue;
	}
	
	public double getSubjectiveValue(int objectiveDataInt) {
		return this.getSubjectiveValue(Integer.valueOf(objectiveDataInt).doubleValue());
	}

	@Override
	public Double apply(Double t) {
		return getSubjectiveValue(t);
	}

}
