package com.thecherno.rain.level.tile.spawn_level;

import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.graphics.Sprite;
import com.thecherno.rain.level.tile.Tile;

public class SpawnWaterTile extends Tile {
	
	private final String tileName="SpawnWaterTile";
	
	public String getName() {return tileName;}
	
	public SpawnWaterTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x,int y,Screen screen){
		screen.renderTile(x<<4, y<<4, this);
	}

}
