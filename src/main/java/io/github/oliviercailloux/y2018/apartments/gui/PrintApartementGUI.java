package io.github.oliviercailloux.y2018.apartments.gui;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
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
	}

	public PrintApartementGUI(String fileName) throws IOException, IllegalArgumentException, IllegalAccessException {
		if (display == null ){
			setDisplayApartment();
		}
		ReadApartmentsXMLFormat xmlReader = new ReadApartmentsXMLFormat();
		FileInputStream fileinputstream = new FileInputStream(fileName);
		this.appar = xmlReader.readApartment(fileinputstream);

		LOGGER.info("Apratement has been loaded ");

	}

	/**
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public static void main(String args[]) throws IllegalArgumentException, IllegalAccessException, IOException {

		@SuppressWarnings("unused")
		// PrintApartementGUI prtApp = new
		// PrintApartementGUI("/home/aissatou/PROJETJAVA/Apartments/src/main/java/io/github/oliviercailloux/y2018/apartments/gui/testXML.xml");
		PrintApartementGUI prtApp = new PrintApartementGUI();
		
		LOGGER.info("Test Apartment has been created");
		
		prtApp.appar.addImages("118021148.jpg");
	
		
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
		
		ArrayList<String> listImage = printAppartmentGui.appar.getImages();
		Image image;
		LOGGER.info(""+getClass().getClassLoader().getResourceAsStream(listImage.get(0)));
		image = new Image (display, getClass().getClassLoader().getResourceAsStream(listImage.get(0)));
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
		imageLabel.setLocation(25, 145);
		imageLabel.setSize(400 , 300);
		
		title.setFont( new Font(display,"Calibri", 24, SWT.COLOR_BLACK ));
		pricePerNight.setFont(new Font(display,"Calibri", 28, SWT.COLOR_DARK_GREEN));
		pricePerNight.setForeground(new Color(printAppartmentGui.display, 100,150,80));
		florArea.setFont( new Font(display,"Calibri", 16 , SWT.COLOR_BLACK ));
		adress.setFont( new Font(display,"Calibri", 16 , SWT.COLOR_BLACK ));
		imageLabel.setImage(image);
		
		title.pack();
		pricePerNight.pack();
		florArea.pack();
		adress.pack();
		imageLabel.pack();
		
		
		
	}

}
