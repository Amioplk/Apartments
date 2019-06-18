package io.github.oliviercailloux.y2018.apartments.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * @author Amioplk
 * Manages random ranges of doubles
 */
public class RandomRange {

	public static List<Double> weightRangeOfSum(double s, int size) {
		
		List<Double> weightRange = new ArrayList<Double>();
		
		for(int i = 0 ; i < size ; ++i) weightRange.add(Math.random());
		
		/**
		 * Found on https://stackoverflow.com/questions/30125296/how-to-sum-a-list-of-integers-with-java-streams
		 */
		double sum = weightRange.stream().reduce(0d, Double::sum);
		weightRange = weightRange.stream().map(d -> (d/sum)*s).collect(ImmutableList.toImmutableList());
		
		return weightRange;
		
	}
	
}
