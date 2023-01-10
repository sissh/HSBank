package utils;
import java.util.ArrayList;
import java.util.Random;

import components.*;

public class Generators {
	//1.1.2 Creation of table of clients
	public static ArrayList<Client> generateClients(int amount) {
		ArrayList<Client> clients = new ArrayList();;
		for (int i = 0; i < amount; i++) {
			Client client = new Client("testName"+i, "testFirstName"+i);
			clients.add(client);
		}
		return clients;
	}
	
	//1.2.3 Creation of the table of accounts
	public static ArrayList<Accounts> generateAccounts(ArrayList<Client> clients) {
		Random rand = new Random();
		ArrayList<Accounts> accounts = new ArrayList();
		clients.stream().forEach(client -> accounts.add(new CurrentAccount("label", client)));
		accounts.stream().forEach(client -> client.setBalance(rand.nextDouble(-100000, 100000)));
		return accounts;
	}
}
