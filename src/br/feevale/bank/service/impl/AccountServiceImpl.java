package br.feevale.bank.service.impl;

import javax.jws.WebService;

import br.feevale.bank.model.Account;
import br.feevale.bank.model.AccountOperation;
import br.feevale.bank.persistence.AccountDao;
import br.feevale.bank.service.AccountService;

@WebService(name="AccountService", targetNamespace="http://account.feevale.br/", 
			serviceName="accountService", portName="accountServicePort",
			endpointInterface="br.feevale.bank.service.AccountService")
public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao = AccountDao.getDefault();
	
	@Override
	public void deposit(String accountNumber, Double amount) {
		Account account = accountDao.get(accountNumber);
		account.add(amount);
		
		accountDao.save(account);
	}

	@Override
	public Double draw(String accountNumber, Double amount) {
		Account account = accountDao.get(accountNumber);
		amount = account.subtract(amount);
		
		accountDao.save(account);
		return amount;
	}

	@Override
	public Double getBalance(String accountNumber) {
		Account account = accountDao.get(accountNumber);
		return account.getBalance();
	}

	@Override
	public AccountOperation[] getExtract(String accountNumber) {
		Account account = accountDao.get(accountNumber);
		return account.getExtract().toArray(new AccountOperation[account.getExtract().size()]);
	}

}
