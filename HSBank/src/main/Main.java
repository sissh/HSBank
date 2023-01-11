package main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import components.*;
import utils.*;

//1.1.2 Creation of main class for tests
public class Main {
	public static void main (String[]args) {
		ArrayList<Client> clients = Operation.generateClients(10);
		//Display.displayClients(clients);
		
		//1.2.3 Creation of the table of accounts
		ArrayList<Account> accounts = Operation.generateAccounts(clients);
		//Display.displayAccounts(accounts);
		
		//1.3.1 Using hashmaps for accounts
		HashMap<Integer, Account> hashAccounts = Operation.convertToHashmap(accounts);
		//Display.displayHashAccounts(hashAccounts);
		
		//1.3.4 Creation of the Flow array
		ArrayList<Flow> flows = new ArrayList<Flow>();
		flows.add(new Debit("Debit from account 1", "A15DADZ", 50.00, 1, true, LocalDate.now().plusDays(2)));
		
		//add credit to all current accounts
		hashAccounts.values().stream().filter(account -> account instanceof CurrentAccount).forEach(account -> 
			flows.add(new Credit("Credit to current account " + account.getAccountNumber(), "DZADZD", 100.50, account.getAccountNumber(), true, LocalDate.now().plusDays(2))));
		
		//add credit to all savings accounts
		hashAccounts.values().stream().filter(account -> account instanceof SavingsAccount).forEach(account ->
			flows.add(new Credit("Credit to savings account " + account.getAccountNumber(), "dadzaazd", 1500.00, account.getAccountNumber(), true, LocalDate.now().plusDays(2))));
		
		flows.add(new Transfer(1, "Transfer from account 1 to account 2", "dazazadzah", 60.00, 2, true, LocalDate.now().plusDays(2)));
		
		System.out.println("Accounts before transactions : \n");
		Display.displayHashAccounts(hashAccounts);
		System.out.println("\n");
		System.out.println("Accounts After transactions : \n");
		hashAccounts = Operation.updateAccounts(flows, hashAccounts);
		Display.displayHashAccounts(hashAccounts);

		System.out.println("\n");
		String negative = Operation.checkNegativeAccounts(hashAccounts);
		System.out.println(negative);
	}
}
