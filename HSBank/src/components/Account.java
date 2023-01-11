package components;

//1.2.1 Creation of the account class
public abstract class Account implements Comparable<Account>{
	protected String label;
	protected Double balance = 0.00;
	protected Integer accountNumber;
	protected Client client;
	private static Integer count = 0;
	
	protected Account (String argLabel, Client argClient) {
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
	
	//1.3.5 Updating accounts
	public void setBalance(Flow flow) {
		if (flow instanceof Credit credit) {
			this.balance += credit.getAmount();
		}
		
		if (flow instanceof Debit debit) {
			this.balance -= debit.getAmount();
		}
		
		if (flow instanceof Transfer transfer) {
			if (transfer.getTargetAccountNumber().equals(this.accountNumber)) {
				this.balance += transfer.getAmount();
			} 
			if (transfer.getIssuerAccountNumber().equals(this.accountNumber)){
				this.balance -= transfer.getAmount();
			}
		}
	}
	
	public void setAccountNumber(Integer argAccountNumber) {
		this.accountNumber = argAccountNumber;
	}
	
	public void setClient(Client argClient) {
		this.client = argClient;
	}
	
	public String toString() {
		return "Account number: " + accountNumber + " | Client: " + client.getFirstName() + " " + client.getName() + 
				" | Label: " + label + " | Balance: " + String.format("%.2f", balance) + "€";
	}
	
	//1.3.1 Allow for comparisons between accounts through balance attribute
	@Override
	public int compareTo(Account argAccount) {
		return Double.compare(this.balance, argAccount.balance);
	}
	

}
