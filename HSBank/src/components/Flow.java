package components;

import java.time.LocalDate;

//1.3.2 Creation of the Flow class
public abstract class Flow {
	private String comment;
	private String identifier;
	private Double amount;
	private Integer targetAccountNumber;
	private Boolean effect;
	private LocalDate date;
	
	protected Flow (String argComment, String argIdentifier, Double argAmount, Integer argTargetAccountNumber, Boolean argEffect, LocalDate argDate) {
		comment = argComment;
		identifier = argIdentifier;
		amount = argAmount;
		targetAccountNumber = argTargetAccountNumber;
		effect = argEffect;
		date = argDate;
	}
	
	public String getComment() {
		return comment;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public Integer getTargetAccountNumber() {
		return targetAccountNumber;
	}
	
	public Boolean getEffect() {
		return effect;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setComment(String argComment) {
		this.comment = argComment;
	}
	
	public void setIdentifier(String argIdentifier) {
		this.identifier = argIdentifier;
	}
	
	public void setAmount(Double argAmount) {
		this.amount = argAmount;
	}
	
	public void setTargetAccountNumber(Integer argTargetAccountNumber) {
		this.targetAccountNumber = argTargetAccountNumber;
	}
	
	public void setEffect(Boolean argEffect) {
		this.effect = argEffect;
	}
	
	public void setDate(LocalDate argDate) {
		this.date = argDate;
	}
	
	public String toString() {
		return "Target Account Number " + targetAccountNumber + " | Identifier: " + identifier + " | Amount: " + amount + 
				" | Comment: " + comment + " | Effect: " + effect.toString() + " | Date: " + date.toString();
	}
}
