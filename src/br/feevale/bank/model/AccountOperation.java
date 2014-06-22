package br.feevale.bank.model;

import javax.xml.bind.annotation.XmlElement;

public class AccountOperation {

	private Operation operation;
	private Double amount;
	
	public AccountOperation() {
		this(null, 0D);
	}
	
	public AccountOperation(Operation operation, Double amount) {
		this.operation = operation;
		this.amount = amount;
	}
	
	@XmlElement
	public Operation getOperation() {
		return operation;
	}
	
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	@XmlElement
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
