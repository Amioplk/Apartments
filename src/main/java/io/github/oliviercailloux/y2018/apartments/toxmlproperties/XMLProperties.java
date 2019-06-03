package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class XMLProperties{

	
	private Properties properties;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(XMLProperties.class);
	
	public XMLProperties()
	{
		this.properties = new Properties();
	}
	/**
	 *  toXml transform an Apartment into an xml File. The user specify the file in parameter
	 * @param a
	 * 		the apartment to put into an xml file
	 * @param xmlFile
	 * 			an file object where the apartment will be store. Warning : if the file already exists, it will be erased.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void toXML(Apartment a, OutputStream xmlFile) throws IOException, IllegalArgumentException, IllegalAccessException
	{
			
			for(Field f : a.getClass().getDeclaredFields()) {
				
				String[] fullName = f.toString().split(" ")[2].split("\\.");
				
				f.setAccessible(true);
				properties.setProperty(fullName[fullName.length-1],f.get(a).toString());
				
				LOGGER.info("Adding entry : " + fullName[fullName.length-1] + " : " + f.get(a));
			
			}
				properties.remove("apartment");
				properties.remove("LOGGER");
				properties.remove("Logger");
				properties.storeToXML(xmlFile, "Generated file for the apartment " + a.getTitle());
				
				xmlFile.close();
				LOGGER.info("Stream has been closed");
//			}
		
	}
	
	public static void generateRandomXML() throws DOMException, IllegalArgumentException, IllegalAccessException, IOException {
		
		List<String> titlesList = Arrays.asList( "Maison", "logement", "appartement"," "," "," "," "," "," "," ");
		List<String> addressList = Arrays.asList( "2 avenue Pasteur 94160 Saint-mandé", "8 avenue de Paris 94160 Saint-mandé", "5 avenue des Champs-Elysées 75016" , "13 rue des Arts 75001","10 rue de Dauphine 75016","33 rue de Tolbiac 75013","33 rue de Tolbiac 75013"," "," ", " ");
		
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> address = new ArrayList<String>();
		titles.addAll(titlesList);
		address.addAll(addressList);
		
		for (int i = 1; i < 10 ; i ++) {
			 XMLProperties j = new XMLProperties();
			 String Area = String.format ("%.2f", (double)(Math.random()*300));
			 double floorArea = Double.parseDouble(Area);
			 int terraceInt = (int) (Math.random()*2);
			 boolean terrace = false;
			 double floorAreaTerrace = 0;
			 if (terraceInt != 0) {
				 terrace = true;
				 String AreaTerrace = String.format ("%.2f", (double)(Math.random()*100));
				 floorAreaTerrace = Double.parseDouble(AreaTerrace);
			 }
			 int nbMinNight = (int) (Math.random()*5);
			 int nbBedrooms = (int) (Math.random()*10);
			 String price = String.format ("%.2f", (double)(Math.random()*80 + 20));
			 double pricePerNight = Double.parseDouble(price);
			 int nbSleeping = (int) (Math.random()*5);
			 int nbBathrooms = (int) (Math.random()*10);
			 
			 Apartment a = new Apartment(floorArea, address.get(i), titles.get(i), nbBedrooms, nbSleeping, nbBathrooms, floorAreaTerrace, pricePerNight, nbMinNight, terrace);
			 File f = new File("src/test/resources/io/github/oliviercailloux/y2018/apartments/readApartments/Apartment" + i + ".xml");
			 try(FileOutputStream s = new FileOutputStream(f.getAbsolutePath()))
			 {
				 j.toXML(a, s);
				 s.close();
			 }
		}	
	}	
	/**
	 * This is the main function
	 * 
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws DOMException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws DOMException, IllegalArgumentException, IllegalAccessException, IOException  {
	generateRandomXML();
	}
}
