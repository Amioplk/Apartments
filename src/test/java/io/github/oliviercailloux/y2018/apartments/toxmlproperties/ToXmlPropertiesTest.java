package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readApartments.ReadTwoApartmentsTest;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

class ToXmlPropertiesTest {
	@Test

	void readApartmentTest() throws IllegalArgumentException, IllegalAccessException, IOException
	{
		XMLProperties j = new XMLProperties();
		Apartment a = new Apartment(1182118.48, "118 rue du père noel 77480", "Grand Igloo");
		a.setNbSleeping(10);
		a.setNbMinNight(1);
		a.setTerrace(true);
		a.setNbBedrooms(5);
		a.setPricePerNight(404);
		a.setTele(false);
		a.setWifi(true);
		a.setNbBathrooms(1);
		a.setDescription("Un igloo tout mignon en compagnie du père noël et de la mère noël");
		a.setFloorAreaTerrace(8.6);
		File f = new File("src/test/resources/io/github/oliviercailloux/y2018/apartments/readApartments/xmlfileTest.xml");
		try(FileOutputStream s = new FileOutputStream(f.getAbsolutePath()))
		{
			j.toXML(a, s);
		}
		
		
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();

		try (InputStream f1 = ReadApartmentsXMLFormat.class.getResourceAsStream("xmlfileTest.xml")){

			Apartment a1 = r.readApartment(f1);

			Assertions.assertEquals("Address doesn't match with the address in the XML file","118 rue du père noel 77480",a1.getAddress());
			Assertions.assertEquals("Title doesn't match with the title in the XML file","Grand Igloo",a1.getTitle());
			Assertions.assertEquals("Description doesn't match with the description in the XML file","Un igloo tout mignon en compagnie du père noël et de la mère noël",a1.getDescription());
			Assertions.assertEquals(10,a1.getNbSleeping(),"Number of sleepings doesn't match with the number of sleepings in the XML file");
			Assertions.assertEquals(1,a1.getNbBathrooms(),"Number of bathrooms doesn't match with the number of bathrooms in the XML file");
			Assertions.assertEquals(5,a1.getNbBedrooms(),"Number of bedrooms doesn't match with the number of bedrooms in the XML file");
			Assertions.assertEquals(1182118.48,a1.getFloorArea(),0,"Floor area doesn't match with the floor area in the XML file");
			Assertions.assertEquals(8.6,a1.getFloorAreaTerrace(),0,"Floor area terrace doesn't match with the floor area terrace in the XML file");
			Assertions.assertEquals(404.0,a1.getPricePerNight(),0,"Price per night doesn't match with the price per night in the XML file");
			Assertions.assertEquals(1,a1.getNbMinNight(),"Minimum number of nights doesn't match with the minimum number of nights in the XML file");
			Assertions.assertFalse(a1.getTele(),"The value of boolean tele doesn't match with the value of tele in the XML File");
			Assertions.assertTrue(a1.getTerrace(),"The value of boolean terrace doesn't match with the value of terrace in the XML File");
			Assertions.assertTrue(a1.getWifi(),"The value of boolean wifi doesn't match with the value of wifi in the XML File");
			
		}
	

		ReadApartmentsXMLFormat r1 = new ReadApartmentsXMLFormat();
		try (InputStream f1 = ReadTwoApartmentsTest.class.getResourceAsStream("xmlfileTest.xml")){
			Apartment a1 = r1.readApartment(f1);
			System.out.println(a1);
			
		}
	}
}