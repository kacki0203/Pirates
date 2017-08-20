package com.pirates.game;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.pirates.screen.LoginScreen;

import de.pirates.player.Player;

public class PiratesMain extends ApplicationAdapter {
	private final float UPDATE_TIME = 1/60f;
	float timer;
	SpriteBatch batch;
	private Socket socket;
	private Player player;
	private Texture pTexture;
	private Texture enemy;
	private HashMap<String,Player> map_players;
	@Override
	public void create () {

		batch = new SpriteBatch();
	
		
		pTexture = new Texture("skeleton1_lit.png");
		enemy = new Texture("char.png");
		map_players = new HashMap<String,Player>();
		connectSocket();
		JSONObject data = new JSONObject();
		try
		{
			data.put("name","Davyjones");
			socket.emit("playerConnected",data);
		}
		catch(JSONException e)
		{
			Gdx.app.log("SOCKETIO",e.getMessage());
		}
		configSocketEvents();

	}
	public void handleInput(float dt)
	{
		if(player != null)
		{
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			{
				player.setPosition(player.getX() + (-200 * dt),player.getY());
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			{
				player.setPosition(player.getX() + (+200 * dt),player.getY());
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.C))
			{
				TextIInput listener = new TextIInput();
				Gdx.input.getTextInput(listener, "Dialog Title", "Initial Textfield Value", "Hint Value");
			}
			
			
		}
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleInput(Gdx.graphics.getDeltaTime());
		updateServer(Gdx.graphics.getDeltaTime());
		batch.begin();
		if(player != null)
		{
			player.draw(batch);
		}
		for(HashMap.Entry<String,Player> entry : map_players.entrySet())
		{
			entry.getValue().draw(batch);
		}
		batch.end();
	}
	
	public void updateServer(float dt)
	{
		timer += dt;
		if(timer >= UPDATE_TIME && player != null && player.hasMoved())
		{
			JSONObject data = new JSONObject();
			try
			{
				data.put("x",player.getX());
				data.put("y",player.getY());
				socket.emit("playerMoved",data);
			}
			catch(JSONException e)
			{
				Gdx.app.log("SOCKETIO",e.getMessage());
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		pTexture.dispose();
		enemy.dispose();	
		}
	
	private void connectSocket()
	{
		try
		{
			
			socket = IO.socket("http://localhost:8080");
			socket.connect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void configSocketEvents()
	{
		socket.on(Socket.EVENT_CONNECT,new Emitter.Listener()
		{		
			@Override
			public void call(Object...args)
			{
				
				Gdx.app.log("SOCKETID","Connected to server");
			}
		}).on("socketID",new Emitter.Listener()
		{
			
			@Override
			public void call(Object...args)
			{
				JSONObject data = (JSONObject) args[0];
				try
				{
					String id = data.getString("id");
					Gdx.app.log("SOCKETIO","ClientId: " + id);
				}
				catch(JSONException e)
				{
					Gdx.app.log("SOCKETIO",e.getMessage());
				}
			}
		}).on("newPlayer",new Emitter.Listener()
		{
			
			@Override
			public void call(Object...args)
			{
				JSONObject data = (JSONObject) args[0];
				try
				{
					String playerId = data.getString("id");
					Gdx.app.log("SOCKETIO","New Player: " + playerId);
					map_players.put(playerId,new Player(enemy));
				}
				catch(JSONException e)
				{
					Gdx.app.log("SOCKETIO",e.getMessage());
				}
			}
		}).on("playerDisconnected",new Emitter.Listener()
		{
			
			@Override
			public void call(Object...args)
			{
				JSONObject data = (JSONObject) args[0];
				try
				{
					String id = data.getString("id");
					Gdx.app.log("SOCKETIO","Player disconnected: " + id);
					map_players.remove(id);
				}
				catch(JSONException e)
				{
					Gdx.app.log("SOCKETIO",e.getMessage());
				}
			}
		}).on("getPlayers",new Emitter.Listener()
		{
			
			@Override
			public void call(Object...args)
			{
				JSONArray objects = (JSONArray) args[0];
				try
				{
					for(int i = 0;i< objects.length();i++)
					{
						Player player = new Player(enemy);
						Vector2 pos = new Vector2();
						pos.x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
						pos.y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
						player.setPosition(pos.x,pos.y);
						
						map_players.put(objects.getJSONObject(i).getString("id"),player);
					}
				}
				catch(JSONException e)
				{
					Gdx.app.log("SOCKETIO",e.getMessage());
				}
			}
		}).on("playerMoved",new Emitter.Listener()
		{
			
			@Override
			public void call(Object...args)
			{
				JSONObject data = (JSONObject) args[0];
				try
				{
					String playerId = data.getString("id");
					Double x = data.getDouble("x");
					Double y = data.getDouble("y");
					if(map_players.get(playerId) != null)
					{
						map_players.get(playerId).setPosition(x.floatValue(),y.floatValue());
					}
				}
				catch(JSONException e)
				{
					Gdx.app.log("SOCKETIO",e.getMessage());
				}
			}
		}).on("playerLog",new Emitter.Listener()
		{
			@Override
			public void call(Object...args)
			{
				JSONObject data = (JSONObject) args[0];
				try
				{
					Gdx.app.log("SOCKETIO","Get credentials");
					String salt = data.getString("salt");
					MessageDigest md1 =  MessageDigest.getInstance("SHA-256");
					String tmppw = "MyPassWord" + salt;
					md1.update(tmppw.getBytes("UTF-8")); // Change this to "UTF-16" if needed
					byte[] digest = md1.digest();
					String passw = new String(digest, StandardCharsets.UTF_8);
					JSONObject credentials = new JSONObject();
					credentials.put("password",passw);
					credentials.put("name","Davyjones");
					socket.emit("validateCredentials",credentials);
				}
				catch(JSONException e)
				{
					Gdx.app.log("SOCKETIO",e.getMessage());
				}
				catch(UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				catch(NoSuchAlgorithmException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).on("loginOK", new Emitter.Listener()
		{
			
			@Override
			public void call(Object...args)
			{
				Gdx.app.log("Player created","OK");
				player = new Player(pTexture);
			}
		});
	}
	
}
