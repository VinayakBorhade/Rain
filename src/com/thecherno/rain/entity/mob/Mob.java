package com.thecherno.rain.entity.mob;

import com.thecherno.rain.entity.Entity;
import com.thecherno.rain.entity.projectile.Projectile;
import com.thecherno.rain.entity.projectile.WizardProjectile;
import com.thecherno.rain.graphics.Sprite;

public abstract class Mob extends Entity{
	protected Sprite sprite;
	protected int dir=0;
	protected double gravity=1.1;
	protected boolean moving=false;
	protected boolean walking=false;
	protected boolean falling=false;
	protected boolean jumping=false;
	
	public void move(double xa,double ya) {
		if(xa!=0.0 && ya!=0.0 ) {
			move(xa,0);
			move(0,ya);
			return;
		}
		
		if(xa>0.0) dir=1;		/*right*/
		else if(xa<0.0) dir=3;		/*left*/
		//else if(ya>0.0 && jumping) dir=2;		/*down*/
		//else if(ya<0.0) dir=0;		/*forward,north*/
		
		if(!collision( (int)xa, (int)ya )) {
			x+=xa;
			y+=ya;
		}
	}
	
	public void update() {
		
	}
	
	protected void shoot(int x,int y,double dir) {
		//dir*=(180/Math.PI);
		Projectile p=new WizardProjectile(x,y,dir);
		level.add(p);
	}
	
	protected boolean collision(int xa,int ya) {
		boolean solid=false;
		for(int c=0;c<4;c++) {
			int xt=(( (int)x+xa) + c % 2 * 14 - 8)>>4;
			int yt=(( (int)y+ya) + c / 2 * 12 + 3)>>4;
			if(level.getTile(xt, yt).solid()) solid=true;
			
			//System.out.println("Tile : "+level.getTile(xt, yt).getName() );
			//System.out.println("solid: "+solid);
		}
		return solid;
	}
	
	public void render() {
		
	}

}
