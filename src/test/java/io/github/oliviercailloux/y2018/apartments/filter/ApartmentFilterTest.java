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
	
	static List<Apartment> readTwoFilesWithDifferentTitles() throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		ReadApartmentsXMLFormat readXML = new ReadApartmentsXMLFormat();
		
		InputStream input = new FileInputStream(new File("start-apartment.xml"));
		Apartment a1 = readXML.readApartment(input);
		if(!a1.getTitle().equals("hotel")) a1.setTitle("hotel");
		
		input = new FileInputStream(new File("start-apartment.xml"));
		Apartment a2 = readXML.readApartment(input);
		a2.setTitle("restaurant");
		
		List<Apartment> aparts = new ArrayList<Apartment>();
		aparts.add(a1);
		aparts.add(a2);
		
		return aparts;
	}
	
	static List<Apartment> readTwoFilesWithDifferentNbOfBathrooms() throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		ReadApartmentsXMLFormat readXML = new ReadApartmentsXMLFormat();
		
		InputStream input = new FileInputStream(new File("start-apartment.xml"));
		Apartment a1 = readXML.readApartment(input);
		if(!(a1.getNbBathrooms() < 2)) a1.setNbBathrooms(2);
		
		input = new FileInputStream(new File("start-apartment.xml"));
		Apartment a2 = readXML.readApartment(input);
		a2.setNbBathrooms(1);
		
		List<Apartment> aparts = new ArrayList<Apartment>();
		aparts.add(a1);
		aparts.add(a2);
		
		return aparts;
	}
	
	
	@Test
	public void correctFilterOnTitle() throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		List<Apartment> aparts = ApartmentFilterTest.readTwoFilesWithDifferentTitles();
		Apartment a1 = aparts.get(0);
		Apartment a2 = aparts.get(1);
		
		ApartmentFilter filter = new ApartmentFilter();
		filter.concat(a -> a.getTitle().contains("hotel"));
		
		aparts = ApartmentFilter.filter(aparts, filter.getPre());
		Assertions.assertTrue(aparts.contains(a1));
		Assertions.assertFalse(aparts.contains(a2));
	}
	
	@Test
	public void correctFilterOnNbBathroom() throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		List<Apartment> aparts = ApartmentFilterTest.readTwoFilesWithDifferentNbOfBathrooms();
		Apartment a1 = aparts.get(0);
		Apartment a2 = aparts.get(1);
		
		ApartmentFilter filter = new ApartmentFilter();
		filter.concat(a -> a.getNbBathrooms() >= 2);
		
		aparts = ApartmentFilter.filter(aparts, filter.getPre());
		Assertions.assertTrue(aparts.contains(a1));
		Assertions.assertFalse(aparts.contains(a2));
	}
	
	@Test
	public void correctFilterOnBothTitleAndBathroom() throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		
		List<Apartment> aparts1 = ApartmentFilterTest.readTwoFilesWithDifferentTitles();
		Apartment a1 = aparts1.get(0);
		aparts1.get(0).setNbBathrooms(2);
		Apartment a2 = aparts1.get(1);
		
		List<Apartment> aparts2 = ApartmentFilterTest.readTwoFilesWithDifferentNbOfBathrooms();
		Apartment a3 = aparts2.get(0);
		aparts2.get(0).setTitle("hotel");
		Apartment a4 = aparts2.get(1);
		
		aparts1.addAll(aparts2);
		
		ApartmentFilter filter = new ApartmentFilter();
		filter.concat(a -> a.getNbBathrooms() >= 2);
		filter.concat(a -> a.getTitle().contains("hotel"));
		
		aparts1 = ApartmentFilter.filter(aparts1, filter.getPre());
		Assertions.assertTrue(aparts1.contains(a1));
		Assertions.assertFalse(aparts1.contains(a2));
		Assertions.assertTrue(aparts1.contains(a3));
		Assertions.assertFalse(aparts1.contains(a4));
	}
	
}
