package utils;
import java.util.ArrayList;
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

}
