package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
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
	public EventHandler eventH = new EventHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public UI ui = new UI(this);
	Thread gameThread;
	
	// Entity and object
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	ArrayList<Entity> entityList = new ArrayList<>();
	
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

			// add entities to the list
			entityList.add(player);
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			
			// sorting entities
			Collections.sort(entityList, new Comparator<Entity>() {
				// comparing entities on y value
				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});
			
			
			// drawing all entities
			for(int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2, this);
			}
			entityList.clear();
			
			// overlay
			ui.draw(g2);
		}
		
		// debugging
		if(keyH.checkDrawTime) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
			g2.drawString("Draw time: " + passed, 10, 400);
			g2.drawString("FPS: " + currentFPS, 10, 420);
			// position
			int x = player.worldX/tileSize;
			int y = player.worldY/tileSize;
			g2.drawString("X: " + x, 10, 440);
			g2.drawString("Y: " + y, 10, 460);
			// current state
			g2.drawString("State: " + gameState, 10, 480);
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
