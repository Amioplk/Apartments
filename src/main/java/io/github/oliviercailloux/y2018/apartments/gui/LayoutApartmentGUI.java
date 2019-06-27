package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.toxmlproperties.XMLProperties;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearValueFunction;

import org.eclipse.swt.layout.*;

/**
 * @author AlWAZZAN & SAKHO
 * 
 */

/**
 * this class displays a list of apartments sorted according to the user's
 * utilities
 */

public class LayoutApartmentGUI {

	private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);

	static Display display;
	static Shell shell;

	/**
	 * This is the main function
	 * 
	 * @param args
	 * @throws IllegalAccessException
	 * @throws DOMException
	 * @throws IOException
	 */
	public static void main(String[] args) throws DOMException, IllegalAccessException, IOException {
		ApartmentValueFunction avf = new ApartmentValueFunction();
		avf.setFloorAreaValueFunction(new LinearValueFunction(0, 300));

		ArrayList<Apartment> listap = getListSorted(avf);
		displayAppart(listap);
	}

	/**
	 * General method which displays all the sorted apartment
	 * 
	 * @param listApp the list of apartments to display
	 * @throws DOMException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */

	public static void displayAppart(ArrayList<Apartment> listApp) {
		Label adresse;
		Label surface;
		Label prix;
		Label nbrChambres;
		Canvas photoCanevas;

		// define a display
		display = new Display();
		shell = new Shell(display);
		shell.setText("Sélection d'un appartement");

		// create a gridLayout of 2 columns
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);

		// create a griddata for a list of objects
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;

		// create a list label
		Label label = new Label(shell, SWT.NULL);
		label.setText("Liste des appartements disponibles :");
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		new Label(shell, SWT.NULL);

		// define griddata with a verticalspan : 3 rows
		new Label(shell, SWT.NULL);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 3;

		// add objects of apartments in a list
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);

		for (Apartment a : listApp) {
			System.out.println("Appart : " + a);
			list.add("Titre: " + a.getTitle() + "\t" + " Adresse : " + a.getAddress());
		}

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.verticalSpan = 4;
		gridData.heightHint = 400;
		list.setLayoutData(gridData);

		Group appartInfo = new Group(shell, SWT.NULL);
		appartInfo.setText("Détail sur l'appartement sélectionné :");

		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		appartInfo.setLayout(gridLayout);

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		appartInfo.setLayoutData(gridData);

		// beginning of creation of labels
		new Label(shell, SWT.NULL).setText("Photo : ");
		photoCanevas = new Canvas(shell, SWT.BORDER);

		new Label(appartInfo, SWT.NULL).setText("Adresse :");
		adresse = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		adresse.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Surface :");
		surface = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		surface.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Prix :");
		prix = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		prix.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Nombre de Chambres :");
		nbrChambres = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		nbrChambres.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.horizontalSpan = 3;

		// on click on an apartment
		list.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				int[] selectedItems = list.getSelectionIndices();

				for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++) {
					adresse.setText(listApp.get(list.getSelectionIndex()).getAddress());
					surface.setText(" " + listApp.get(list.getSelectionIndex()).getFloorArea());
					prix.setText(" " + listApp.get(list.getSelectionIndex()).getPricePerNight());
					nbrChambres.setText(" " + listApp.get(list.getSelectionIndex()).getNbBedrooms());
				}
			}
		});

		shell.setSize(1000, 550);
		shell.open();
		LOGGER.info("The Shell was opened with success.");

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		LOGGER.info("The screen was closed with success.");

		// photos to be added lately
		if (photoCanevas != null) {
			photoCanevas.dispose();
		}
	}

	/**
	 * Method that creates an apartment list randomly and sort it
	 * 
	 * @param avf a way to rate the apartments
	 * @throws DOMException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */

	public static ArrayList<Apartment> getListSorted(ApartmentValueFunction avf)
			throws IOException, IllegalAccessException {
		ArrayList<Apartment> appart = new ArrayList<Apartment>();
		for (int i = 0; i < 50; ++i) {
			Apartment a = XMLProperties.generateRandom(); 
			appart.add(a);
		}

		// trier la liste selon l'utilité
		appart.sort(
				(Apartment c, Apartment d) -> (int) ((avf.getSubjectiveValue(c) - avf.getSubjectiveValue(d)) * 100000));

		return appart;

	}

}