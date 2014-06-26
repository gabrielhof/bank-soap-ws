package br.feevale.bank.service.impl;

import java.util.logging.Logger;

import javax.jws.WebService;

import br.feevale.bank.model.Account;
import br.feevale.bank.model.AccountOperation;
import br.feevale.bank.persistence.AccountDao;
import br.feevale.bank.service.AccountService;
import br.feevale.bank.util.LoggerHelper;

@WebService(name="AccountService", targetNamespace="http://account.feevale.br/", 
			serviceName="accountService", portName="accountServicePort",
			endpointInterface="br.feevale.bank.service.AccountService")
public class AccountServiceImpl implements AccountService {

	private Logger logger = LoggerHelper.getLogger();
	
	private AccountDao accountDao = AccountDao.getDefault();
	
	@Override
	public void deposit(String accountNumber, Double amount) {
		Account account = accountDao.get(accountNumber);
		account.add(amount);
		
		logger.info(String.format("R$ %s depositados na conta %s", amount.toString(), accountNumber));
		
		accountDao.save(account);
	}

	@Override
	public Double draw(String accountNumber, Double amount) {
		Account account = accountDao.get(accountNumber);
		amount = account.subtract(amount);
		
		logger.info(String.format("R$ %s sacados da conta %s", amount.toString(), accountNumber));
		
		accountDao.save(account);
		return amount;
	}

	@Override
	public Double getBalance(String accountNumber) {
		Account account = accountDao.get(accountNumber);
		Double balance = account.getBalance();
		
		logger.info(String.format("Saldo de R$ %s na conta %s", balance.toString(), accountNumber));
		
		return balance;
	}

	@Override
	public AccountOperation[] getExtract(String accountNumber) {
		logger.info(String.format("Gerando extrado da conta %s", accountNumber));
		
		Account account = accountDao.get(accountNumber);
		return account.getExtract().toArray(new AccountOperation[account.getExtract().size()]);
	}

}
