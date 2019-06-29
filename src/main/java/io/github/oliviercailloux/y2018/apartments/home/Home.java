package io.github.oliviercailloux.y2018.apartments.home;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.gui.ListApartmentAndDetailsGUI;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

public class Home {

	private final static Logger LOGGER = LoggerFactory.getLogger(Home.class);
	private ArrayList<Apartment> listApartments;
	
	public Home() throws NumberFormatException, InvalidPropertiesFormatException, IOException{
		this.listApartments = this.loadAllApartments();
		ListApartmentAndDetailsGUI listGUI = new ListApartmentAndDetailsGUI(this.listApartments);
	
	}
	
	public static void main(String[] args) throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		// TODO Auto-generated method stub
		Home home = new Home();
		
	}
	
	
	/**
	 * @param listApartments
	 * A method for charging all the apartments 
	 * We will seek the resources folder and add each apartment to the list
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 * @throws NumberFormatException 
	 */
	 public ArrayList<Apartment> loadAllApartments() throws NumberFormatException, InvalidPropertiesFormatException, IOException{
		ArrayList<String> listXML = seekXML_folder("XML");
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();
		ArrayList<Apartment> listApartments = new ArrayList<Apartment>();
		
		for ( String str : listXML){
			listApartments.add(r.readApartment(Home.class.getResourceAsStream(str) ));
			LOGGER.info("ajout de l'appartment " + str);
		}
		
		return listApartments;
	}
	
	/**
	 * @return a list of elements in the folder ; should be xml files only 
	 * THIS FUNCTION IS THE SAME USED TO LOAD THE IMAGES INTO THE APARTMENT
	 */
	private ArrayList<String> seekXML_folder(String folderName){
		
			File file = new File(Home.class.getResource("FILES").getFile());
			
			LOGGER.info("Folder path has been set : " + file );
			String liste[] = file.list();
	        ArrayList<String> finalList  = new ArrayList<String>();
	        for (String str : liste){
	        	finalList.add(str);
	        	LOGGER.info("a new path has been set : " + str);
	        }
	        return finalList;
		
	}
	
	
	
}
