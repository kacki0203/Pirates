package com.pirates.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pirates.screen.GameScreen;
import com.priates.map.Map;

public class Pirates extends Game
{

	public static SpriteBatch batch;
	private OrthographicCamera camera;
	private Map currentMap;
	private Map map;
	@Override
	public void create()
	{
		this.setScreen(new GameScreen(this));
		System.out.println("JA");
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		this.map = new Map("maps/grassMap.tmx",camera);
	}
	
	public void resize(int width,int height)
	{
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();
	}
	
	public void dispose()
	{
		this.map.dispose();
	}
	
	@Override
	public void render()
	{
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			camera.position.x += 200*Gdx.graphics.getDeltaTime();
		}	
		camera.update();
		super.render();
		this.map.render();

	}
}
