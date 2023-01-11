package utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	
	//2.1 JSON file of flows - writing
	public static void writeFlowsToJson(ArrayList<Flow> flows) {
		Path path = FileSystems.getDefault().getPath("resources", "flows.json");
		String output = "[\n";
		for (int i = 0; i < flows.size(); i++) {
			if (flows.get(i) instanceof Transfer transfer) {
				output += "  { \n"
						+ "    \"flowType\": \"Transfer\",\n"
						+ "    \"issuerAccountNumber\": \"" + transfer.getIssuerAccountNumber() + "\",\n"
						+ "    \"targetAccountNumber\": \"" + transfer.getTargetAccountNumber() + "\",\n"
						+ "    \"identifier\": \"" + transfer.getIdentifier() + "\",\n"
						+ "    \"amount\": \"" +  transfer.getAmount() + "\",\n"
						+ "    \"comment\": \"" + transfer.getComment() + "\",\n"
						+ "    \"effect\": \"" + transfer.getEffect().toString() + "\",\n"
						+ "    \"date\": \"" + transfer.getDate().toString() + "\"\n";
			} 
			if (flows.get(i) instanceof Debit debit){
				output += "  { \n"
						+ "    \"flowType\": \"Debit\",\n"
						+ "    \"targetAccountNumber\": \"" + debit.getTargetAccountNumber() + "\",\n"
						+ "    \"identifier\": \"" + debit.getIdentifier() + "\",\n"
						+ "    \"amount\": \"" +  debit.getAmount() + "\",\n"
						+ "    \"comment\": \"" + debit.getComment() + "\",\n"
						+ "    \"effect\": \"" + debit.getEffect().toString() + "\",\n"
						+ "    \"date\": \"" + debit.getDate().toString() + "\"\n";
			}
			
			if (flows.get(i) instanceof Credit credit){
				output += "  { \n"
						+ "    \"flowType\": \"Credit\",\n"
						+ "    \"targetAccountNumber\": \"" + credit.getTargetAccountNumber() + "\",\n"
						+ "    \"identifier\": \"" + credit.getIdentifier() + "\",\n"
						+ "    \"amount\": \"" +  credit.getAmount() + "\",\n"
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
		output += "]";
		
		try {
			Files.write(path, output.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//2.2 XML file of accounts - writing
	public static void writeAccountsToXML(HashMap<Integer, Account> hashAccounts) {
		Path path = FileSystems.getDefault().getPath("resources", "accounts.xml");
		ArrayList<Account> accounts = new ArrayList<Account>(hashAccounts.values());
		
		String output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Accounts>\n";
		
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i) instanceof CurrentAccount currentAccount) {
				output += "  <CurrentAccount>\n" 
						+ "    <AccountNumber>" + currentAccount.getAccountNumber() + "</AccountNumber>\n"
						+ "    <Client>\n"  
							+ "      <Name>" + currentAccount.getClient().getName() + "</Name>\n"
							+ "      <FirstName>" + currentAccount.getClient().getFirstName() + "</FirstName>\n"
							+ "      <ClientNumber>" + currentAccount.getClient().getClientNumber() + "</ClientNumber>\n" + "    </Client>\n"						+ "    <Label>" + currentAccount.getLabel() + "</Label>\n"
						+ "    <Balance>" + currentAccount.getBalance() + "</Balance>\n"
						+ "  </CurrentAccount>\n\n";
			}
			
			if (accounts.get(i) instanceof SavingsAccount savingsAccount) {
				output += "  <SavingsAccount>\n" 
						+ "    <AccountNumber>" + savingsAccount.getAccountNumber() + "</AccountNumber>\n"
						+ "    <Client>\n"  
							+ "      <Name>" + savingsAccount.getClient().getName() + "</Name>\n"
							+ "      <FirstName>" + savingsAccount.getClient().getFirstName() + "</FirstName>\n"
							+ "      <ClientNumber>" + savingsAccount.getClient().getClientNumber() + "</ClientNumber>\n" + "    </Client>\n"
						+ "    <Label>" + savingsAccount.getLabel() + "</Label>\n"
						+ "    <Balance>" + savingsAccount.getBalance() + "</Balance>\n"
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
	
	//2.1 JSON file of flows - reading
	public static ArrayList<Flow> readFlowsFromJSON(Path path) throws IOException {
		ArrayList<Flow> flows = new ArrayList<>();
		String fileOutput = new String(Files.readAllBytes(path));
		JSONArray json = new JSONArray(fileOutput);
		
		for (int i = 0; i < json.length(); i++) {
			if (json.get(i) instanceof JSONObject jsonObj) {
				if (jsonObj.get("flowType").equals("Transfer")) {
					Integer issuerAccountNumber = Integer.parseInt(jsonObj.get("issuerAccountNumber").toString());
					Integer targetAccountNumber = Integer.parseInt(jsonObj.get("targetAccountNumber").toString());
					String identifier = jsonObj.get("identifier").toString();
					Double amount = Double.parseDouble(jsonObj.get("amount").toString());
					String comment = jsonObj.get("comment").toString();
					Boolean effect = Boolean.parseBoolean(jsonObj.get("effect").toString());
					LocalDate date = LocalDate.parse(jsonObj.get("date").toString());
					flows.add(new Transfer(issuerAccountNumber, comment, identifier, amount, targetAccountNumber, effect, date));
				}
				
				if (jsonObj.get("flowType").equals("Credit")) {
					Integer targetAccountNumber = Integer.parseInt(jsonObj.get("targetAccountNumber").toString());
					String identifier = jsonObj.get("identifier").toString();
					Double amount = Double.parseDouble(jsonObj.get("amount").toString());
					String comment = jsonObj.get("comment").toString();
					Boolean effect = Boolean.parseBoolean(jsonObj.get("effect").toString());
					LocalDate date = LocalDate.parse(jsonObj.get("date").toString());
					flows.add(new Credit(comment, identifier, amount, targetAccountNumber, effect, date));
				}
				
				if (jsonObj.get("flowType").equals("Debit")) {
					Integer targetAccountNumber = Integer.parseInt(jsonObj.get("targetAccountNumber").toString());
					String identifier = jsonObj.get("identifier").toString();
					Double amount = Double.parseDouble(jsonObj.get("amount").toString());
					String comment = jsonObj.get("comment").toString();
					Boolean effect = Boolean.parseBoolean(jsonObj.get("effect").toString());
					LocalDate date = LocalDate.parse(jsonObj.get("date").toString());
					flows.add(new Debit(comment, identifier, amount, targetAccountNumber, effect, date));
				}
			}
		}
		return flows;
	}
	
	//2.2 XML file of accounts - reading
	public static ArrayList<Account> readAccountsFromXML(Path path) throws ParserConfigurationException, SAXException, IOException {
		ArrayList<Account> accounts = new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(Files.newInputStream(path));
		
		String name = "";
		String firstName = "";
		Integer clientNumber = 0;
		Integer accountNumber = 0;
		String label = "";
		Double balance = 0.00;
		
		NodeList currentAccountNodes = doc.getElementsByTagName("CurrentAccount");
		for (int k = 0; k < currentAccountNodes.getLength(); k++) {
			Node currentAccountNode = currentAccountNodes.item(k);
			if (currentAccountNode.getNodeType() == Node.ELEMENT_NODE) {
				Element currentAccount = (Element) currentAccountNode;
				accountNumber = Integer.parseInt(currentAccount.getElementsByTagName("AccountNumber").item(0).getTextContent());
				label = currentAccount.getElementsByTagName("Label").item(0).getTextContent();
				balance = Double.parseDouble(currentAccount.getElementsByTagName("Balance").item(0).getTextContent());
				
				NodeList clientAttributesNodes = currentAccount.getElementsByTagName("Client");
				for (int l = 0; l < clientAttributesNodes.getLength(); l++) {
					Node clientAttributesNode = clientAttributesNodes.item(l);
					if (clientAttributesNode.getNodeType() == Node.ELEMENT_NODE) {
						Element clientAttributes = (Element) clientAttributesNode;
						name = clientAttributes.getElementsByTagName("Name").item(0).getTextContent();
						firstName = clientAttributes.getElementsByTagName("FirstName").item(0).getTextContent();
						clientNumber = Integer.parseInt(clientAttributes.getElementsByTagName("ClientNumber").item(0).getTextContent());
					}
				}
				
				Client client = new Client(name, firstName);
				client.setClientNumber(clientNumber);
				
				CurrentAccount newcurrentAccount = new CurrentAccount(label, client);
				newcurrentAccount.setBalance(balance);
				newcurrentAccount.setAccountNumber(accountNumber);
				accounts.add(newcurrentAccount);
			}
		}
			
		NodeList savingsAccountNodes = doc.getElementsByTagName("SavingsAccount");
		for (int k = 0; k < savingsAccountNodes.getLength(); k++) {
			Node savingsAccountNode = savingsAccountNodes.item(k);
			if (savingsAccountNode.getNodeType() == Node.ELEMENT_NODE) {
				Element savingsAccount = (Element) savingsAccountNode;
				accountNumber = Integer.parseInt(savingsAccount.getElementsByTagName("AccountNumber").item(0).getTextContent());
				label = savingsAccount.getElementsByTagName("Label").item(0).getTextContent();
				balance = Double.parseDouble(savingsAccount.getElementsByTagName("Balance").item(0).getTextContent());
				
				NodeList clientAttributesNodes = savingsAccount.getElementsByTagName("Client");
				for (int l = 0; l < clientAttributesNodes.getLength(); l++) {
					Node clientAttributesNode = clientAttributesNodes.item(l);
					if (clientAttributesNode.getNodeType() == Node.ELEMENT_NODE) {
						Element clientAttributes = (Element) clientAttributesNode;
						name = clientAttributes.getElementsByTagName("Name").item(0).getTextContent();
						firstName = clientAttributes.getElementsByTagName("FirstName").item(0).getTextContent();
						clientNumber = Integer.parseInt(clientAttributes.getElementsByTagName("ClientNumber").item(0).getTextContent());
					}
				}
				
				Client client = new Client(name, firstName);
				client.setClientNumber(clientNumber);
				
				SavingsAccount newSavingsAccount = new SavingsAccount(label, client);
				newSavingsAccount.setBalance(balance);
				newSavingsAccount.setAccountNumber(accountNumber);
				accounts.add(newSavingsAccount);
			}
		}
		
		return accounts;
	}
}
