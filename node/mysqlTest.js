var mysql = require('mysql');
var connection = mysql.createConnection({
	'host' : 'localhost',
	'user' : 'root',
	'password' : 'abc123...',
	database : 'ISI'
});

connection.connect();

connection.query("SELECT * FROM vehicules", function(err, rows, fields) {
	if(err) throw err;
	console.log("Length:"+rows.length);
	console.log("Length:"+fields.length);
	console.log(fields);
	for(var i=0; i<rows.length; i++) {
		console.log(rows[i].no);	
	}
});

connection.end();
