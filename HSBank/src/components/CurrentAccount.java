package components;

//1.2.2 Creation of the CurrentAccount class
public class CurrentAccount extends Account {

	public CurrentAccount(String argLabel, Client argClient) {
		super(argLabel, argClient);
	}
	
	@Override
	public String toString() {
		return "Account type: Current Account | " + super.toString();
	}

}
