package utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import components.*;

public class Operation {
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
	public static ArrayList<Account> generateAccounts(ArrayList<Client> clients) {
		Random rand = new Random();
		ArrayList<Account> accounts = new ArrayList<Account>();
		clients.stream().forEach(client -> accounts.add(new CurrentAccount("label", client)));
		clients.stream().forEach(client -> accounts.add(new SavingsAccount("label", client)));
		accounts.stream().forEach(account -> account.setBalance(rand.nextDouble(0, 20)));
		return accounts;
	}
	
	//1.3.1 Using hashmaps for accounts
	public static HashMap<Integer, Account> convertToHashmap(ArrayList<Account> accounts) {
		HashMap<Integer, Account> newAccounts = new HashMap<Integer, Account>();
		accounts.stream().forEach(account -> newAccounts.put(account.getAccountNumber(), account));
		return newAccounts;
	}
	
	//1.3.5 Updating Accounts
	public static HashMap<Integer, Account> updateAccounts(ArrayList<Flow> flows, HashMap<Integer, Account> accounts) {
		flows.stream().forEach(flow -> {
			if (flow instanceof Transfer transfer) {
				accounts.get(transfer.getIssuerAccountNumber()).setBalance(flow);
				accounts.get(transfer.getTargetAccountNumber()).setBalance(flow);
			} else {
				accounts.get(flow.getTargetAccountNumber()).setBalance(flow);
			}
		});
		return accounts;
	}
	
	public static String checkNegativeAccounts(HashMap<Integer, Account> accounts) {
		String message = "No negative balance.";
		
		ArrayList<Account> negativeAccounts = new ArrayList<>();
		accounts.values().stream().forEach(account -> {
			if (account.getBalance() < 0) {
				negativeAccounts.add(account);
			}
		});
		
		if (!negativeAccounts.isEmpty()) {
			message = "Warning: the following accounts have negative balance \n";
			for (int i = 0; i < negativeAccounts.size(); i++) {
				message += negativeAccounts.get(i).toString() + "\n";
			}
		}

		return message;
	}
	
	
}
