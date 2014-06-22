package br.feevale.bank.model;

import java.util.ArrayList;
import java.util.List;

public class Account {

	private String number;
	private Double balance = 0D;
	
	private List<AccountOperation> extract = new ArrayList<AccountOperation>();
	
	public Account(String number) {
		this.number = number;
	}
	
	public Account(String number, Double initialBalance) {
		this(number);
		add(initialBalance);
	}
	
	public String getNumber() {
		return number;
	}
	
	public void add(Double amount) {
		if (amount != null && amount > 0) {
			synchronized (this) {
				balance += amount;
				extract.add(new AccountOperation(Operation.ADD, amount));
			}
		}
	}
	
	public Double subtract(Double amount) {
		if (amount != null && amount > 0) {
			synchronized (this) {
				if (balance < amount) {
					amount = amount - balance;
					balance = 0D;
				} else {
					balance -= amount;
				}
				
				extract.add(new AccountOperation(Operation.SUBTRACT, amount));
			}
		}
		
		return amount == null || amount < 0 ? 0D : amount;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public List<AccountOperation> getExtract() {
		int extractSize = extract.size();
		return extractSize <= 30 ? extract : extract.subList(extractSize - 30, extractSize);
	}
	
}
