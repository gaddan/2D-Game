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
		//gamePanel.obj[0] = new OBJ_Key(gamePanel);
		//gamePanel.obj[0].worldX = 23 * gamePanel.tileSize;
		//gamePanel.obj[0].worldY = 7 * gamePanel.tileSize;
	}
}
