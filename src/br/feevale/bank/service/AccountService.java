package br.feevale.bank.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlElement;

import br.feevale.bank.model.AccountOperation;

@WebService(name="AccountService", targetNamespace="http://account.feevale.br/", serviceName="accountService", portName="accountServicePort")
@SOAPBinding(parameterStyle=ParameterStyle.WRAPPED)
public interface AccountService {

	@WebMethod
	public void deposit(
			@XmlElement(required=true) @WebParam(name="accountNumber") String accountNumber, 
			@XmlElement(required=true) @WebParam(name="amount") Double amount);
	
	@WebMethod @WebResult(name="realDrawValue")
	public Double draw(
			@XmlElement(required=true) @WebParam(name="accountNumber") String accountNumber, 
			@XmlElement(required=true) @WebParam(name="amount") Double amount);
	
	@WebMethod @WebResult(name="balance")
	public Double getBalance(@XmlElement(required=true) @WebParam(name="accountNumber") String accountNumber);
	
	@WebMethod @WebResult(name="extract")
	public AccountOperation[] getExtract(@XmlElement(required=true) @WebParam(name="accountNumber")String accountNumber);
	
}
