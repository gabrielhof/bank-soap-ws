var soap = require("soap");
var account = "99887766";

soap.createClient("http://127.0.0.1:8080/account/?wsdl", function(error, client) {
	if (error) {
		throw error;
	}

	nodeSoapWorkaround(client.wsdl.definitions.messages);

	client.deposit({accountNumber: account,  amount: 600.0}, function(error, result) {
		console.log("R$ 600.00 depositados na conta " + account);

		client.draw({accountNumber: account, amount: 200}, function(error, result) {
			console.log("R$ " + parseFloat(result.realDrawValue).toFixed(2) + " sacados da conta " + account);

			client.getBalance({accountNumber: account}, function(error, result) {
				console.log("Saldo atual da conta " + account + " é R$ " + parseFloat(result.balance).toFixed(2));
			});
		});
	});

});

/**
 * Pequeno contorno para os problemas que tive com a lib node-soap
 */
function nodeSoapWorkaround(messages) {
	for (var i in messages) {
		if (i.indexOf('Response') < 0) {
			var message = messages[i];
			message.element['$name'] = message.element['$type'];
			message.element.targetNSAlias = null;
			message.element.targetNamespace = null;
		}
	}
}