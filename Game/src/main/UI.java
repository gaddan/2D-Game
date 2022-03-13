package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {

	GamePanel gamePanel;
	Font arial_40, arial_80B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		arial_40 = new Font("Arial", Font.PLAIN, 40); // instantiate font so not in game loop
		arial_80B = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}
	
	public void draw(Graphics2D g2) {
		
		if(gameFinished == true) {
			
			g2.setFont(arial_40);
			g2.setColor(Color.WHITE);
			
			String text;
			int textLenght;
			int x;
			int y;
			
			text = "You found the treasure!";
			textLenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // length of text
			
			x = gamePanel.screenWidth/2 - textLenght/2;
			y = gamePanel.screenWidth/2 - gamePanel.tileSize*3;
			g2.drawString(text, x, y);
			
			g2.setFont(arial_80B);
			g2.setColor(Color.YELLOW);
			text = "Congratulations!";
			textLenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gamePanel.screenWidth/2 - textLenght/2;
			y = gamePanel.screenWidth/2 + gamePanel.tileSize*2;
			g2.drawString(text, x, y);
			
			gamePanel.gameThread = null;
			
		}else {
		
			g2.setFont(arial_40);
			g2.setColor(Color.WHITE);
			g2.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize, gamePanel.tileSize, null);
			g2.drawString("x " + gamePanel.player.hasKey, 74, 65);
			
			if(messageOn == true) {
				g2.drawString(message, gamePanel.tileSize/2, gamePanel.tileSize*5);
				
				messageCounter++;
				
				if(messageCounter > 120) { // 120 frames -> 60f/s -> 2s until disappearing
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
}
