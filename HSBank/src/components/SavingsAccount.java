package components;

//1.2.2 Creation of the SavingsAccount class
public class SavingsAccount extends Account {

	public SavingsAccount(String argLabel, Client argClient) {
		super(argLabel, argClient);
	}
	
	@Override
	public String toString() {
		return "Account type: Savings Account | " + super.toString();
	}


}
