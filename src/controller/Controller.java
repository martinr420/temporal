package controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.data_structures.Nodo;
import model.data_structures.noExisteObjetoException;
import model.logic.Model;
import model.logic.Multa;
import view.View;

public class Controller {


	// -------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------

	/**
	 * A model.
	 */
	private Model modelo;

	/**
	 * A view.
	 */
	private View view;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	/**
	 * Creates the project view and the project model
	 */
	public Controller() {
		modelo = new Model();
		view = new View();
	}

	// -------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------

	/**
	 * Prints the user options and updates the view using the model.
	 *
	 * @throws InputMismatchException If the user inputs an incorrect number sequence.
	 * @throws noExisteObjetoException 
	 */
	public void run() throws InputMismatchException, noExisteObjetoException {
		try {
			modelo.cargarDatos();
			Scanner reader = new Scanner(System.in);
			boolean end = false;

			while (!end) {
				view.displayMenu();
				
				int option = reader.nextInt();
				switch (option) {

				case 0:
					
					
					modelo.darInfoCargaDatos();

					
					
					
					break;

				case 1:
					// Display option 1
					view.displayOp1Menu();
					
					
					
					break;

				case 2:
					view.displayOp2Menu();
					
					
					

					break;
					
				case 3:
					view.displayOp3Menu();
				
					view.displayOp3Menu();
					
					break;
					
					
				case 4:
					view.displayOp4Menu();
					
					view.displayOp4Menu();
					break;

					// Invalid option
				default:
					view.badOption();
					end = true;
					break;
				}
			}
		} 
		catch (InputMismatchException e)
		{
			run();
		}
	}

}
