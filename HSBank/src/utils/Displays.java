package utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import components.*;


public class Displays {
	//1.1.2 Creation of table of clients
	public static void displayClients(ArrayList<Client> clients) {
		clients.stream().forEach(System.out::println);
	}
	
	//1.2.3 Creation of the table of accounts
	public static void displayAccounts(ArrayList<Accounts> accounts) {
		accounts.stream().forEach(System.out::println);
	}
	
	//1.3.1 Using hashmaps for accounts
	public static void displayHashAccounts(HashMap<Integer, Accounts> accounts, boolean desc) {
		if (desc) {
			accounts.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);
		} else {
			accounts.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
		}
	}

}
