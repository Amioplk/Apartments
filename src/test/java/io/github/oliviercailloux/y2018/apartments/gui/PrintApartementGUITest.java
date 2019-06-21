package io.github.oliviercailloux.y2018.apartments.gui;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 *  @author AIT ALI BRAHAM Farouk 
 * Date : 20.06.2019
 *
 */
public class PrintApartementGUITest {

	
	private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(CreateApartmentGUI.class);
	
	
	/**
	 *
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testPrintApartementGUI() throws IOException, IllegalArgumentException, IllegalAccessException {
		PrintApartementGUI printapartment = new PrintApartementGUI();
		assertNotEquals(printapartment.appar, null );
		
	}

	/**
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * test if the images are been successfully loaded
	 */
	@Test
	public void testPrintApartementGUIString() throws IllegalArgumentException, IllegalAccessException, IOException {
		PrintApartementGUI printapartment = new PrintApartementGUI("apartTest.xml");
		assertNotEquals(printapartment.appar, null);
		assertEquals(printapartment.appar.getImagesFloder() , "Images_A");
		assertEquals(printapartment.appar.getTitle(), "soleil couchant");
		LOGGER.info(printapartment.appar.getTitle());
		
		assertEquals(printapartment.appar.getImages().size() , 4);
	}



}
