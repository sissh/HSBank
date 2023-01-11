package components;

import java.time.LocalDate;

//1.3.3 Creation of the Transfer class
public class Transfer extends Flow {
	private Integer issuerAccountNumber;
	
	public Transfer(Integer argIssuerAccountNumber, String argComment, String argIdentifier, Double argAmount, Integer argTargetAccountNumber, Boolean argEffect, LocalDate argDate) {
		super(argComment, argIdentifier, argAmount, argTargetAccountNumber, argEffect, argDate);
		issuerAccountNumber = argIssuerAccountNumber;
	}
	
	public Integer getIssuerAccountNumber() {
		return issuerAccountNumber;
	}
	
	public void setIssuerAccountNumber(Integer argIssuerAccountNumber) {
		this.issuerAccountNumber = argIssuerAccountNumber;
	}
	
	@Override
	public String toString() {
		return "Issuer Account Number: " + issuerAccountNumber + " | " + super.toString();
	}

}
