package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {

	GamePanel gamePanel;
	Graphics2D g2;
	Font arial_40, arial_80B;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		arial_40 = new Font("Arial", Font.PLAIN, 40); // instantiate font so not in game loop
		arial_80B = new Font("Arial", Font.BOLD, 80);
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.WHITE);
		
		
		if(gamePanel.gameState == gamePanel.playState) { // play state
			
		} else if(gamePanel.gameState == gamePanel.pauseState) { // pause state
			drawPauseScreen();
		} else if (gamePanel.gameState == gamePanel.dialogueState) { // dialogue state
			drawDialogueScreen();
		}
	}
	
	public void drawPauseScreen() {
		String text = "Paused";
		int x = getXCenterText(text);
		int y = gamePanel.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		// window for dialogue
		int x = gamePanel.tileSize*3;
		int y = gamePanel.tileSize*8;
		int width = gamePanel.screenWidth - (gamePanel.tileSize*6);
		int height = gamePanel.tileSize*3;
		
		drawSubWindow(x, y, width, height);
		
		x += gamePanel.tileSize;
		y += gamePanel.tileSize;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
	
	public void drawSubWindow(int x, int y, int w, int h) {
		Color c = new Color(0,0,0, 180);
		g2.setColor(c);
		g2.fillRoundRect(x, y, w, h, 35, 35);
		
		// white border for the rectangle above
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(x+2, y+2, w-4, h-4, 25, 25);
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
