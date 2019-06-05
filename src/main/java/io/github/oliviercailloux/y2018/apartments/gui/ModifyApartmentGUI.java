package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import io.github.oliviercailloux.y2018.apartments.iconDisplay.DisplayIcon;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

/**
 * This class initializes a GUI for modifying an apartment object from an XML File.
 *
 */
public class ModifyApartmentGUI extends FormApartmentGUI{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ModifyApartmentGUI.class);
	
	public ModifyApartmentGUI(String fileCompleteName) {
		super();
		this.file = new File(fileCompleteName);
		this.titlePanel = "Modify an apartment";
	}
	
	/**
	 * 
	 * @param args
	 * 	must contains as first parameter the complete name of the file (Full Path).
	 */
	static public void main(String args[]) {
		ModifyApartmentGUI c;
		if (args[0] == null)
			c = new ModifyApartmentGUI("GUITest");
		else
			c = new ModifyApartmentGUI(args[0]);
		try {
			c.screenDisplay();
		}
		catch(Exception e){
			LOGGER.error("An error occured while displaying the data.");
		}
	}
	/**
	 * This method initializes every element of the form with the data present 
	 * in the Apartment apart field.
	 * It used getApartment() in order to get the apartment from the xml file 
	 * transmit in the main method
	 * @throws IOException if an exception occurs in getApartment() 
	 */
	private void initializeField() throws IOException {
		getApartment();
		if(!apart.getTitle().equals("") && !apart.getTitle().isEmpty())
			title.setText(apart.getTitle());
		if(apart.getTele())
			tele.setSelection(true);
		if(apart.getTerrace())
			terrace.setSelection(true);
		if(apart.getWifi())
			wifi.setSelection(true);
		if(!apart.getAddress().equals("") && !apart.getAddress().isEmpty())
			address.setText(apart.getAddress());
		if(!apart.getDescription().equals("") && !apart.getDescription().isEmpty())
			description.setText(apart.getDescription());
		if(apart.getFloorArea() != 0)
			floorArea.setText(Double.toString(apart.getFloorArea()));
		if(apart.getFloorAreaTerrace() != 0)
		{
			floorAreaTerrace.setText(Double.toString(apart.getFloorAreaTerrace()));
			floorAreaTerrace.setEditable(true);
		}
		if(apart.getNbBathrooms() != 0)
			nbBathrooms.setText(Integer.toString(apart.getNbBathrooms()));
		if(apart.getNbBedrooms() != 0)
			nbBedrooms.setText(Integer.toString(apart.getNbBedrooms()));
		if(apart.getNbMinNight() != 0)
			nbMinNight.setText(Integer.toString(apart.getNbMinNight()));
		if(apart.getNbSleeping() != 0)
			nbSleeping.setText(Integer.toString(apart.getNbSleeping()));
		if(apart.getPricePerNight() != 0)
			pricePerNight.setText(Double.toString(apart.getPricePerNight()));
	}
	
	/**
	 * Initialize the apart field with the xml file set in the file field.
	 * @throws IOException if an I/O exception occurs while opening the stream.
	 */
	private void getApartment() throws IOException {
		try (InputStream i = Files.asByteSource(file).openStream())
		{
			ReadApartmentsXMLFormat f = new ReadApartmentsXMLFormat();
			apart = f.readApartment(i);
		}
		catch(IOException e) {
			LOGGER.error("An arreor occured while reading the xml file.");
		}
	}
	/**
	 * General method which displays all the element of the GUI.
	 * @throws IOException if the logo doesn't load well.
	 * @throws IllegalAccessException if an exception occurs while initializing the form.
	 */
	protected void screenDisplay() throws IOException, IllegalAccessException {
		try(InputStream f = DisplayIcon.class.getResourceAsStream("logo.png")){

			LOGGER.info("The logo has been loaded with success.");
			FillLayout r = new FillLayout();
			r.type = SWT.VERTICAL;
			shell.setLayout(r);
			Image i = new Image(display, f);
			shell.setImage(i);
			shell.setText("Apartments");


			createPageTitle();
			createForm();

			shell.pack();
			shell.setMinimumSize(600, 700);
			shell.setSize(600, 700);

			shell.open();
			LOGGER.info("The Shell was opened with success.");
			
			initializeField();
			loadMessage(MessageInfo.LOAD,  "Everything is loaded !");
			while(!shell.isDisposed( )){
				if(!display.readAndDispatch( ))
					display.sleep( );
			}
			i.dispose();
			display.dispose();
			LOGGER.info("The screen was closed with success.");
		}
	}
}
	

