package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;

/**
 * A class that allows the user to determinate the subjective value of a double
 * given in argument, according to two or more known values.
 * 
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

		super(parameters, new ConcurrentSkipListMap<Range<Double>, PartialValueFunction<Double>>());

		ConcurrentSkipListMap<Range<Double>, PartialValueFunction<Double>> linears = new ConcurrentSkipListMap<Range<Double>, PartialValueFunction<Double>>(
				Comparator.comparingDouble(Range::lowerEndpoint));

		Preconditions.checkNotNull(map);

		for (Double k : map.keySet()) {
			if (map.higherKey(k) != null) {
				linears.put(Range.closed(map.floorKey(k), map.higherKey(k)),
						new LinearValueFunction(map.floorEntry(k), map.higherEntry(k)));
			}
		}

		setPartials(linears);

	}

	public double getSubjectiveValue(int objectiveDataInt) {
		return this.getSubjectiveValue(Integer.valueOf(objectiveDataInt).doubleValue());
	}

}