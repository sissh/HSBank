package components;

import java.time.LocalDate;

//1.3.3 Creation of the Debit class
public class Debit extends Flow {
	
	public Debit(String argComment, String argIdentifier, Double argAmount, Integer argTargetAccountNumber, Boolean argEffect, LocalDate argDate) {
		super(argComment, argIdentifier, argAmount, argTargetAccountNumber, argEffect, argDate);
	}

}
