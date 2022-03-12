package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	
	// Screen settings
	final int originalTileSize = 16;
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48x48
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768
	final int screenHeight = tileSize * maxScreenRow; // 576
	
	// FPS for the game
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this, keyH);
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true); // can improve game rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.requestFocus();
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
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
			
		}
		
	}
	
	public void update() {
		player.update();
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		player.draw(g2);
		g2.dispose();
	}
}