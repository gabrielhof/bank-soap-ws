package br.feevale.bank.service;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.feevale.bank.model.AccountOperation;

public class AccountServiceTest {

	private static AccountService accountService;

	private static Integer accountNumber = -1;
	
	@BeforeClass
	public static void setUp() {
		try {
			URL url = new URL("http://127.0.0.1:8080/account?wsdl");
			QName serviceName = new QName("http://account.feevale.br/", "accountService");
			Service service = Service.create(url, serviceName);
			
			accountService = service.getPort(AccountService.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void deposit100Test() {
		String account = generateAccountNumber();
		Double currentAmount = accountService.getBalance(account);
		
		accountService.deposit(account, 100D);
		Double newAmount = accountService.getBalance(account);
		
		Assert.assertEquals(currentAmount.doubleValue() + 100D, newAmount.doubleValue(), 0.1);
	}
	
	@Test
	public void draw200FromAccountWithBalanceTest() {
		String account = generateAccountNumber();
		
		accountService.deposit(account, 500D);
		Double realDrawValue = accountService.draw(account, 200D);
		
		Assert.assertEquals(200D, realDrawValue, 0.1);
		Assert.assertTrue(accountService.getBalance(account) >= 300);
	}
	
	@Test
	public void draw200FromAccountWith100BalanceTest() {
		String account = generateAccountNumber();
		
		accountService.deposit(account, 100D);
		Double realDrawValue = accountService.draw(account, 200D);
		
		Assert.assertEquals(100D, realDrawValue, 0.1);
		Assert.assertEquals(0D, accountService.getBalance(account), 0.1);
	}
	
	@Test
	public void extractTest() {
		String account = generateAccountNumber();
		
		accountService.deposit(account, 500D);
		accountService.draw(account, 100D);
		accountService.draw(account, 150D);
		accountService.deposit(account, 1000D);
		accountService.draw(account, 555D);
		
		List<AccountOperation> extract = Arrays.asList(accountService.getExtract(account));
		
		int size = extract.size();
		
		Assert.assertTrue(extract.size() > 0);
		Assert.assertEquals(500D, extract.get(size - 5).getAmount(), 0.1);
		Assert.assertEquals(100D, extract.get(size - 4).getAmount(), 0.1);
		Assert.assertEquals(150D, extract.get(size - 3).getAmount(), 0.1);
		Assert.assertEquals(1000D, extract.get(size - 2).getAmount(), 0.1);
		Assert.assertEquals(555D, extract.get(size - 1).getAmount(), 0.1);
	}
	
	@Test
	public void extractLast30OperationsTest() {
		String account = generateAccountNumber();
		
		for (int i = 0; i < 70; i++) {
			accountService.deposit(account, 1D);
		}
		
		List<AccountOperation> extract = Arrays.asList(accountService.getExtract(account));
		Assert.assertEquals(30, extract.size());
	}
	
	protected String generateAccountNumber() {
		return (--accountNumber).toString();
	}
}
