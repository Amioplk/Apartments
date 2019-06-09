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

	private Apartment apar;
	protected Display display = new Display();
	protected Shell shell = new Shell(display);

	public PrintApartementGUI() {
		this.setApar(new Apartment(20.0, "20 rue des consé", "Test Apartment"));

	}

	/**
	 * Create a printer of an apartment given in xml format
	 * @param fileName
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public PrintApartementGUI(String fileName) throws IOException, IllegalArgumentException {
		ReadApartmentsXMLFormat xmlReader = new ReadApartmentsXMLFormat();
		FileInputStream fileinputstream = new FileInputStream(fileName);
		this.setApar(xmlReader.readApartment(fileinputstream));

		LOGGER.info("Apratement has been loaded ");

	}

	public void print() {
		
		LOGGER.info("Test Apartment has been created");
		Label title = new Label(shell, SWT.CENTER);
		Label adress = new Label(shell, SWT.CENTER);
		Label florArea = new Label(shell, SWT.CENTER);
		Label wifi = new Label(shell, SWT.CENTER);

		title.setText(getApar().getTitle());
		adress.setText("adress : " + this.getApar().getAddress());
		florArea.setText("floor Area : " + this.getApar().getFloorArea() + " m2 ");
		wifi.setText("wifi : " + this.getApar().getWifi() + "");

		wifi.pack();
		florArea.pack();
		adress.pack();
		title.pack();

		florArea.setLocation(100, 60);
		adress.setLocation(100, 90);
		wifi.setLocation(100, 120);
		title.setLocation(100, 20);

		shell.setText("Apartments");
		shell.setMinimumSize(1000, 500);
		shell.setSize(1000, 1500);
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		title.dispose();
		display.dispose();

	}
	
	/**
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public static void main(String args[]) throws IllegalArgumentException, IOException {

//		Exemple : 
		 PrintApartementGUI prtApp = new PrintApartementGUI("src/test/resources/io/github/oliviercailloux/y2018/apartments/gui/apartTest.xml");
//		PrintApartementGUI prtApp = new PrintApartementGUI();
		prtApp.print();
		
	}

	public Apartment getApar() {
		return apar;
	}

	public void setApar(Apartment apar) {
		this.apar = apar;
	}

}
