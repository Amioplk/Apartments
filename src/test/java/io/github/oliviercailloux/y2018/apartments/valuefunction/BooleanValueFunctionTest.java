package io.github.oliviercailloux.y2018.apartments.valuefunction;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BooleanValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		BooleanValueFunction b = new BooleanValueFunction(true);
		Assertions.assertEquals(1, b.getSubjectiveValue(true), 0);
		Assertions.assertEquals(0, b.getSubjectiveValue(false), 0);
	}


	@Test
	void applyTest() {
		BooleanValueFunction bo = new BooleanValueFunction(false);
		Assertions.assertEquals(0, bo.apply(true), 0);
		Assertions.assertEquals(1, bo.apply(false), 0);

	}
}