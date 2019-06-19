package io.github.oliviercailloux.y2018.apartments.gui;


import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


import org.w3c.dom.DOMException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.List;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.toxmlproperties.XMLProperties;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author ZEINA & SAKHO
 * 
 */
public class LayoutApartmentGUI {
	/**
	 * This class aims to print apartments to the users
	 */
	//private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);

	/**
	 * @param filename : the file describing apartments should be given in
	 *                 parameters "XML format"
	 * @return 
	 * @return 
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws DOMException 
	 */

	public static void main(String args[])throws IllegalArgumentException, IOException, DOMException, IllegalAccessException {
		Display display = new Display();
	       Shell shell = new Shell(display);
	       shell.setText("SWT List (o7planning.org)");
	       shell.setSize(700, 1000);
	       Label label1 = new Label(shell, SWT.NONE);
	       label1.setText("Nos Apartements");
	       label1.setSize(100,25);
	       GridLayout gridLayout = new GridLayout();
           gridLayout.numColumns = 3;
           shell.setLayout(gridLayout);
	       
	       RowLayout layout = new RowLayout(SWT.VERTICAL);
	       layout.spacing = 50;
	       layout.marginHeight = 40;
	       layout.marginWidth = 40;
	       shell.setLayout(layout);
	       
	       
	       //Create a List
	       //(Allows select multiple lines and display vertical scroll bar.).
	       final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	       list.setLayoutData(new RowData(700, 500));
	       
	     
	       ArrayList<Apartment> appart = new ArrayList<Apartment>(10);
			 for(int i = 0; i < 100; ++i) {
			  Apartment a = XMLProperties.generateRandomXML();
			  appart.add(a);
			 }
			 
			 for(Apartment a : appart) {
			//shell.setLayoutData(new GridLayout(3,4));
			  System.out.println("Appart : " + a);
			  list.add(a.getTitle() + "    "+ a.getAddress());
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
