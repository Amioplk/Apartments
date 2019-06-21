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
	
	/**
	 * @param pre adds the criteria(s) to the filter
	 */
	void concat(Predicate<Apartment> pre) {
		this.setPre(this.getPre().and(pre));
	}
	
	/**
	 * @param aparts represents the apartments to filter on
	 * @param predicate represents the criterias to filter on
	 * @return the apartments from param aparts that do match the predicate of this filter
	 */
	public List<Apartment> filter(List<Apartment> aparts) {
		return aparts.stream().filter(pre).collect(Collectors.toList());
	}

	public Predicate<Apartment> getPre() {
		return pre;
	}

	public void setPre(Predicate<Apartment> pre) {
		this.pre = pre;
	}
	
}