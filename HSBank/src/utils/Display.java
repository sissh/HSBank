package utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import components.*;


public class Display {
	//1.1.2 Creation of table of clients
	public static void displayClients(ArrayList<Client> clients) {
		clients.stream().forEach(System.out::println);
	}
	
	//1.2.3 Creation of the table of accounts
	public static void displayAccounts(ArrayList<Account> accounts) {
		accounts.stream().forEach(System.out::println);
	}
	
	//1.3.1 Using hashmaps for accounts
	public static void displayHashAccounts(HashMap<Integer, Account> accounts) {
		accounts.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
	}
	
	//same method as above, but allows for descending order if specified.
	public static void displayHashAccounts(HashMap<Integer, Account> accounts, boolean desc) {
		if (desc) {
			accounts.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);
		} else {
			accounts.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
		}
	}

}
