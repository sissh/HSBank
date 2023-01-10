package components;

//1.2.1 Creation of the account class
public abstract class Accounts {
	protected String label;
	protected Double balance = 0.00;
	protected Integer accountNumber;
	protected Client client;
	private static Integer count = 0;
	
	protected Accounts (String argLabel, Client argClient) {
		label = argLabel;
		client = argClient;
		count++;
		accountNumber = count;
	}
	
	public String getLabel() {
		return label;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public Integer getAccountNumber() {
		return accountNumber;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setLabel(String argLabel) {
		this.label = argLabel;
	}
	
	public void setBalance(Double argBalance) {
		this.balance = argBalance;
	}
	
	public void setAccountNumber(Integer argAccountNumber) {
		this.accountNumber = argAccountNumber;
	}
	
	public void setClient(Client argClient) {
		this.client = argClient;
	}
	
	public String toString() {
		return "Account number: " + accountNumber + " | Client: " + client.getFirstName() + " " + client.getName() + 
				" | Label: " + label + " | Balance: " + String.format("%.2f", balance);
	}
}
