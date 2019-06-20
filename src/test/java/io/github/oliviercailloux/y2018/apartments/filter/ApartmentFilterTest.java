package io.github.oliviercailloux.y2018.apartments.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

public class ApartmentFilterTest {
	
	@Test
	public void correctFilterOnTitle() throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		List<Apartment> aparts = new ArrayList<>();
		
		Apartment a1 = getApartmentWithApropriateTitle(true);
		Apartment a2 = getApartmentWithApropriateTitle(false);
		
		aparts.add(a1);
		aparts.add(a2);
		
		ApartmentFilter filter = new ApartmentFilter();
		filter.concat(a -> a.getTitle().contains("hotel"));
		
		aparts = ApartmentFilter.filter(aparts, filter.getPre());
		Assertions.assertTrue(aparts.contains(a1));
		Assertions.assertFalse(aparts.contains(a2));
	}
	
	@Test
	public void correctFilterOnNbBathroom() throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		List<Apartment> aparts = new ArrayList<>();
		
		Apartment a1 = getApartmentWithApropriateNbOfBathroom(true);
		Apartment a2 = getApartmentWithApropriateNbOfBathroom(false);
		
		aparts.add(a1);
		aparts.add(a2);
		
		ApartmentFilter filter = new ApartmentFilter();
		filter.concat(a -> a.getNbBathrooms() >= 2);
		
		aparts = ApartmentFilter.filter(aparts, filter.getPre());
		Assertions.assertTrue(aparts.contains(a1));
		Assertions.assertFalse(aparts.contains(a2));
	}
	
	@Test
	public void correctFilterOnBothTitleAndBathroom() throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		List<Apartment> aparts = new ArrayList<>();
		
		Apartment a1 = getApartmentWithApropriateNbOfBathroom(true);
		a1.setTitle("restaurant");
		Apartment a2 = getApartmentWithApropriateNbOfBathroom(false);
		Apartment a3 = getApartmentWithApropriateTitle(true);
		a3.setNbBathrooms(1);
		Apartment a4 = getApartmentWithApropriateTitle(false);
		Apartment a5 = getApartmentWithApropriateTitle(true);
		a5.setNbBathrooms(2);
		
		aparts.add(a1);
		aparts.add(a2);
		aparts.add(a3);
		aparts.add(a4);
		aparts.add(a5);
		
		ApartmentFilter filter = new ApartmentFilter();
		filter.concat(a -> a.getNbBathrooms() >= 2);
		filter.concat(a -> a.getTitle().contains("hotel"));
		
		aparts = ApartmentFilter.filter(aparts, filter.getPre());
		Assertions.assertFalse(aparts.contains(a1));
		Assertions.assertFalse(aparts.contains(a2));
		Assertions.assertFalse(aparts.contains(a3));
		Assertions.assertFalse(aparts.contains(a4));
		Assertions.assertTrue(aparts.contains(a5));
		
	}
	
	private static Apartment getApartmentWithApropriateTitle(boolean correct) throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		ReadApartmentsXMLFormat readXML = new ReadApartmentsXMLFormat();
		
		InputStream input = new FileInputStream(new File("start-apartment.xml"));
		Apartment a = readXML.readApartment(input);
		if(correct)
			a.setTitle("hotel");
		else
			a.setTitle("restaurant");
		return a;
	}
	
	private static Apartment getApartmentWithApropriateNbOfBathroom(boolean correct) throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
	ReadApartmentsXMLFormat readXML = new ReadApartmentsXMLFormat();
		
		InputStream input = new FileInputStream(new File("start-apartment.xml"));
		Apartment a = readXML.readApartment(input);
		if(correct)
			a.setNbBathrooms(2);
		else
			a.setNbBathrooms(1);
		return a;
	
	}
	
}
