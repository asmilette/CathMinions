var http = require('http');
var SQLConnection = require('mysql').createConnection({
	'host' : 'localhost',
	'user' : 'root',
	'password' : 'abc123...',
	database : 'test'
});

var url = require('url');
var query = "SELECT * FROM test";

http.createServer(function (req, res) {
	//Pour extraire les paramètre passé en GET
	var params = url.parse(req.url, true).query;
	//if(params.index) {
		SQLConnection.query(query, [params.index], function(err, rows, fields) {
			//On converti l'objet en string pour ensuite dans l'application récupérer le formet JSON et le parser
			res.write(JSON.stringify(rows));
			res.end();
		});
	//}
	/*else {
		//Si la requête est mal formulé
		res.writeHead(400, "BAD REQUEST", {'Content-Type': 'text/html'});
		res.end('<html><head><title>400 - Request not supported</title></head><body><h1>Request not supported.</h1></body></html>');
		res.end();
	}*/
}).listen(8080); // listen on tcp port 8080 (all interfaces)