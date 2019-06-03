package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.FileInputStream;
import java.io.IOException;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author AITALIBRAHAM & SAKHO
 *  
 */
public class PrintApartementGUI {
	/**
	 *  This class aims to print an apartment to the users 
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);
	
	/**
	 * @param filename : the file describing apartments should be given in parameters "XML format"
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	
	public Apartment appar;
	protected static Display display = new Display();
	protected static Shell shell = new Shell(display);
	
	public PrintApartementGUI() throws IOException, IllegalArgumentException, IllegalAccessException {
		this.appar = new Apartment(20.0 , "20 rue des consé" , "Test Apartment" );

	}
	
	public PrintApartementGUI(String fileName) throws IOException, IllegalArgumentException, IllegalAccessException {
		ReadApartmentsXMLFormat xmlReader= new ReadApartmentsXMLFormat();
		FileInputStream fileinputstream = new FileInputStream(fileName); 
		this.appar = xmlReader.readApartment(fileinputstream);
		
		LOGGER.info("Apratement has been loaded ");
		//lecture d'un appartement au format xml
		
		
		
	}
	
	/**
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public static void main(String args[]) throws IllegalArgumentException, IllegalAccessException, IOException{
		
		
		@SuppressWarnings("unused")
		//PrintApartementGUI prtApp = new PrintApartementGUI("/home/aissatou/PROJETJAVA/Apartments/src/main/java/io/github/oliviercailloux/y2018/apartments/gui/testXML.xml");
		PrintApartementGUI prtApp = new PrintApartementGUI("/home/aissatou/PROJETJAVA/Apartments/src/main/java/io/github/oliviercailloux/y2018/apartments/gui/testXML.xml");
		
                LOGGER.info("Test Apartment has been created");
		Label lbl = new Label(shell, SWT.CENTER);
		lbl.setText("here are alll the apartments");
		lbl.pack();
		
		shell.setText("Apartments");
                shell.setMinimumSize(1000, 500);
		shell.setSize(1000, 1500);
		shell.pack();
		shell.open();
		
		while(!shell.isDisposed( )){
			if(!display.readAndDispatch( ))
				display.sleep( );
		}
		lbl.dispose();
		display.dispose();
		
	}
	
}
