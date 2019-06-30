
package io.github.oliviercailloux.y2018.apartments.gui;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author AITALIBRAHAM & SAKHO
 * 
 */
public class ShowApartementGUI {
	/**
	 * This class aims to print an apartment to the users
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);

	private int i;
	public Apartment appar;
	protected static Display display; 
	protected static Shell shell;

	public static void setDisplayApartment(){
		display = new Display();
		shell = new Shell(display);

		shell.setText("Apartments");
		shell.setMinimumSize(700, 800);
		shell.setSize(1000, 1500);
		shell.pack();
		shell.open();
	}
	
	public ShowApartementGUI() throws IOException, IllegalArgumentException, IllegalAccessException {
		if (display == null ){
			setDisplayApartment();
		}
		this.appar = new Apartment(20.0, "20 rue des consé", "Test Apartment pour ta mère, mais pas grave");
		this.i = 0;
	}

	public ShowApartementGUI(String fileName) throws IOException, IllegalArgumentException, IllegalAccessException {
		if (display == null ){
			setDisplayApartment();
		}
		ReadApartmentsXMLFormat xmlReader = new ReadApartmentsXMLFormat();
		LOGGER.info(fileName);
		
		InputStream f = ShowApartementGUI.class.getResourceAsStream(fileName);
		this.appar = xmlReader.readApartment(f);
		
		

		
		this.appar.setImages(findOutImagesPaths(this.appar.getImagesFolder()));
		this.i = 0;
		LOGGER.info("Apratement has been loaded ");
		LOGGER.info(this.appar.getImages().toString());

	}
	
	public ShowApartementGUI(Apartment appart) throws IOException {
		if (shell == null){
			setDisplayApartment();
		}
		this.appar = appart;
		this.i = 0;
		this.appar.setImages(findOutImagesPaths(this.appar.getImagesFolder()));
	}

	/**
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public static void main(String args[]) throws IllegalArgumentException, IllegalAccessException, IOException {

		ShowApartementGUI prtApp = new ShowApartementGUI("ApartmentA.xml");
		
		LOGGER.info("Test Apartment has been created");
		
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
		
		prtApp.setWindow(prtApp);
		display.dispose();
	}
	

	/**
	 * @param appar
	 * this function aims to design and complete the window with all the information of the apartment
	 * It also shows an image of the apartment, and let the user navigate between the available images
	 */
	@SuppressWarnings("unused")
	public void setWindow(ShowApartementGUI showAppartmentGui) {
		Label title = new Label(shell, SWT.CENTER);
		Label adress = new Label(shell, SWT.CENTER);
		Label florArea = new Label(shell, SWT.CENTER);
		Label wifi = new Label(shell, SWT.CENTER);
		Label pricePerNight = new Label(shell, SWT.CENTER);
		Label imageLabel = new Label(shell, SWT.BORDER);
		
		//Load all the images related to the apartment
		ArrayList<Path> listImage = showAppartmentGui.appar.getImages();
		
		
		LOGGER.info("la liste des images : " + listImage.toString());
		Image image;
		
		if (listImage.size() > 0){
			image = new Image (display, ShowApartementGUI.class.getResourceAsStream(showAppartmentGui.appar.getImagesFolder()+"/"+listImage.get(0)));
			GC gc = new GC(image);

			imageLabel.setImage(image);
			image = resize (image, 500, 500);
			imageLabel.setImage(image);
			imageLabel.pack();
			imageLabel.setLocation(50, 160);
			imageLabel.setSize(100, 100);
		}
		
		
		
		title.setText(showAppartmentGui.appar.getTitle());
		adress.setText("adress : " + showAppartmentGui.appar.getAddress());
		florArea.setText(showAppartmentGui.appar.getFloorArea() + " m2 ");
		wifi.setText("wifi : " + showAppartmentGui.appar.getWifi() + "");
		pricePerNight.setText(showAppartmentGui.appar.getPricePerNight() + " euros/night");
		
		
		title.setLocation(25, 10);
		pricePerNight.setLocation(25 , 50 );
		florArea.setLocation(25, 110);
		adress.setLocation(25, 125);
		title.setFont( new Font(display,"Calibri", 24, SWT.COLOR_BLACK ));
		pricePerNight.setFont(new Font(display,"Calibri", 28, SWT.COLOR_DARK_GREEN));
		pricePerNight.setForeground(new Color(ShowApartementGUI.display, 100,150,80));
		florArea.setFont( new Font(display,"Calibri", 16 , SWT.COLOR_BLACK ));
		adress.setFont( new Font(display,"Calibri", 16 , SWT.COLOR_BLACK ));
		
		

		
	    final Button button1 = new Button(shell, SWT.BUTTON4);
	    button1.setText("next");
	    button1.setLocation(550,330);
	    button1.setSize(new Point(20,500));
	   
	  
	  
	    title.pack();
		pricePerNight.pack();
		florArea.pack();
		adress.pack();
		
	    
	    Label label = new Label(shell, 0);
	    
	   button1.addSelectionListener(new SelectionAdapter() {
		   public void widgetSelected(SelectionEvent arg0) {
			   if (i < listImage.size()-1) i++;
			   else 
				   i = 0;
			   if(listImage.size() > 0){
			   Image image = new Image (display, ShowApartementGUI.class.getResourceAsStream(showAppartmentGui.appar.getImagesFolder()+"/"+listImage.get(i)));
			   image = resize (image, 500, 500);
			   
			   imageLabel.setImage(image);
			   imageLabel.pack();
			   button1.pack();
			   }
		   }
		 
		   
	});
	   button1.moveAbove(imageLabel);
	   button1.pack();
	
	   
	}
		
	
	
// funtion to resize to change the size of an image 
// source : https://www.aniszczyk.org/2007/08/09/resizing-images-using-swt/
	
	   public static Image resize(Image image, int width, int height) {
		   Image scaled = new Image(Display.getDefault(), width, height);
		   GC gc = new GC(scaled);
		   gc.setAntialias(SWT.ON);
		   gc.setInterpolation(SWT.HIGH);
		   gc.drawImage(image, 0, 0,image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		   gc.dispose();
		   image.dispose(); // don't forget about me!
		   return scaled;
		 }
	   
	
	/**
	 * @param the Path of the folder which contains Images related to the Apartment described in the XML file
	 * @return ArrayList of strings, each string is the path of one image related to the apartment
	 */

	private ArrayList<Path> findOutImagesPaths(Path path) throws IOException{
		
		try {
			
			File file = new File(ShowApartementGUI.class.getResource(path.toString()).getFile());
			
			LOGGER.info("Folder path has been set : " + file );
			String liste[] = file.list();
	        ArrayList<Path> finalList  = new ArrayList<Path>();
	        for (String str : liste){
	        	finalList.add(Paths.get(str));
	        	LOGGER.info("a new path to another Image has been set : " + str);
	        }
	        return finalList;
			
		}
	    catch(Exception e) {
	    	LOGGER.info("le fichier " + path + "est introuvabe"); 
	    	e.printStackTrace();
	    }
		return new ArrayList<Path>();
		
	}

}
