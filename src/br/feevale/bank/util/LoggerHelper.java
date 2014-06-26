package br.feevale.bank.util;

import java.util.logging.Logger;

public class LoggerHelper {

	private static final String LOGGER_NAME = "BankSoapWS";
	
	public static Logger getLogger() {
		return Logger.getLogger(LOGGER_NAME);
	}
	
}
