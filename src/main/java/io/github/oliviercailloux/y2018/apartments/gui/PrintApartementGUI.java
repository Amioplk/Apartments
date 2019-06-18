package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.FileInputStream;
import java.io.IOException;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;
import io.github.oliviercailloux.y2018.apartments.toxmlproperties.XMLProperties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
//import org.eclipse.swt.widgets.Shell;
//import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;


/**
 * @author AITALIBRAHAM & SAKHO
 * 
 */
public class PrintApartementGUI {
	/**
	 * This class aims to print apartments to the users
	 */
	//private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);

	/**
	 * @param filename : the file describing apartments should be given in
	 *                 parameters "XML format"
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */


	
	/**
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public static void main(String args[]) throws IllegalArgumentException, IOException {

		Display display = new Display();
	       Shell shell = new Shell(display);
	       shell.setText("SWT List (o7planning.org)");
	       shell.setSize(700, 1000);
	 
	       RowLayout layout = new RowLayout(SWT.VERTICAL);
	       layout.spacing = 10;
	       layout.marginHeight = 10;
	       layout.marginWidth = 10;
	 
	       shell.setLayout(layout);
	       
	       

	       
	       
	 
	       // Create a List
	       // (Allows selecte multiple lines and display vertical scroll bar.).
	       final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	       list.setLayoutData(new RowData(240, 100));
	       
	       
	       final String [] titres= new String[100];
	       
	       for(int i= 0; i<=9; i++) {
	    	   //titres[i]= "titre "+ i;
	    	   //titres= apps[i].getTitle();
	    	   list.add("titre "+titres);
	       }
	       
	       
	 
	       Label label = new Label(shell, SWT.NONE);
	       label.setLayoutData(new RowData(240, SWT.DEFAULT));
	 
	       list.addSelectionListener(new SelectionAdapter() {
	 
	           @Override
	           public void widgetSelected(SelectionEvent event) {
	               int[] selections = list.getSelectionIndices();
	               String outText = "";
	               for (int i = 0; i < selections.length; i++) {
	                   outText += selections[i] + " ";
	               }
	               label.setText("You selected: " + outText);
	           }
	 
	       });
	 
	       shell.open();
	       while (!shell.isDisposed()) {
	           if (!display.readAndDispatch())
	               display.sleep();
	       }
	       display.dispose();
	   }
	

	

}
