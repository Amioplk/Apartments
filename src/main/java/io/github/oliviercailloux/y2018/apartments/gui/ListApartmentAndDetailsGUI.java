package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearValueFunction;

/**
 * @author AIT ALI BRAHAM
 * this class aims to print a list of apartment to the user, then on the click, it allows a better description with images
 */
public class ListApartmentAndDetailsGUI {
	
	private ArrayList<Apartment> listApartment;
	@SuppressWarnings("unused")
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);
	private ApartmentValueFunction avf;
	/**
	 * @param listApartment the list of apartments we have
	 */
	public ListApartmentAndDetailsGUI( ArrayList<Apartment> listApartment, ApartmentValueFunction avf){
		this.setListApartment(listApartment);
		this.avf = avf;
	}
	
	/**
	 * In this case we create a random list 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 */
	public ListApartmentAndDetailsGUI() throws IllegalAccessException, IOException{
		this.avf = new ApartmentValueFunction();
		avf.setFloorAreaValueFunction(new LinearValueFunction(0, 300));

		this.setListApartment(LayoutApartmentGUI.getListSorted(avf));
	}

	
	/**
	 * Aims to print the list
	 */
	public void viewList(){
		LayoutApartmentGUI.displayAppart(this.listApartment);
	}
	
	public ArrayList<Apartment> getListApartment() {
		return listApartment;
	}

	public void setListApartment(ArrayList<Apartment> listApartment) {
		this.listApartment = listApartment;
	}
	
	public void main(String args[]) throws IllegalAccessException, IOException{
		ListApartmentAndDetailsGUI listGUI = new ListApartmentAndDetailsGUI();
		listGUI.viewList();
	}
	
	
}
