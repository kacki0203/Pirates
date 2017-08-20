package de.pirates.test;

import io.socket.emitter.Emitter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;

public class Test
{

	public static void main(String[] args)
	{
	    SecureRandom random = new SecureRandom();
	    MessageDigest md;
		try
		{
		      byte bytes[] = new byte[20];
		      random.nextBytes(bytes);
		      String str = new String(bytes, StandardCharsets.UTF_8);
			md = MessageDigest.getInstance("SHA-256");
		    String text = "MyPassWord" + str;

		    md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
		    byte[] digest = md.digest();
		    String pw = new String(digest, StandardCharsets.UTF_8);
		}
		catch(NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * 
		
						JSONObject pws = new JSONObject();
				try
				{
					pws.put("name","Davyjones");
					socket.emit("loggin",pws);
				}
				catch(JSONException e)
				{
					Gdx.app.log("SOCKETIO",e.getMessage());
				}
	 * 
	 * .on("playerLog", new Emitter.Listener()
		{
			@Override
			public void call(Object...args)
			{
				JSONObject data = (JSONObject) args[0];
				try
				{
					Gdx.app.log("SOCKETIO USERDATA",data.toString());
					//MessageDigest md1 = null;
					String salt = data.getString("salt");
					//String dataPW = data.getString("passsword");
					/*String tmppw = "MyPassWord" + salt;
					md1.update(tmppw.getBytes("UTF-8")); // Change this to "UTF-16" if needed
					byte[] digest = md1.digest();
					String passw = new String(digest, StandardCharsets.UTF_8);
					if(passw == dataPW)
						Gdx.app.log("SOCKETIO","CORRECT");
					Gdx.app.log("SOCKETIO",dataPW + " " + passw);								
					
				}
				catch(JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*catch(UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		})
	 */

}
