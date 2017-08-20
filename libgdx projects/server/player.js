var index = require('./index.js');
var players = [];

function player(id,name,x,y)
{
	this.id = id;
	this.x = x;
	this.y = y;
	this.name = name;
};

var pushPlayer = function(data,socket) {
		console.log("Player " + data.name + " Connected!");
		socket.emit('socketID' , {id:socket.id});
		socket.emit("getPlayers",players);
		socket.emit("loginOK",null);
		socket.broadcast.emit("newPlayer",{id:socket.id});
        players.push(new player(data.id,data.name,0,0));
};

var splicePlayer = function(socket)
{
	console.log("Player disconnected");
	socket.broadcast.emit("playerDisconnected",{id:socket.id});
	for(var i = 0;i<players.length;i++)
	{
		if(players[i].id == socket.id)
		{
			players.splice(i,1);
		}
	}
};

var getPlayers = function()
{
	return players;
};

var playerMoved = function(data,socket)
{
	data.id = socket.id;
	socket.broadcast.emit("playerMoved",data);
	for(var i = 0;i<players.length;i++)
	{
		if(players[i].id == data.id)
		{
			players[i].x = data.x;
			players[i].y = data.y;
		}
	}
}

module.exports.pushPlayer = pushPlayer;
module.exports.getPlayers = getPlayers;
module.exports.playerMoved = playerMoved;
module.exports.splicePlayer = splicePlayer;


