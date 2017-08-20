package com.pirates.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pirates.screen.GameScreen;
import com.pirates.screen.LoginScreen;

public class Pirates extends Game
{

	public static SpriteBatch batch;
	
	@Override
	public void create()
	{
		this.setScreen(new GameScreen(this));
		System.out.println("JA");
		batch = new SpriteBatch();
	}
	
	@Override
	public void render()
	{
		super.render();
	}
}
