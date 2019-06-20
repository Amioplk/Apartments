package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Range;

public class MultiPartialValueFunctionTest {

	@Test
	public void acceptDifferentTypesOfPartialValueFunction() {

		MultiPartialValueFunction multi = initializeMultiPartial();

		Assert.assertEquals(0.25, multi.getSubjectiveValue(5d), 0.001);
		Assert.assertEquals(0.625, multi.getSubjectiveValue(15d), 0.001);
		Assert.assertEquals(0.875, multi.getSubjectiveValue(25d), 0.001);

	}

	@Test
	public void correctSubjectiveValueOnBounds() {
		MultiPartialValueFunction multi = initializeMultiPartial();

		Assert.assertEquals(0, multi.getSubjectiveValue(0d), 0.001);
		Assert.assertEquals(0.5, multi.getSubjectiveValue(10d), 0.001);
		Assert.assertEquals(0.75, multi.getSubjectiveValue(20d), 0.001);
		Assert.assertEquals(1, multi.getSubjectiveValue(30d), 0.001);

	}

	private MultiPartialValueFunction initializeMultiPartial() {

		ConcurrentSkipListMap<Double, Double> map = new ConcurrentSkipListMap<>();
		map.put(0d, 0d);
		map.put(10d, 0.5);
		map.put(20d, 0.75);
		map.put(30d, 1d);

		SortedMap<Range<Double>, PartialValueFunction<Double>> partials = new ConcurrentSkipListMap<Range<Double>, PartialValueFunction<Double>>(
				Comparator.comparingDouble(Range::lowerEndpoint));

		int side = 0;
		for (Double key : map.keySet()) {
			if (map.higherKey(key) != null) {
				if (side % 2 == 0)
					partials.put(Range.closed(key, map.higherKey(key)),
							new LinearValueFunction(map.floorEntry(key), map.higherEntry(key)));
				if (side % 2 == 1)
					partials.put(Range.closed(key, map.higherKey(key)),
							new ReversedLinearValueFunction(map.floorEntry(key), map.higherEntry(key)));
			}
		}
		MultiPartialValueFunction multi = new MultiPartialValueFunction(ImmutableSortedMap.copyOf(map), partials);

		return multi;
	}

}
