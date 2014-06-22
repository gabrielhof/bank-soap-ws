package br.feevale.bank.main;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import br.feevale.bank.service.AccountService;

public class ServiceConsumer {

//	@WebServiceRef(wsdlLocation="http://127.0.0.1:8080/account?wsdl")
	private AccountService accountService;
	
	public static void main(String[] args) throws MalformedURLException {
		ServiceConsumer consumer = new ServiceConsumer();
		consumer.consume();
	}
	
	public ServiceConsumer() throws MalformedURLException {
		URL url = new URL("http://127.0.0.1:8080/account?wsdl");
		QName serviceName = new QName("http://account.feevale.br/", "accountService");
		Service service = Service.create(url, serviceName);
		accountService = service.getPort(AccountService.class);
	}
	
	public void consume() {
		String accountNumber = "123456";
		
		System.out.println("Depositando R$ 200,00");
		accountService.deposit(accountNumber, 200D);
		
		System.out.println("Sacando R$ 100,00");
		accountService.draw(accountNumber, 100D);
		
		Double amount = accountService.getBalance(accountNumber);
		System.out.println(String.format("Saldo: %d", amount));
		
		System.out.println(accountService.getExtract(accountNumber));
	}
	
}
