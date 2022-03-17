package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {

	GamePanel gamePanel;
	Graphics2D g2;
	Font arial_40, arial_80B;
	//BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		arial_40 = new Font("Arial", Font.PLAIN, 40); // instantiate font so not in game loop
		arial_80B = new Font("Arial", Font.BOLD, 80);
		//OBJ_Key key = new OBJ_Key(gamePanel);
		//keyImage = key.image;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.WHITE);
		
		if(gamePanel.gameState == gamePanel.playState) {
			// play state stuff
		} else if(gamePanel.gameState == gamePanel.pauseState) {
			drawPauseScreen();
		}
	}
	
	public void drawPauseScreen() {
		String text = "Paused";
		int x = getXCenterText(text);
		int y = gamePanel.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public int getXCenterText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gamePanel.screenWidth/2 - length/2;
		return x;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
}
