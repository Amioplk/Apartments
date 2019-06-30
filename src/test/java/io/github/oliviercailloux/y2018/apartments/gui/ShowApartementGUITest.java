package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.IOException;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
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
		ShowApartementGUI printapartment = new ShowApartementGUI("ApartmentA.xml");
		assertNotEquals(printapartment.appar, null);
		assertEquals(printapartment.appar.getImages().size() , 4);
	}



}
