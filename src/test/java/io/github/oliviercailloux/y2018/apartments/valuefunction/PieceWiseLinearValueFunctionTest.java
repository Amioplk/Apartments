package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class PieceWiseLinearValueFunctionTest {
	
	@Test
	public void getSubjectiveValueNormalTest() throws IOException {
		
		Map<Double,Double> map = new HashMap<>();
		map.put(0d, 0d);
		map.put(10d,0.5);
		map.put(30d,1d);
		
		PieceWiseLinearValueFunction p = new PieceWiseLinearValueFunction(map);
		Assert.assertEquals(0.75, p.getSubjectiveValue(20d), 0.001);
		Assert.assertEquals(0.25, p.getSubjectiveValue(5d), 0.001);
	}
	
}
