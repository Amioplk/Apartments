package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Map;
import java.util.SortedMap;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;

/**
 * @author Amioplk, alexisperdereau
 * A class which enables to get a utility whose type is different between 0 and 1
 * Does only work with ? extends PartialValueFunction<Double>
 */
public class MultiPartialValueFunction extends PieceWiseLinearValueFunction {
	
	/**
	 * Represents the PartialValueFunctions used from the key
	 * A map of PartialValueFunctions 
	 * Must have a size which is the size of map - 1
	 */
	private SortedMap<Double, PartialValueFunction<Double>> partials;
	
	public MultiPartialValueFunction(Map<Double, Double> parameters) {
		super(parameters);
		setPartials(partials);
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
	
	public Double apply(Double arg0) {
		return this.getSubjectiveValue(arg0);
	}

	public SortedMap<Double, PartialValueFunction<Double>> getPartials() {
		return partials;
	}

	public void setPartials(SortedMap<Double, PartialValueFunction<Double>> partials) {
		this.partials = partials;
	}
	
}