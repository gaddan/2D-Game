package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_OldMan extends Entity{
	
	BufferedImage sprite = null;

	public NPC_OldMan(GamePanel gamePanel) {
		super(gamePanel);
		
		direction = "down";
		speed = 1;
		
		getNPCImage();
		setDialogue();
	}
	
	public void getNPCImage() {
		loadSprite();
		idleDown = setUp(0, 0);
		down1 = setUp(16, 0);
		down2 = setUp(32, 0);
		idleUp = setUp(48, 0);
		up1 = setUp(64, 0);
		up2 = setUp(80, 0);
		right1 = setUp(96, 0);
		right2 = setUp(112, 0);
		left1 = setUp(128, 0);
		left2 = setUp(144, 0);
	}
	
	public void loadSprite() {
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/npc/npc_sprite_sheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage setUp(int x, int y) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		image = sprite.getSubimage(x, y, 16, 16);
		image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
		return image;
	}
	
	public void setDialogue() {
		dialogues[0] = "Good day!";
		dialogues[1] = "What a nice day it is today. \nDon't you think?";
		dialogues[2] = "Well, good luck to you!";
	}
	
	public void setAction() { // super simple AI for npc
		actionLockCounter++;
		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1; // random num 1-100
			
			if(i <= 25) {
				direction = "up";
			} else if (25 < i && i <= 50) {
				direction = "down";
			} else if (50 < i && i <= 75) {
				direction = "left";
			} else if (75 < i && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}
	
	public void speak() {
		super.speak(); // since same for every npc, can be called from Entity
	}
}
