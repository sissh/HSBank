package main;
import java.util.ArrayList;

import components.*;
import utils.*;

//1.1.2 Creation of main class for tests
public class Main {
	public static void main (String[]args) {
		ArrayList<Client> clients = Generators.generateClients(10);
		Displays.displayClients(clients);
	}
}
