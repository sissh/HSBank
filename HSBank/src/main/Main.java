package main;
import java.util.ArrayList;
import java.util.HashMap;

import components.*;
import utils.*;

//1.1.2 Creation of main class for tests
public class Main {
	public static void main (String[]args) {
		ArrayList<Client> clients = Generators.generateClients(10);
		//Displays.displayClients(clients);
		
		//1.2.3 Creation of the table of accounts
		ArrayList<Accounts> accounts = Generators.generateAccounts(clients);
		//Displays.displayAccounts(accounts);
		
		//1.3.1 Using hashmaps for accounts
		HashMap<Integer, Accounts> hashAccounts = Generators.convertToHashmap(accounts);
		Displays.displayHashAccounts(hashAccounts, true);
	}
}
