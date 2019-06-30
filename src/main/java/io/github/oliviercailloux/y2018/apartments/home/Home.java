package io.github.oliviercailloux.y2018.apartments.home;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.gui.AskOpinionForUtility;
import io.github.oliviercailloux.y2018.apartments.gui.ListApartmentAndDetailsGUI;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;

/**
 * @author AIT ALI BRAHAM Farouk 
 * This class aims to merge all the functionalities developed during this project
 *
 */
public class Home {

	private final static Logger LOGGER = LoggerFactory.getLogger(Home.class);
	private ArrayList<Apartment> listApartments;
	

	
	public static void main(String[] args) throws NumberFormatException, InvalidPropertiesFormatException, IOException {
		@SuppressWarnings("unused")
		Home home = new Home();
		
	}
	
	/**
	 * @throws NumberFormatException
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 */
	
	public Home() throws NumberFormatException, InvalidPropertiesFormatException, IOException{
		this.listApartments = this.loadAllApartments();

		AskOpinionForUtility asker = new AskOpinionForUtility();
		ApartmentValueFunction avf = new ApartmentValueFunction();
		asker.askQuestions();
		asker.adaptAnswers(avf);
		ListApartmentAndDetailsGUI listGUI = new ListApartmentAndDetailsGUI(this.listApartments,avf);
		listGUI.viewList();
		
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
		ArrayList<String> listXML = seekXML_folder("FILES");
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();
		ArrayList<Apartment> listApartments = new ArrayList<Apartment>();
		
		for ( String str : listXML){
			LOGGER.info("ajout de l'appartment " + str);
			listApartments.add(r.readApartment(Home.class.getResourceAsStream("FILES/"+str) ));
		}
		
		return listApartments;
	}
	
	/**
	 * @return a list of elements in the folder ; should be xml files only 
	 * THIS FUNCTION IS THE SAME USED TO LOAD THE IMAGES INTO THE APARTMENT
	 */
	private ArrayList<String> seekXML_folder(String folderName){
		
			File file = new File(Home.class.getResource(folderName).getFile());
			
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
