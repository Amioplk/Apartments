package io.github.oliviercailloux.y2018.apartments.gui;

import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import org.w3c.dom.DOMException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
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
	// private final static Logger LOGGER =
	// LoggerFactory.getLogger(CreateApartmentGUI.class);

	/**
	 * @param filename :
	 * @return
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws DOMException
	 */

	public static void main(String args[])
			throws IllegalArgumentException, IOException, DOMException, IllegalAccessException {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("SWT List Apartements");
		shell.setSize(1500, 700);
		GridLayout gridLayout = new GridLayout(3, false);
		shell.setLayout(gridLayout);

		GridData gridlabel = new GridData();
		gridlabel.widthHint = 500;
		gridlabel.horizontalSpan = 1;

		Label label1 = new Label(shell, SWT.NONE);
		label1.setText("Nos Apartements");
		label1.setLayoutData(gridlabel);
		// RowLayout layout = new RowLayout(SWT.VERTICAL);
		// layout.spacing = 50;
		// layout.marginHeight = 40;
		// layout.marginWidth = 40;
		// shell.setLayout(layout);

		// Create a List
		// (Allows select multiple lines and display vertical scroll bar.).
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		
		GridData gridDatalist = new GridData();
		gridDatalist.horizontalSpan = 1;
		gridDatalist.grabExcessHorizontalSpace = true;
		gridDatalist.grabExcessVerticalSpace = true;
		gridDatalist.widthHint = 500;
		gridDatalist.heightHint = 500;
		list.setLayoutData(gridDatalist);

		Label label = new Label(shell, SWT.NONE);
		label.setLayoutData(gridlabel);

		ArrayList<Apartment> appart = new ArrayList<Apartment>(10);
		for (int i = 0; i < 100; ++i) {
			Apartment a = XMLProperties.generateRandomXML();
			appart.add(a);
		}

		for (Apartment a : appart) {
			// shell.setLayoutData(new GridLayout(3,4));
			System.out.println("Appart : " + a);
			list.add(a.getTitle() + "    " + a.getAddress() + "  " + a.getFloorArea());
			// list.add(a.toString());
		}

		list.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				// int[] selections = list.getSelectionIndices();
				// appart.get(list.getSelectionIndex());
				String outText = "";
				// for (int i = 0; i < selections.length; i++) {
				// outText += selections[i] + " ";
				// }
				label.setText("You selected: " + appart.get(list.getSelectionIndex()).getTitle());
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
