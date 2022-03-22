package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.print.attribute.HashPrintRequestAttributeSet;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

public class UI {

	GamePanel gamePanel;
	Graphics2D g2;
	Font smallPixelFont;
	BufferedImage heart_full, heart_half, heart_empty;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		InputStream is = getClass().getResourceAsStream("/font/small_pixel.ttf");
		try {
			smallPixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		// creating HUD object
		SuperObject heart = new OBJ_Heart(gamePanel);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_empty = heart.image3;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(smallPixelFont);
		g2.setColor(Color.WHITE);
		
		// title state
		if(gamePanel.gameState == gamePanel.titleState) {
			drawTitleScreen();
		}
		
		// play state
		if(gamePanel.gameState == gamePanel.playState) { // play state
			drawPlayerLife();
		// pause state
		} else if(gamePanel.gameState == gamePanel.pauseState) { // pause state
			drawPauseScreen();
			drawPlayerLife();
		// dialogue state
		} else if (gamePanel.gameState == gamePanel.dialogueState) { // dialogue state
			drawDialogueScreen();
			drawPlayerLife();
		}
	}
	
	public void drawPlayerLife() {
		int x = gamePanel.tileSize/4;
		int y = gamePanel.tileSize/4;
		int i = 0;
		
		// draws max life
		while(i < gamePanel.player.maxLife/2) {
			g2.drawImage(heart_empty, x, y, null);
			i++;
			x += gamePanel.tileSize + gamePanel.tileSize/4;
		}
		
		x = gamePanel.tileSize/4;
		y = gamePanel.tileSize/4;
		i = 0;
		while(i < gamePanel.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gamePanel.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gamePanel.tileSize + gamePanel.tileSize/4;
		}
		
	}
	
	public void drawTitleScreen() {
		// title
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
		String text = "Adventure Game";
		int x = getXCenterText(text);
		int y = gamePanel.tileSize*3;
		g2.setColor(Color.GRAY); // shadow effect
		g2.drawString(text, x+5, y+5); // shadow effect
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);
		
		// menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
		
		text = "New Game";
		x = getXCenterText(text);
		y += gamePanel.tileSize*3;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", getXMarkerLeftSide(text), y);
			g2.drawString("<", getXMarkerRightSide(text), y);
		}
		
		text = "Load Game";
		x = getXCenterText(text);
		y += gamePanel.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", getXMarkerLeftSide(text), y);
			g2.drawString("<", getXMarkerRightSide(text), y);
		}
		
		text = "Quit";
		x = getXCenterText(text);
		y += gamePanel.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", getXMarkerLeftSide(text), y);
			g2.drawString("<", getXMarkerRightSide(text), y);
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
	
	public int getXMarkerRightSide(String text) {
		 return getXCenterText(text) + (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() + gamePanel.tileSize;
	}
	
	public int getXMarkerLeftSide(String text) {
		 return getXCenterText(text) - gamePanel.tileSize;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
}
