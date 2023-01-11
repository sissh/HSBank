package utils;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Random;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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
	
	//1.3.5 Updating Accounts - Check negative accounts
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
	
	//2.1 JSON file of flows
	public static void writeFlowsToJson(ArrayList<Flow> flows) {
		Path path = FileSystems.getDefault().getPath("resources", "flows.json");
		String output = "{\n\"flows\": [ \n";
		for (int i = 0; i < flows.size(); i++) {
			if (flows.get(i) instanceof Transfer transfer) {
				output += "  { \n"
						+ "    \"issuerAccountNumber\": \"" + transfer.getIssuerAccountNumber() + "\",\n"
						+ "    \"targetAccountNumber\": \"" + transfer.getTargetAccountNumber() + "\",\n"
						+ "    \"flowType\": \"Transfer\",\n"
						+ "    \"amount\": \"" +  String.format("%.2f", transfer.getAmount()) + "\",\n"
						+ "    \"comment\": \"" + transfer.getComment() + "\",\n"
						+ "    \"effect\": \"" + transfer.getEffect().toString() + "\",\n"
						+ "    \"date\": \"" + transfer.getDate().toString() + "\"\n";
			} 
			if (flows.get(i) instanceof Debit debit){
				output += "  { \n"
						+ "    \"flowType\": \"Debit\",\n"
						+ "    \"targetAccountNumber\": \"" + debit.getTargetAccountNumber() + "\",\n"
						+ "    \"amount\": \"" +  String.format("%.2f", debit.getAmount()) + "\",\n"
						+ "    \"comment\": \"" + debit.getComment() + "\",\n"
						+ "    \"effect\": \"" + debit.getEffect().toString() + "\",\n"
						+ "    \"date\": \"" + debit.getDate().toString() + "\"\n";
			}
			
			if (flows.get(i) instanceof Credit credit){
				output += "  { \n"
						+ "    \"flowType\": \"Credit\",\n"
						+ "    \"targetAccountNumber\": \"" + credit.getTargetAccountNumber() + "\",\n"
						+ "    \"amount\": \"" +  String.format("%.2f", credit.getAmount()) + "\",\n"
						+ "    \"comment\": \"" + credit.getComment() + "\",\n"
						+ "    \"effect\": \"" + credit.getEffect().toString() + "\",\n"
						+ "    \"date\": \"" + credit.getDate().toString() + "\"\n";
			}
			if (i < flows.size() - 1) {
				output += "  },\n\n";
			} else {
				output += "  }";
			}
		}
		
		output += "\n ]\n}";
		try {
			Files.write(path, output.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//2.2 XML file of flows
	public static void writeAccountsToXML(HashMap<Integer, Account> hashAccounts) {
		Path path = FileSystems.getDefault().getPath("resources", "accounts.xml");
		ArrayList<Account> accounts = new ArrayList<Account>(hashAccounts.values());
		
		String output = "<Accounts>\n";
		
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i) instanceof CurrentAccount currentAccount) {
				output += "  <CurrentAccount>\n" 
						+ "    <AccountNumber>" + currentAccount.getAccountNumber() + "</AccountNumber>\n"
						+ "    <Client>" + currentAccount.getClient().getFirstName() + " " + currentAccount.getClient().getName() + "</Client>\n"
						+ "    <Label>" + currentAccount.getLabel() + "</Label>\n"
						+ "    <Balance>" + String.format("%.2f", currentAccount.getBalance()) + "</Balance>\n"
						+ "  </CurrentAccount>\n\n";
			}
			
			if (accounts.get(i) instanceof SavingsAccount savingsAccount) {
				output += "  <SavingsAccount>\n" 
						+ "    <AccountNumber>" + savingsAccount.getAccountNumber() + "</AccountNumber>\n"
						+ "    <Client>" + savingsAccount.getClient().getFirstName() + " " + savingsAccount.getClient().getName() + "</Client>\n"
						+ "    <Label>" + savingsAccount.getLabel() + "</Label>\n"
						+ "    <Balance>" + String.format("%.2f", savingsAccount.getBalance()) + "</Balance>\n"
						+ "  </SavingsAccount>\n\n";
			}
		}
		output += "</Accounts>";
		try {
			Files.write(path, output.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
