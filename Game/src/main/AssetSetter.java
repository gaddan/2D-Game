package main;

import entity.NPC_Book;
import entity.NPC_Computer;
import entity.NPC_Flower;
import entity.NPC_OldMan;
import entity.NPC_SpaceChicken;
import mob.MOB_Rabbit;
import monster.MON_Slime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Dispenser;
import object.OBJ_Door;
import object.OBJ_Floppy;
import object.OBJ_Key;
import object.OBJ_Laser;
import object.OBJ_PC;
import object.OBJ_Vine;

public class AssetSetter {
	
	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void setObject() {
		gamePanel.obj.add(new OBJ_Vine(gamePanel));
		gamePanel.obj.get(0).worldX = 38 * gamePanel.tileSize;
		gamePanel.obj.get(0).worldY = 18 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Vine(gamePanel));
		gamePanel.obj.get(1).worldX = 39 * gamePanel.tileSize;
		gamePanel.obj.get(1).worldY = 18 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Dispenser(gamePanel));
		gamePanel.obj.get(2).worldX = 24 * gamePanel.tileSize;
		gamePanel.obj.get(2).worldY = 35 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Dispenser(gamePanel));
		gamePanel.obj.get(3).worldX = 27 * gamePanel.tileSize;
		gamePanel.obj.get(3).worldY = 35 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Laser(gamePanel));
		gamePanel.obj.get(4).worldX = 26 * gamePanel.tileSize;
		gamePanel.obj.get(4).worldY = 35 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Laser(gamePanel));
		gamePanel.obj.get(5).worldX = 25 * gamePanel.tileSize;
		gamePanel.obj.get(5).worldY = 35 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Floppy(gamePanel));
		gamePanel.obj.get(6).worldX = 28 * gamePanel.tileSize;
		gamePanel.obj.get(6).worldY = 37 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_PC(gamePanel));
		gamePanel.obj.get(7).worldX = 29 * gamePanel.tileSize;
		gamePanel.obj.get(7).worldY = 37 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Key(gamePanel));
		gamePanel.obj.get(8).worldX = 25 * gamePanel.tileSize;
		gamePanel.obj.get(8).worldY = 38 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Key(gamePanel));
		gamePanel.obj.get(9).worldX = 39 * gamePanel.tileSize;
		gamePanel.obj.get(9).worldY = 39 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Key(gamePanel));
		gamePanel.obj.get(10).worldX = 39 * gamePanel.tileSize;
		gamePanel.obj.get(10).worldY = 8 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Door(gamePanel));
		gamePanel.obj.get(11).worldX = 13 * gamePanel.tileSize;
		gamePanel.obj.get(11).worldY = 23 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Door(gamePanel));
		gamePanel.obj.get(12).worldX = 10 * gamePanel.tileSize;
		gamePanel.obj.get(12).worldY = 20 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Door(gamePanel));
		gamePanel.obj.get(13).worldX = 14 * gamePanel.tileSize;
		gamePanel.obj.get(13).worldY = 16 * gamePanel.tileSize;
		
		gamePanel.obj.add(new OBJ_Chest(gamePanel));
		gamePanel.obj.get(14).worldX = 10 * gamePanel.tileSize;
		gamePanel.obj.get(14).worldY = 9 * gamePanel.tileSize;
	}
	
	public void setNPC() {
		
		gamePanel.npc[0] = new NPC_Book(gamePanel);
		gamePanel.npc[0].worldX = gamePanel.tileSize*21;
		gamePanel.npc[0].worldY = gamePanel.tileSize*22;
		
		gamePanel.npc[1] = new NPC_Flower(gamePanel);
		gamePanel.npc[1].worldX = gamePanel.tileSize*37;
		gamePanel.npc[1].worldY = gamePanel.tileSize*19;
		
		gamePanel.npc[2] = new NPC_Computer(gamePanel);
		gamePanel.npc[2].worldX = gamePanel.tileSize*25;
		gamePanel.npc[2].worldY = gamePanel.tileSize*30;
		
		gamePanel.npc[3] = new NPC_SpaceChicken(gamePanel);
		gamePanel.npc[3].worldX = gamePanel.tileSize*10;
		gamePanel.npc[3].worldY = gamePanel.tileSize*40;
	}
	
	public void setMonster() {
		
		gamePanel.mon[0] = new MON_Slime(gamePanel);
		gamePanel.mon[0].worldX = gamePanel.tileSize*36;
		gamePanel.mon[0].worldY = gamePanel.tileSize*36;
		
		gamePanel.mon[1] = new MON_Slime(gamePanel);
		gamePanel.mon[1].worldX = gamePanel.tileSize*40;
		gamePanel.mon[1].worldY = gamePanel.tileSize*33;
		
		gamePanel.mon[2] = new MON_Slime(gamePanel);
		gamePanel.mon[2].worldX = gamePanel.tileSize*40;
		gamePanel.mon[2].worldY = gamePanel.tileSize*40;
		
		gamePanel.mon[3] = new MON_Slime(gamePanel);
		gamePanel.mon[3].worldX = gamePanel.tileSize*37;
		gamePanel.mon[3].worldY = gamePanel.tileSize*39;
	}
	
	public void setMob() {
		gamePanel.mob[0] = new MOB_Rabbit(gamePanel);
		gamePanel.mob[0].worldX = gamePanel.tileSize*20;
		gamePanel.mob[0].worldY = gamePanel.tileSize*21;	
		
		gamePanel.mob[1] = new MOB_Rabbit(gamePanel);
		gamePanel.mob[1].worldX = gamePanel.tileSize*22;
		gamePanel.mob[1].worldY = gamePanel.tileSize*8;
	}
}
