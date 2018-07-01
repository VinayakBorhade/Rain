package com.thecherno.rain;

import com.thecherno.rain.entity.mob.Player;
import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.graphics.Sprite;
import com.thecherno.rain.input.Keyboard;
import com.thecherno.rain.input.Mouse;
import com.thecherno.rain.level.Level;
import com.thecherno.rain.level.RandomLevel;
import com.thecherno.rain.level.SpawnLevel;
import com.thecherno.rain.level.TileCoordinate;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	private static int width=300;
	private static int height=168;	/*width/16*9;*/
	private static int scale=3;
	public static String title="Rain";
	private boolean running=false;
	private JFrame frame;
	private Thread thread;
	private Level level;
	private Keyboard key;
	private Player player;
	private Screen screen;
	
	private BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	private int[] pixels=((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(){
		Dimension size=new Dimension(width*scale,height*scale);
		setPreferredSize(size);
		screen=new Screen(width,height);
		frame=new JFrame();
		key=new Keyboard();
		//level=Level.spawn;
		level=Level.sampleLevel;
		//TileCoordinate playerSpawn=new TileCoordinate(19,25);
		TileCoordinate playerSpawn=new TileCoordinate(10,3);
		player=new Player(playerSpawn.x(),playerSpawn.y(),key);
		player.init(level);
		addKeyListener(key);
		
		Mouse mouse=new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public static int getWindowWidth() {
		return width*scale;
	}
	
	public static int getWindowHeight() {
		return height*scale;
	}
	
	public synchronized void start(){
		running=true;
		thread=new Thread(this,"Display");
		thread.start();
	}
	
	public synchronized void stop(){
		running=false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime=System.nanoTime();
		long timer=System.currentTimeMillis();
		final double ns=1000000000.0/60;
		double delta=0;
		int frames=0;
		int updates=0;
		requestFocus();
		while(running){
			long now=System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
			if(delta>=1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis()-timer>=1000){
				timer+=1000;
				frame.setTitle(title+" | "+updates+" ups, "+frames+" fps");
				frames=0;
				updates=0;
			}
		}
	}
	public void update(){
		key.update();
		player.update();
		level.update();
	}
	
	public void render(){
		BufferStrategy bs=getBufferStrategy();
		if(bs==null){
			createBufferStrategy(2);
			return;
		}
		
		screen.clear();
		int xScroll=(int)player.x-screen.width/2;
		int yScroll=(int)player.y-screen.height/2;
		level.render(xScroll,yScroll, screen);
		player.render(screen);
					
		for(int i=0;i<pixels.length;i++){
			pixels[i]=screen.pixels[i];
		}
		
		Graphics g=bs.getDrawGraphics();
		g.drawImage(image,0,0,getWidth(),getHeight(),null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana",0,50));
		//g.fillRect(Mouse.getX()-32, Mouse.getY()-32, 64, 64);
		//if(Mouse.getButton()!=-1) g.drawString("Button: "+Mouse.getButton(), 80, 80);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		Game game=new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}
