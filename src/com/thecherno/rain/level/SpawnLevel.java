package com.thecherno.rain.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.thecherno.rain.level.tile.Tile;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
		loadLevel(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image=ImageIO.read(SpawnLevel.class.getResource(path));
			int h=height=image.getHeight();
			int w=width=image.getWidth();
			tiles=new int[w*h];
			image.getRGB(0,0, w, h, tiles, 0, w);
			System.out.println("width,height: "+width+" , "+height);
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
	}
	
	// grass=0xff00
	// flower=0xffff00
	// rock=0x7f7f00
	protected void generateLevel() {
		
	}
	
}
