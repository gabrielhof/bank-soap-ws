<?php
$accountNumber = '123456';

$ws = new SoapClient('http://127.0.0.1:8080/account/?wsdl', array('cache_wsdl' => WSDL_CACHE_NONE));

echo "Depositando R$ 200,00...\n\n";
$ws->deposit(array('accountNumber' => $accountNumber, 'amount' => '200'));

echo "Sacando R$ 100,00...\n\n";
$ws->draw(array('accountNumber' => $accountNumber, 'amount' => '100'));

$response = $ws->getBalance(array('accountNumber' => $accountNumber));
$balance = number_format($response->balance, 2, ',', '.');
echo "O saldo atual é de R$ {$balance}\n\n";

$response = $ws->getExtract(array('accountNumber' => $accountNumber));
echo "---------- Extrato ----------\n";
echo "---  Operação | Valor     ---\n";
foreach ($response->extract as $operation) {
	echo '--- ';
	echo str_pad(($operation->operation == 'ADD' ? 'Deposito' : 'Saque'), 9);
	echo ' | ';
	
	$amount = number_format($operation->amount, 2, ',', '.');
	echo str_pad("R$ {$amount}", 8);
	echo " ---\n";
}
echo "-----------------------------\n";
