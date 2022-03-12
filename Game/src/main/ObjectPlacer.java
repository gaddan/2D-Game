package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_key;

public class ObjectPlacer {
	
	GamePanel gamePanel;
	
	public ObjectPlacer(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void setObject() {
		gamePanel.obj[0] = new OBJ_key();
		gamePanel.obj[0].worldX = 23 * gamePanel.tileSize;
		gamePanel.obj[0].worldY = 7 * gamePanel.tileSize;
		
		gamePanel.obj[1] = new OBJ_key();
		gamePanel.obj[1].worldX = 23 * gamePanel.tileSize;
		gamePanel.obj[1].worldY = 40 * gamePanel.tileSize;
		
		gamePanel.obj[2] = new OBJ_Chest();
		gamePanel.obj[2].worldX = 15 * gamePanel.tileSize;
		gamePanel.obj[2].worldY = 20 * gamePanel.tileSize;
		
		gamePanel.obj[3] = new OBJ_Door();
		gamePanel.obj[3].worldX = 10 * gamePanel.tileSize;
		gamePanel.obj[3].worldY = 22 * gamePanel.tileSize;
	}
}
