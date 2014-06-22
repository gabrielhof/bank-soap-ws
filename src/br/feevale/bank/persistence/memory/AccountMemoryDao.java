package br.feevale.bank.persistence.memory;

import java.util.HashMap;
import java.util.Map;

import br.feevale.bank.model.Account;
import br.feevale.bank.persistence.AccountDao;

public class AccountMemoryDao extends AccountDao {

	private Map<String, Account> accounts = new HashMap<String, Account>();
	
	@Override
	public Account get(String accountNumber) {
		Account account = accounts.get(accountNumber);
		
		if (account == null) {
			account = new Account(accountNumber);
			save(account);
		}
		
		return account;
	}

	@Override
	public Account save(Account account) {
		return accounts.put(account.getNumber(), account);
	}

	
	
}
