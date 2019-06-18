package io.github.oliviercailloux.y2018.apartments.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

/**
 * @author Amioplk
 * An object which enables us to filter on a list of Apartments considering a certain predicate (or intersection of predicates)
 * By default, doesn't filter anything since pre = o -> true
 */
public class ApartmentFilter {
	
	private Predicate<Apartment> pre;
	
	ApartmentFilter() {
		this.pre = o -> true;
	}
	
	void reset() {
		setPre(o -> true);
	}
	
	void concat(Predicate<Apartment> pre) {
		this.setPre(this.getPre().and(pre));
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