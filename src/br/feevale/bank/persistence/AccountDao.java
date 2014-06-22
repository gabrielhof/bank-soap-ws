package br.feevale.bank.persistence;

import br.feevale.bank.model.Account;
import br.feevale.bank.persistence.memory.AccountMemoryDao;

public abstract class AccountDao {
	
	private static AccountDao accountDao;
	
	public abstract Account get(String accountNumber);
	
	public abstract Account save(Account account);
	
	public static AccountDao getDefault() {
		if (accountDao == null) {
			accountDao = new AccountMemoryDao();
		}
		
		return accountDao;
	}

}
