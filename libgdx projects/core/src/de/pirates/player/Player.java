package de.pirates.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite
{
	private Vector2 pastPosition;
	public Player(Texture texture)
	{
		super(texture);
		pastPosition = new Vector2(getX(),getY());
	}
	
	public boolean hasMoved()
	{
		if(pastPosition.x != getX() || pastPosition.y != getY())
		{
			pastPosition.x = getX();
			pastPosition.y = getY();
			return true;
		}
		else
			return false;
	}
	
}
