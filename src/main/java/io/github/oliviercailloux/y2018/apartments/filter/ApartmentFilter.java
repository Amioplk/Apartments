package io.github.oliviercailloux.y2018.apartments.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class ApartmentFilter {
	
	private Predicate<Apartment> pre;
	
	ApartmentFilter() {
		this.pre = o -> false;
	}
	
	void reset() {
		setPre(o -> false);
	}
	
	void concat(Predicate<Apartment> pre) {
		this.pre.and(pre);
	}
	
	static List<Apartment> filter(List<Apartment> aparts, Predicate<? super Apartment> predicate) {
		return aparts.stream().filter(predicate).collect(Collectors.toList());
	}

	public Predicate<Apartment> getPre() {
		return pre;
	}

	public void setPre(Predicate<Apartment> pre) {
		this.pre = pre;
	}
	
}