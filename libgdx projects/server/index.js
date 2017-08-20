var app = require('express');
var server = require('http').Server(app);
var io = require('socket.io')(server);
var playerJs = require('./player');
var mysql = require('mysql');

var con = mysql.createConnection({
      		connectionLimit   :   100,
			  host: "localhost",
			  user: "adminuser",
			  password: "VbfPWQoOUlge",
			  database: "pirates"
			});
//con.connect(function(err) {
  //if (err) throw err;
  //console.log("Connected!");
//});
var user = {
	password:'',
	salt:''
};

server.listen(8080,function()
{
	console.log("Server is now running...");
});

function getUserCredentials(data,socket)
{
		var query = 'SELECT salt FROM player where name = \'' + data.name + '\'';
		con.query(query,function(err,rows,fiels)
		{
			if(err)
			{
				console.log("Error: " + err.message);
			}
			else
			{
				socket.emit("playerLog",rows[0]);
				console.log("retured rows: " + rows);
			}
		});
}

function validatePassword(data,socket)
{
	var query = 'SELECT password FROM player where name = \'' + data.name + '\'';
 	con.query(query,function(err,rows,fiels)
	{
		if(err)
		{
			console.log("Error: " + err.message);
		}
		else
		{
			console.log(rows[0]);
			if(rows[0].password == data.password)
			{
				data.id = socket.id;
				playerJs.pushPlayer(data,socket);
				console.log("Loggin successfull");
			}
			else
				console.log("Wrong Credentials");
		}
	});
}

io.on('connection', function(socket)
{
	socket.on('playerConnected',function(data)
	{
		setTimeout(function()
		{
			getUserCredentials(data,socket);
		},1000);
		/*
		getUserCredentials(data.name,function(res)
		{
				console.log("player connected res: " + res.rows);
            	// code to execute on data retrieval

		});
		*/


	});
	socket.on("validateCredentials",function(data)
	{
		setTimeout(function()
		{
			validatePassword(data,socket);
		},1000);
	});
	socket.on("playerMoved",function(data)
	{
		playerJs.playerMoved(data,socket);
	});
	socket.on('disconnect',function()
	{
		playerJs.splicePlayer(socket);
	});
	socket.on('createPlayer',function(data)
	{
		con.query('INSERT INTO player (name) VALUES (\'' + data.name + '\')',function(err,reuslt)
		{
			if(err) throw err;
			console.log('Player: ' + data.name + ' created')
		});
	});
	socket.on('debugPW', function(data)
	{
		con.query('UPDATE player SET password = \'' + data.password + '\',salt = \''+ data.salt  + '\' where name = \'Davyjones\' ',function(err,reuslt)
		{
			if(err) throw err;
		});
	});
});
