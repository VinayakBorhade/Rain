package com.thecherno.rain.entity.mob;

import com.thecherno.rain.Game;
import com.thecherno.rain.entity.projectile.Projectile;
import com.thecherno.rain.entity.projectile.WizardProjectile;
import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.graphics.Sprite;
import com.thecherno.rain.input.Keyboard;
import com.thecherno.rain.input.Mouse;

public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	//private Sprite prevSprite;
	private int anim=0;
	private boolean walking=false;
	
	Projectile p;
	private int fireRate=0;
	private static final int JUMP_HEIGHT=10;
	
	private int[] jump_sequence= new int[JUMP_HEIGHT+1];
	private int jumpIndex=JUMP_HEIGHT;
	
	
	public Player(Keyboard input) {
		this.input=input;
		this.sprite=Sprite.player_forward;
		
		for(int i=0;i<JUMP_HEIGHT;i++) jump_sequence[i]=JUMP_HEIGHT-(i+1);
		jump_sequence[JUMP_HEIGHT]=0;
	}
	
	public Player(int x,int y,Keyboard input) {
		this.x=x;
		this.y=y;
		this.input=input;
		this.sprite=Sprite.player_forward;
		fireRate=WizardProjectile.FIRE_RATE;
		
		for(int i=0;i<JUMP_HEIGHT;i++) jump_sequence[i]=JUMP_HEIGHT-(i+1);
		jump_sequence[JUMP_HEIGHT-1]=0;
	}
	
	public void update() {
		if(fireRate>0) fireRate--;
		double xa=0.0,ya=0.0;
		anim++;
		if(anim>10000) anim=0;
		
		//if(input.up) ya--;
		//if(input.down) ya++;

		if(jumpIndex<JUMP_HEIGHT-1 && jumping) jumpIndex++;		
		else if(input.up && collision( 0, (int)ya+1 ) ) {/* still on the ground and upArrow/W is presssed */
			if(jumpIndex==JUMP_HEIGHT) {
				jumpIndex=0;
				jumping=true;
			}
		}
		else if(jumpIndex == JUMP_HEIGHT-1) {
			jumping=false;
			jumpIndex++;
		}

		ya=-jump_sequence[jumpIndex]*5/4;
		ya=fall(ya);
		
		
		if(input.left) xa--;
		if(input.right) xa++;
		
		if(xa!=0.0 || ya!=0.0) {
			move(xa,ya);
			walking=true;
		} else {
			walking=false;
		}
		clear();
		updateShooting();
	}
	
	private double fall(double ya) {
		if( !collision( 0, (int)ya +1) ) {
			ya+=gravity;
			falling=true;
		}else {
			ya=0.0;
			falling=false;
		}
		return ya;
	}

	/*private int jump(int ya) {
		//if falling is true put ya=0
		if(falling) ya =0;
		//else ya+=
	}*/

	private void clear() {
		for(int i=0;i<level.getProjectiles().size();i++) {
			Projectile p=level.getProjectiles().get(i);
			if(p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {
		if(Mouse.getButton()==1&&fireRate<=0) {
			double dx=Mouse.getX() - Game.getWindowWidth()/2;
			double dy=Mouse.getY() - Game.getWindowHeight()/2;
			double dir=Math.atan2(dy,dx);
			shoot((int)x,(int)y,dir);
			fireRate=WizardProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {
		/*
		int xx=x-16;
		int yy=y-16;
		
		screen.renderPlayer(xx, yy, Sprite.player0);
		screen.renderPlayer(xx+16, yy, Sprite.player1);
		screen.renderPlayer(xx, yy+16, Sprite.player2);
		screen.renderPlayer(xx+16, yy+16, Sprite.player3);
		*/
		//prevSprite=sprite;
		if(dir==0) {
			sprite=Sprite.player_forward;
			if(walking) {
				if(anim%20<10) sprite=Sprite.player_forward1;
				else sprite=Sprite.player_forward2;
			}
			//else sprite=prevSprite;
		}
		if(dir==1) {
			sprite=Sprite.player_side;
			if(walking) {
				if(anim%20<10) sprite=Sprite.player_side1;
				else sprite=Sprite.player_side2;
			}
			//else sprite=prevSprite;
		}
		if(dir==2) {
			sprite=Sprite.player_down;
			if(walking) {
				if(anim%20<10) sprite=Sprite.player_down1;
				else sprite=Sprite.player_down2;
			}
			//else sprite=prevSprite;
		}
		if(dir==3) {
			sprite=Sprite.player_side;
			if(walking) {
				if(anim%20<10) sprite=Sprite.player_side1;
				else sprite=Sprite.player_side2;
			}
			//else sprite=prevSprite;
		}
		
		screen.renderPlayer((int)x-16, (int)y-16, sprite,dir==3);
	}
	
}
