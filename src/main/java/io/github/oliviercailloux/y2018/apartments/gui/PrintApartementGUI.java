package io.github.oliviercailloux.y2018.apartments.gui;



import java.awt.Window;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.omg.CORBA.portable.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author AITALIBRAHAM & SAKHO
 * 
 */
public class PrintApartementGUI {
	
	
	
	   
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
	private int i = 0;
	
	public static void setDisplayApartment() {
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
		
		prtApp.appar.addImages("image.jpg");
		prtApp.appar.addImages("image2.jpg");
		
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
	    button1.setLocation(550,500);
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
			   Image image = new Image (display, getClass().getClassLoader().getResourceAsStream(listImage.get(i)));
			   image = resize (image, 500, 500);
			   
			   imageLabel.setImage(image);
			   imageLabel.pack();
			   button1.pack();
		   }
		 
		   
	});
	   button1.moveAbove(imageLabel);
	   button1.pack();
	
	   
	}

	

}
