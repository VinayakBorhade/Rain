package com.thecherno.rain.entity.particle;

import com.thecherno.rain.entity.Entity;
import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.graphics.Sprite;

public class Particle extends Entity {
	
	private Sprite sprite;
	
	private int life;
	private int time=0;
	
	protected double xx,yy;
	protected double xa,ya;
	
	public Particle(int x,int y,int life){
		this.x=x;
		this.y=y;
		this.xx=x;
		this.yy=y;
		this.life=life+(random.nextInt(20)-10);
		this.sprite=Sprite.particle_normal;
		
		this.xa=random.nextGaussian();
		this.ya=random.nextGaussian();
		
	}
	
	public void update(){
		time++;
		if(time>=7400) time=0;
		
		if(time>life) remove();
		System.out.println("time, life, removed: "+time+" , "+life+" , "+isRemoved());
		this.xx+=xa;
		this.yy+=ya;
	}
	
	public void render(Screen screen){
		screen.renderSprite((int)xx, (int)yy, sprite, true);
	}
	
}
