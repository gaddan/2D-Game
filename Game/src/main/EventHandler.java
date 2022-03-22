package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gamePanel;
	Rectangle eventRect;
	int eventRectDefaultX;
	int eventRectDefaultY;
	
	public EventHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		eventRect = new Rectangle();
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 2;
		eventRect.height = 2;
		eventRectDefaultX = eventRect.x;
		eventRectDefaultY = eventRect.y;
	}
	
	public void checkEvent() {
		if(hit(27, 16, "right") == true) { damagePit(gamePanel.dialogueState);}
		if(hit(27, 15, "up") == true) { healingPool(gamePanel.dialogueState);}
		if(hit(27, 14, "right") == true) { teleportPlayer(gamePanel.dialogueState);}
	}
	
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		boolean hit = false;
		
		gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
		gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
		eventRect.x = eventCol*gamePanel.tileSize + eventRect.x;
		eventRect.y = eventRow*gamePanel.tileSize + eventRect.y;
		
		if(gamePanel.player.solidArea.intersects(eventRect)) {
			if(gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}
		
		gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
		gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
		eventRect.x = eventRectDefaultX;
		eventRect.y = eventRectDefaultY;
		return hit;
	}
	
	public void damagePit(int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.ui.currentDialogue = "You fell!";
		gamePanel.player.life -= 1;
	}
	
	public void healingPool(int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.ui.currentDialogue = "The grace of the pool heals you..";
		gamePanel.player.life = gamePanel.player.maxLife;
	}
	
	public void teleportPlayer(int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.ui.currentDialogue = "Some mysterious magic teleported you!";
		gamePanel.player.worldX = gamePanel.tileSize*37;
		gamePanel.player.worldY = gamePanel.tileSize*10;
	}
}
