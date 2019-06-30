package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

/**
 *  @author AIT ALI BRAHAM Farouk 
 * Date : 20.06.2019
 *
 */
public class ShowApartementGUITest {

	
	final static Logger LOGGER = (Logger) LoggerFactory.getLogger(CreateApartmentGUI.class);
	
	
	/**
	 *
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testShowApartementGUI() throws IOException, IllegalArgumentException, IllegalAccessException {
		ShowApartementGUI printapartment = new ShowApartementGUI();
		assertNotEquals(printapartment.appar, null );
		
	}

	/**
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * test if the images are been successfully loaded
	 */
	@Test
	public void testShowApartementGUIString() throws IllegalArgumentException, IllegalAccessException, IOException {
		ReadApartmentsXMLFormat xmlReader = new ReadApartmentsXMLFormat();
		InputStream f = ShowApartementGUITest.class.getResourceAsStream("ApartmentA.xml");
		Apartment appar = xmlReader.readApartment(f);
		ShowApartementGUI printapartment = new ShowApartementGUI(appar);
		assertNotEquals(printapartment.appar, null);
		assertEquals(printapartment.appar.getImages().size() , 4);
	}



}
