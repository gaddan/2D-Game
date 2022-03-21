package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	
	// Screen settings
	final int originalTileSize = 16;
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48x48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768
	public final int screenHeight = tileSize * maxScreenRow; // 576
	
	// FPS for the game
	int FPS = 60;
	long currentFPS = 0; 
	
	// World settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	// System
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public UI ui = new UI(this);
	Thread gameThread;
	
	// Entity and object
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[] = new Entity[10];
	
	// game state
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true); // can improve game rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.requestFocus();
	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		//playMusic(0); // background music loop, start in keyhandler instead
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this); // "this" refers to GamePanel
		gameThread.start(); // calls run()
	}
	
	// game loop of the game
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long FPSTimer = 0;
		long FPSdrawCount = 0;

		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			FPSTimer += (currentTime - lastTime);
			lastTime = currentTime;
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				FPSdrawCount++;
			}
			if(FPSTimer >= 1000000000) {
				currentFPS = FPSdrawCount;
				FPSdrawCount = 0;
				FPSTimer = 0;
			}
			
		}
		
	}
	
	public void update() {
		if(gameState == playState) {
			player.update();
			// npc
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
		} else if(gameState == pauseState) {
			// paused
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// debugging
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		// title state
		if(gameState == titleState) {
			ui.draw(g2);
		} else { // continue main game
			
			// draw tiles
			tileM.draw(g2);
			
			// draw objects
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}
			
			// draw npcs
			for(int i = 0; i < npc.length; i++){
				if(npc[i] != null) {
					npc[i].draw(g2, this);
				}
			}
			
			// draw player
			player.draw(g2);
			
			// overlay
			ui.draw(g2);
		}
		
		// debugging
		if(keyH.checkDrawTime) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.WHITE);
			g2.drawString("Draw time: " + passed, 10, 400);
			g2.drawString("FPS: " + currentFPS, 10, 370);
			System.out.println(passed);
		}

		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
