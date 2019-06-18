package io.github.oliviercailloux.y2018.apartments.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class Filter<T> {
	
	private Predicate<T> pre;
	
	Filter() {
		this.pre = o -> false;
	}
	
	void reset() {
		setPre(o -> false);
	}
	
	void concat(Predicate<T> pre) {
		this.pre.and(pre);
	}
	
	static <T> List<Apartment> filter(List<Apartment> aparts, Predicate<T> predicate) {
		return new ArrayList<Apartment>();
	}

	public Predicate<T> getPre() {
		return pre;
	}

	public void setPre(Predicate<T> pre) {
		this.pre = pre;
	}
	
}