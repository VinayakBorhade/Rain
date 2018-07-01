package com.thecherno.rain.graphics;

import java.util.Random;

import com.thecherno.rain.entity.projectile.Projectile;
import com.thecherno.rain.level.tile.Tile;

public class Screen {
	public int width,height;
	public int[] pixels;
	public final int MAP_SIZE=8;
	public final int MAP_SIZE_MASK=MAP_SIZE-1;
	
	public int xOffset,yOffset;
	
	public int[] tiles=new int[MAP_SIZE*MAP_SIZE];
	private Random random=new Random();
	
	private final int ALPHA_COL = 0xffff00ff;

	
	public Screen(int width,int height){
		this.width=width;
		this.height=height;
		pixels=new int[width*height];
		
		for(int i=0;i<MAP_SIZE*MAP_SIZE;i++){
			tiles[i]=random.nextInt(0xffffff);
		}
	}
	
	public void clear(){
		for(int i=0;i<pixels.length;i++)
			pixels[i]=0;
	}
	
	public void renderSprite(int xp,int yp,Sprite sprite,boolean fixed){
		if(fixed){
			xp-=xOffset;
			yp-=yOffset;
		}
		for(int y=0;y<sprite.getHeight();y++){
			int ya=y+yp;
			for(int x=0;x<sprite.getWidth();x++){
				int xa=x+xp;
				if(xa+ya*width<0||xa+ya*width>=pixels.length) continue;
				pixels[xa+ya*width]=sprite.pixels[x+y*sprite.getWidth()];
			}
		}
	}
	
	public void renderTile(int xp,int yp,Tile tile) {
		xp-=xOffset;
		yp-=yOffset;
		for(int y=0;y<tile.sprite.SIZE;y++) {
			int ya=y+yp;
			for(int x=0;x<tile.sprite.SIZE;x++) {
				int xa=x+xp;
				if(xa<-tile.sprite.SIZE||xa>=width||ya<0||ya>=width) break;
				if(xa<0) xa=0;
				if(xa+ya*width>=pixels.length) break;
				pixels[xa+ya*width]=tile.sprite.pixels[x+y*tile.sprite.SIZE];
			}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSprite().SIZE];
				if (col != ALPHA_COL) pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderPlayer(int xp,int yp,Sprite sprite,boolean lrflip) {
		
		xp-=xOffset;
		yp-=yOffset;
		for(int y=0;y<32;y++) {
			int ya=y+yp;
			int ys=y;
			for(int x=0;x<32;x++) {
				int xa=x+xp;
				int xs;
				if(lrflip) xs=31-x;
				else xs=x;
				if(xa<-32||xa>=width||ya<0||ya>=width) break;
				if(xa<0) xa=0;
				if(xa+ya*width>=pixels.length) break;
				if(x+y*32>=sprite.pixels.length) break;
				int color=sprite.pixels[xs+ys*32];
				if(color!=0xffff00ff) pixels[xa+ya*width]=color;
			}
		}
	}
	
	public void setOffset(int xOffset,int yOffset) {
		this.xOffset=xOffset;
		this.yOffset=yOffset;
	}
	
	
}
