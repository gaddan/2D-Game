package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	GamePanel gamePanel;
	public int worldX, worldY;
	public int speed;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idleDown, idleUp;
	public String direction = "down";
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	private String lastDirection;
	public int actionLockCounter;
	String dialogues[] = new String[20];
	public int dialogueIndex = 0;
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	
	// sprites
	BufferedImage npcSprite = null;
	BufferedImage objSprite = null;
	
	// character status
	public int maxLife;
	public int life;
	
	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		loadSprite();
	}
	
	public void setAction() { // unique for entities
		
	}
	
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		// face player when talking
		switch(gamePanel.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	
	public void update() { // same for all entities
		setAction();
		
		collisionOn = false;
		gamePanel.cChecker.checkTile(this);
		gamePanel.cChecker.checkObject(this, false);
		gamePanel.cChecker.checkPlayer(this);
		
		if(collisionOn == false) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		
		lastDirection = direction;
		spriteCounter++;
		
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			} else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		BufferedImage image = null;
		
		if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
				worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
				worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
				worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
			switch (direction) {
			case "up":
				if(spriteNum == 1) {
					image = up1;
				} else if (spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image = down1;
				} else if (spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1) {
					image = left1;
				} else if (spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image = right1;
				} else if (spriteNum == 2) {
					image = right2;
				}
				break;
			case "idleUp":
				image = idleUp;
				break;
			case "idleDown":
				image = idleDown;
				break;
			case "idleLeft":
				image = left1;
				break;
			case "idleRight":
				image = right1;
				break;
			}
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
		}
	}
	
	public BufferedImage setUp(int x, int y, String selector) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		switch (selector) {
		case "npc": {
			image = npcSprite.getSubimage(x, y, 16, 16);
			image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
		}
		case "obj":{
			image = objSprite.getSubimage(x, y, 16, 16);
			image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
		}
		}
		return image;
	}
	
	public void loadSprite() {
		try {
			npcSprite = ImageIO.read(getClass().getResourceAsStream("/npc/npc_sprite_sheet.png"));
			objSprite = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
