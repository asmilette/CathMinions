var http = require('http');
var url = require('url');

http.createServer(function (req, res) {
	//Pour extraire les paramètre passé en GET
	var params = url.parse(req.url, true).query;
	  console.log(params);
	res.write(JSON.stringify(params));
	res.end();
}).listen(8080); // listen on tcp port 8080 (all interfaces)