package utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import components.*;

public class Generators {
	//1.1.2 Creation of table of clients
	public static ArrayList<Client> generateClients(int amount) {
		ArrayList<Client> clients = new ArrayList<Client>();;
		for (int i = 0; i < amount; i++) {
			Client client = new Client("testName"+i, "testFirstName"+i);
			clients.add(client);
		}
		return clients;
	}
	
	//1.2.3 Creation of the table of accounts
	public static ArrayList<Accounts> generateAccounts(ArrayList<Client> clients) {
		Random rand = new Random();
		ArrayList<Accounts> accounts = new ArrayList<Accounts>();
		clients.stream().forEach(client -> accounts.add(new CurrentAccount("label", client)));
		accounts.stream().forEach(account -> account.setBalance(rand.nextDouble(-100000, 100000)));
		return accounts;
	}
	
	//1.3.1 Using hashmaps for accounts
	public static HashMap<Integer, Accounts> convertToHashmap(ArrayList<Accounts> accounts) {
		HashMap<Integer, Accounts> newAccounts = new HashMap<Integer, Accounts>();
		accounts.stream().forEach(account -> newAccounts.put(account.getAccountNumber(), account));
		return newAccounts;
	}
}
