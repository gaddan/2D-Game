package main;

import entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
	
	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void setObject() {
		//gamePanel.obj[0] = new OBJ_Key(gamePanel);
		//gamePanel.obj[0].worldX = 23 * gamePanel.tileSize;
		//gamePanel.obj[0].worldY = 7 * gamePanel.tileSize;
	}
	
	public void setNPC() {
		
		gamePanel.npc[0] = new NPC_OldMan(gamePanel);
		gamePanel.npc[0].worldX = gamePanel.tileSize*21;
		gamePanel.npc[0].worldY = gamePanel.tileSize*21;
	}
}