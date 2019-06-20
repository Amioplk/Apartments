
package io.github.oliviercailloux.y2018.apartments.gui;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author AITALIBRAHAM & SAKHO
 * 
 */
public class PrintApartementGUI {
	/**
	 * This class aims to print an apartment to the users
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);

	private int i;
	/**
	 * @param filename : the file describing apartments should be given in
	 *                 parameters "XML format"
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */

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
	
	public PrintApartementGUI() throws IOException, IllegalArgumentException, IllegalAccessException {
		if (display == null ){
			setDisplayApartment();
		}
		this.appar = new Apartment(20.0, "20 rue des consé", "Test Apartment pour ta mère, mais pas grave");
		this.i = 0;
	}

	public PrintApartementGUI(String fileName) throws IOException, IllegalArgumentException, IllegalAccessException {
		if (display == null ){
			setDisplayApartment();
		}
		ReadApartmentsXMLFormat xmlReader = new ReadApartmentsXMLFormat();
		InputStream inputstream = PrintApartementGUI.class.getResourceAsStream(fileName);
		this.appar = xmlReader.readApartment(inputstream);
		this.appar.setImages(findOutImagesPaths(this.appar.getImagesFloder()));
		this.i = 0;
		LOGGER.info("Apratement has been loaded ");
		LOGGER.info(this.appar.getImages().toString());

	}

	/**
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public static void main(String args[]) throws IllegalArgumentException, IllegalAccessException, IOException {

		@SuppressWarnings("unused")
		PrintApartementGUI prtApp = new PrintApartementGUI("apartTest.xml");
		
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
	public void setWindow(PrintApartementGUI printAppartmentGui) {
		Label title = new Label(shell, SWT.CENTER);
		Label adress = new Label(shell, SWT.CENTER);
		Label florArea = new Label(shell, SWT.CENTER);
		Label wifi = new Label(shell, SWT.CENTER);
		Label pricePerNight = new Label(shell, SWT.CENTER);
		Label imageLabel = new Label(shell, SWT.BORDER);
		
		//Load all the images related to the apartment
		ArrayList<String> listImage = printAppartmentGui.appar.getImages();
		
		
		LOGGER.info("la liste des images : " + listImage.toString());
		Image image;
		image = new Image (display, PrintApartementGUI.class.getResourceAsStream(printAppartmentGui.appar.getImagesFloder()+"/"+listImage.get(0)));
		GC gc = new GC(image);
		
		
		
		title.setText(printAppartmentGui.appar.getTitle());
		adress.setText("adress : " + printAppartmentGui.appar.getAddress());
		florArea.setText(printAppartmentGui.appar.getFloorArea() + " m2 ");
		wifi.setText("wifi : " + printAppartmentGui.appar.getWifi() + "");
		pricePerNight.setText(printAppartmentGui.appar.getPricePerNight() + " euros/night");
		
		
		title.setLocation(25, 10);
		pricePerNight.setLocation(25 , 50 );
		florArea.setLocation(25, 110);
		adress.setLocation(25, 125);
		imageLabel.setLocation(50, 160);
		imageLabel.setSize(100, 100);
		title.setFont( new Font(display,"Calibri", 24, SWT.COLOR_BLACK ));
		pricePerNight.setFont(new Font(display,"Calibri", 28, SWT.COLOR_DARK_GREEN));
		pricePerNight.setForeground(new Color(printAppartmentGui.display, 100,150,80));
		florArea.setFont( new Font(display,"Calibri", 16 , SWT.COLOR_BLACK ));
		adress.setFont( new Font(display,"Calibri", 16 , SWT.COLOR_BLACK ));
		
		

		imageLabel.setImage(image);
		image = resize (image, 500, 500);
		imageLabel.setImage(image);
		
	    final Button button1 = new Button(shell, SWT.BUTTON4);
	    button1.setText("next");
	    button1.setLocation(550,330);
	    button1.setSize(new Point(20,500));
	   
	  
	  
	    title.pack();
		pricePerNight.pack();
		florArea.pack();
		adress.pack();
		imageLabel.pack();
	    
	    Label label = new Label(shell, 0);
	    
	   button1.addSelectionListener(new SelectionAdapter() {
		   public void widgetSelected(SelectionEvent arg0) {
			   if (i < listImage.size()-1) i++;
			   else 
				   i = 0;
			   Image image = new Image (display, getClass().getClassLoader().getResourceAsStream(printAppartmentGui.appar.getImagesFloder()+"/"+listImage.get(i)));
			   image = resize (image, 500, 500);
			   
			   imageLabel.setImage(image);
			   imageLabel.pack();
			   button1.pack();
		   }
		 
		   
	});
	   button1.moveAbove(imageLabel);
	   button1.pack();
	
	   
	}
		
	
	

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
	 * @param FolderPath : the Path of the folder which contains Images related to the Apartment described in the XML file
	 * @return ArrayList of strings, each string is the path of one image related to the apartment
	 */

	@SuppressWarnings("unused")
	private ArrayList<String> findOutImagesPaths(String folderPath) throws IOException{
		
		try {
			LOGGER.info(folderPath);
			File file = new File(PrintApartementGUI.class.getResource(folderPath).getFile());
			
			LOGGER.info("Folder path has been set : " + file );
			String liste[] = file.list();
	        ArrayList<String> finalList  = new ArrayList<String>();
	        for (String str : liste){
	        	finalList.add(str);
	        	LOGGER.info("a new path to another Image has been set : " + str);
	        }
	        return finalList;
			
		}
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
		return null;
		
	}

}
