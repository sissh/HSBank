package main;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import components.*;
import utils.*;

//1.1.2 Creation of main class for tests
public class Main {
	public static void main (String[]args) {
		//1.1.2 Display contents of Clients array
		ArrayList<Client> clients = Operation.generateClients(10);
		//Display.displayClients(clients);
		
		//1.2.3 Display contents of Accounts array
		ArrayList<Account> accounts = Operation.generateAccounts(clients);
		//Display.displayAccounts(accounts);
		
		//1.3.1 Using hashmaps for accounts
		HashMap<Integer, Account> hashAccounts = Operation.convertToHashmap(accounts);
		//Display.displayHashAccounts(hashAccounts);
		
		//1.3.4 Creation of the Flow array
		ArrayList<Flow> flows = new ArrayList<Flow>();
		flows.add(new Debit("Debit from account 1", "A15DADZ", 50.00, 1, true, LocalDate.now().plusDays(2)));
		
		//1.3.4 - Add credit to all current accounts
		hashAccounts.values().stream().filter(account -> account instanceof CurrentAccount).forEach(account -> 
			flows.add(new Credit("Credit to current account " + account.getAccountNumber(), "DZADZD", 100.50, account.getAccountNumber(), true, LocalDate.now().plusDays(2))));
		
		//1.3.4 - Add credit to all savings accounts
		hashAccounts.values().stream().filter(account -> account instanceof SavingsAccount).forEach(account ->
			flows.add(new Credit("Credit to savings account " + account.getAccountNumber(), "dadzaazd", 1500.00, account.getAccountNumber(), true, LocalDate.now().plusDays(2))));
		
		flows.add(new Transfer(1, "Transfer from account 1 to account 2", "dazazadzah", 60.00, 2, true, LocalDate.now().plusDays(2)));
		
		//1.3.5 Updating accounts
		/*System.out.println("Accounts before transactions : \n");
		Display.displayHashAccounts(hashAccounts);
		System.out.println("\n");
		System.out.println("Accounts After transactions : \n");
		hashAccounts = Operation.updateAccounts(flows, hashAccounts);
		Display.displayHashAccounts(hashAccounts);
		
		//1.3.5 Updating accounts - Check for negative accounts
		System.out.println("\n");
		String negative = Operation.checkNegativeAccounts(hashAccounts);
		System.out.println(negative);*/
		
		//2.1 JSON file of flows - Write
		Operation.writeFlowsToJson(flows);
		
		//2.2 XML file of accounts - Write
		Operation.writeAccountsToXML(hashAccounts);
		
		//2.1 JSON file of flows - Read
		Path pathJson = FileSystems.getDefault().getPath("resources", "flows.json");
		try {
			ArrayList<Flow> flowsFromJson= Operation.readFlowsFromJSON(pathJson);
			flowsFromJson.stream().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//2.2  XML file of accounts - Read
		Path pathXML = FileSystems.getDefault().getPath("resources", "accounts.xml");
		try {
			ArrayList<Account> accountsFromXML = Operation.readAccountsFromXML(pathXML);
			accountsFromXML.stream().forEach(System.out::println);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}
}
