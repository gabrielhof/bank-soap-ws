package br.feevale.bank.main;

import javax.xml.ws.Endpoint;

import br.feevale.bank.service.impl.AccountServiceImpl;

public class ServicePublisher {

	public static void main(String[] args) {
		System.out.println("Iniciando WebService...");
		Endpoint.publish("http://127.0.0.1:8080/account", new AccountServiceImpl());
	}
	
}
