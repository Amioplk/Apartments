package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Range;


/**
 * Class which enables the user to get the subjective value of a double given in argument in the case where the subjective value associated to 1 is the lower bound of the range.
 *
 */
public class ReversedLinearValueFunction implements PartialValueFunction<Double> {

	private double minSubjectiveValue;
	private double maxSubjectiveValue;
	private Range<Double> interval ;
	private final static Logger LOGGER = LoggerFactory.getLogger(ReversedLinearValueFunction.class);

	/**
	 * Create a guava interval between min and max.
	 * @param min lower bound of the interval associated to the subjective value 1
	 * @param max upper bound of the interval associated to the subjective value 0
	 */

	public ReversedLinearValueFunction (double min, double max) {
		if (min == max) {
			LOGGER.error("The upper bound is equal to the lower bound in the constructor of the LinearValueFunction.");
			throw new IllegalArgumentException("The upper bound can't be equal to the lower bound.");
		}
		interval = Range.closed(min, max);
		LOGGER.info("The interval ["+min+","+max+"] "+" has been set with success in the LinearValueFunction class.");
	}

	/**
	 * @param min represents the lowerEndBound of the desired interval, along with its subjectiveValue associated
	 * @param max represents the upperEndBound of the desired interval, along with its subjectiveValue associated
	 */
	public ReversedLinearValueFunction (Map.Entry<Double,Double> min, Map.Entry<Double,Double> max) {
		this(min.getKey(), max.getKey());
		setMinSubjectiveValue(min.getValue());
		setMaxSubjectiveValue(max.getValue());
	}
	
	
	@Override
	public double getSubjectiveValue(Double objectiveData) throws IllegalArgumentException {
		if(interval.lowerEndpoint() > objectiveData) {
			return 1;
		}
		else if(interval.upperEndpoint() < objectiveData) {
			return 0;
		}
		else {
			return 1 - (objectiveData - interval.lowerEndpoint())/(interval.upperEndpoint() - interval.lowerEndpoint());
		}
	}

	@Override
	public Double apply(Double objectiveData) {
		return getSubjectiveValue(objectiveData);
	}

	public double getMinSubjectiveValue() {
		return minSubjectiveValue;
	}

	public void setMinSubjectiveValue(double minSubjectiveValue) {
		this.minSubjectiveValue = minSubjectiveValue;
	}

	public double getMaxSubjectiveValue() {
		return maxSubjectiveValue;
	}

	public void setMaxSubjectiveValue(double maxSubjectiveValue) {
		this.maxSubjectiveValue = maxSubjectiveValue;
	}

}
