package com.priates.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private String mapName;
	
	public Map(String mapName,OrthographicCamera camera)
	{
		this.mapName = mapName;
		this.camera = camera;
		
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load(mapName);
		
		renderer = new OrthogonalTiledMapRenderer(map);
	}
	
	public void render()
	{
		renderer.setView(camera);
		renderer.render();
	}

	
	
	public void dispose()
	{
		map.dispose();
		renderer.dispose();
	}
	
}
