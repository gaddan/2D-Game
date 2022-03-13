package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class ObjectPlacer {
	
	GamePanel gamePanel;
	
	public ObjectPlacer(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void setObject() {
		gamePanel.obj[0] = new OBJ_Key();
		gamePanel.obj[0].worldX = 23 * gamePanel.tileSize;
		gamePanel.obj[0].worldY = 7 * gamePanel.tileSize;
		
		gamePanel.obj[1] = new OBJ_Key();
		gamePanel.obj[1].worldX = 23 * gamePanel.tileSize;
		gamePanel.obj[1].worldY = 40 * gamePanel.tileSize;
		
		gamePanel.obj[2] = new OBJ_Chest();
		gamePanel.obj[2].worldX = 15 * gamePanel.tileSize;
		gamePanel.obj[2].worldY = 24 * gamePanel.tileSize;
		
		gamePanel.obj[3] = new OBJ_Door();
		gamePanel.obj[3].worldX = 21 * gamePanel.tileSize;
		gamePanel.obj[3].worldY = 24 * gamePanel.tileSize;
		
		gamePanel.obj[4] = new OBJ_Door();
		gamePanel.obj[4].worldX = 19 * gamePanel.tileSize;
		gamePanel.obj[4].worldY = 24 * gamePanel.tileSize;
		
		gamePanel.obj[5] = new OBJ_Boots();
		gamePanel.obj[5].worldX = 14 * gamePanel.tileSize;
		gamePanel.obj[5].worldY = 27 * gamePanel.tileSize;
	}
}
