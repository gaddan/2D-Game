package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
		int entityRightCol = entityRightWorldX/gamePanel.tileSize;
		int entityTopRow = entityTopWorldY/gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gamePanel.tileM.tile[tileNum1].collision == true || 
					gamePanel.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gamePanel.tileM.tile[tileNum1].collision == true || 
					gamePanel.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gamePanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gamePanel.tileM.tile[tileNum1].collision == true || 
					gamePanel.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gamePanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gamePanel.tileM.tile[tileNum1].collision == true || 
					gamePanel.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for (int i = 0; i < gamePanel.obj.size(); i++) {
			if(gamePanel.obj.get(i) != null) {
				// get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				// get object's solid area position
				gamePanel.obj.get(i).solidArea.x = gamePanel.obj.get(i).worldX + gamePanel.obj.get(i).solidArea.x;
				gamePanel.obj.get(i).solidArea.y = gamePanel.obj.get(i).worldY + gamePanel.obj.get(i).solidArea.y;
				
				switch (entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if(entity.solidArea.intersects(gamePanel.obj.get(i).solidArea)) {
					if(gamePanel.obj.get(i).collision == true) {
						entity.collisionOn = true;
					}
					if(player == true) {
						index = i;
					}						
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gamePanel.obj.get(i).solidArea.x = gamePanel.obj.get(i).solidAreaDefaultX;
				gamePanel.obj.get(i).solidArea.y = gamePanel.obj.get(i).solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	
	// npc or monster
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for (int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				// get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				// get object's solid area position
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
				
				switch (entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if(entity.solidArea.intersects(target[i].solidArea)) {
					if(target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}					
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
			
		}
		
		return index;	
	}
	
	// npc collision with player
	public boolean checkPlayer(Entity entity) {
		
		boolean touchPlayer = false;
		
		// get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		// get object's solid area position
		gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
		gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
		
		switch (entity.direction) {
		case "up": entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left": entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
		}
		
		if(entity.solidArea.intersects(gamePanel.player.solidArea)) {
			entity.collisionOn = true;	
			touchPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
		gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
		
		return touchPlayer;
	}
}
