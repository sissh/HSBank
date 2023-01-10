package components;

// 1.1.1 Creation of the client class
public class Client {
	private String name;
	private String firstName;
	private Integer clientNumber;
	private static Integer count = 0;
	
	public Client (String argName, String argFirstName) {
		name = argName;
		firstName = argFirstName;
		count++;
		clientNumber = count;
	}
	
	public String getName () {
		return name;
	}
	
	public String getFirstName () {
		return firstName;
	}
	
	public Integer getClientNumber () {
		return clientNumber;
	}
	
	public void setName (String argName) {
		this.name = argName;
	}
	
	public void setFirstName (String argFirstName) {
		this.firstName = argFirstName;
	}
	
	public void setClientNumber (Integer argClientNumber) {
		this.clientNumber = argClientNumber;
	}
	
	public String toString() {
		String clientString = new String();
		clientString = "Client Number: " + clientNumber + " | Name: " + name + " | First Name: " + firstName;
		return clientString;
	}
}
