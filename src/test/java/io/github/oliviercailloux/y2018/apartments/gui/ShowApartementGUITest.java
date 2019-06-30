package io.github.oliviercailloux.y2018.apartments.gui;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 *  @author AIT ALI BRAHAM Farouk 
 * Date : 20.06.2019
 *
 */
public class ShowApartementGUITest {

	
	private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(CreateApartmentGUI.class);
	
	
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
		ShowApartementGUI printapartment = new ShowApartementGUI("apartTest.xml");
		assertNotEquals(printapartment.appar, null);
		assertEquals(printapartment.appar.getImagesFolder() , Paths.get("Images_A"));
		assertEquals(printapartment.appar.getTitle(), "soleil couchant");
		LOGGER.info(printapartment.appar.getTitle());
		
		assertEquals(printapartment.appar.getImages().size() , 4);
	}



}
