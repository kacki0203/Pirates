package com.pirates.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pirates.game.Pirates;

public class LoginScreen implements Screen
{
	
	private Game 		game;
	private Stage 		stage;
	private Skin 		skin;
	private TextButton 	playButton;
	private TextField 	passwordInput;
	private TextField 	userInput;
	private Texture 	backGroundImage;
	private Sprite 		backGoundSprite;
	private Label 		userNameLabel;
	private Label 		passwordLabel;
	
	public LoginScreen(Game game)
	{
		this.game = game;
		this.stage = new Stage();
		
		Gdx.input.setInputProcessor(this.stage);
        
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Arcon.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 24;
        params.color = Color.BLACK;
        BitmapFont font24 = generator.generateFont(params);
        
        this.backGroundImage = new Texture("badlogic.jpg");
        this.backGoundSprite = new Sprite(this.backGroundImage);
        this.backGoundSprite.setSize(800,800);
        
		this.skin = new Skin();
		this.skin.addRegions(new TextureAtlas("ui/uiskin.atlas"));
        this.skin.add("default-font", font24);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		
		this.playButton = new TextButton("Play",skin,"default");
		this.playButton.setPosition(250,100);
		this.playButton.setSize(100,50);
	
		this.passwordLabel = new Label("Password:",skin);
		this.passwordLabel.setPosition(150,250);
		
		this.userNameLabel = new Label("Username:",skin);
		this.userNameLabel.setPosition(150,350);
		
		
		this.passwordInput = new TextField("",skin);
		this.passwordInput.setSize(300,50);
		this.passwordInput.setPasswordMode(true);
		this.passwordInput.setPasswordCharacter(("#").charAt(0));
		this.passwordInput.setPosition(150,200);

		
		this.userInput = new TextField("",skin);
		this.userInput.setSize(300,50);
		this.userInput.setPosition(150,300);

		this.playButton.addListener(new ClickListener(){
			
			@Override
			public void clicked (InputEvent event, float x, float y) {
				System.out.println(passwordInput.getText());
			}		
			
		});
		stage.addActor(this.passwordInput);
		stage.addActor(this.playButton);
		stage.addActor(this.userInput);
		stage.addActor(this.passwordLabel);
		stage.addActor(this.userNameLabel);
	}

	@Override
	public void show()
	{
		
	}

	@Override
	public void render(float delta)
	{
		
		Pirates.batch.begin();
			backGoundSprite.draw(Pirates.batch);
		Pirates.batch.end();
		stage.draw();

	}

	@Override
	public void resize(int width,int height)
	{
		
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void dispose()
	{
		
	}

}
