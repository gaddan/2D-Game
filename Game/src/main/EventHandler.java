package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gamePanel;
	EventRect eventRect[][];
	int prevEventX, prevEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		eventRect = new EventRect[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		int col = 0;
		int row = 0;
		while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			col++;
			if(col == gamePanel.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void checkEvent() {
		
		// check that player is 1 tile away from the last occurred event
		int xDistance = Math.abs(gamePanel.player.worldX - prevEventX);
		int yDistance = Math.abs(gamePanel.player.worldY - prevEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gamePanel.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent) {
			//if(hit(27, 16, "any") == true) { damagePit(27, 16, gamePanel.dialogueState);}
			//if(hit(24, 12, "any") == true) { healingPool(24, 12, gamePanel.dialogueState);}
			//if(hit(27, 14, "any") == true) { teleportPlayer(27, 14, gamePanel.dialogueState);}
			//if(hit(25, 38, "any") == true) { pickUpKey(25, 38, gamePanel.playState);}
			//if(hit(39, 39, "any") == true) { pickUpKey(39, 39, gamePanel.playState);}
			//if(hit(39, 8, "any") == true) { pickUpKey(39, 8, gamePanel.playState);}
		}

	}
	
	public boolean hit(int col, int row, String reqDirection) {
		boolean hit = false;
		
		gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
		gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
		eventRect[col][row].x = col*gamePanel.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row*gamePanel.tileSize + eventRect[col][row].y;
		
		if(gamePanel.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if(gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				prevEventX = gamePanel.player.worldX;
				prevEventY = gamePanel.player.worldY;
			}
		}
		
		gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
		gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		return hit;
	}
	
	public void damagePit(int col, int row, int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.ui.currentDialogue = "You fell!";
		gamePanel.player.life -= 1;
		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healingPool(int col, int row, int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.ui.currentDialogue = "The mere presence of/n the lake heals you..";
		gamePanel.player.life = gamePanel.player.maxLife;
	}
	
	public void teleportPlayer(int col, int row, int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.ui.currentDialogue = "Some mysterious magic teleported you!";
		gamePanel.player.worldX = gamePanel.tileSize*37;
		gamePanel.player.worldY = gamePanel.tileSize*10;
	}
	
	public void pickUpKey(int col, int row, int gameState) {
		gamePanel.gameState = gameState;
		//gamePanel.ui.currentDialogue = "Found a key!";
		//gamePanel.obj.get(gameState);
		System.out.println(gamePanel.obj.size());
		for(int i = 0; i < gamePanel.obj.size(); i++) {
			if(gamePanel.obj.get(i).worldX == col*gamePanel.tileSize && gamePanel.obj.get(i).worldY == row*gamePanel.tileSize) {
				gamePanel.obj.remove(i);
				gamePanel.playSE(1);
			}
		}
		eventRect[col][row].eventDone = true;
		gamePanel.player.keys++;
		//gamePanel.player.worldX = gamePanel.tileSize*37;
		//gamePanel.player.worldY = gamePanel.tileSize*10;
	}
	
}
