package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
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
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idleDown, idleUp, extra;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public String direction = "down";
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	private String lastDirection;
	public int actionLockCounter;
	String dialogues[] = new String[20];
	public int dialogueIndex = 0;
	public BufferedImage image, image2, image3;
	public BufferedImage dialogueFace;
	public String name;
	public boolean collision = false;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int type; // 0 = player, 1 = npc, 2 = monster, 3 = mob
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	int dyingCounter = 0;
	public boolean hpBarOn = false;
	public int hpBarCounter = 0;
	public int idleTimer = 12;
	
	// sprites
	BufferedImage npcSprite = null;
	BufferedImage objSprite = null;
	BufferedImage monSprite = null;
	BufferedImage mobSprite = null;
	
	// character status
	public int maxLife;
	public int life;
	
	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		loadSprite();
	}
	
	public void setAction() {}// unique for entities
	
	public void damageReaction() {}
	
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
		boolean touchPlayer = gamePanel.cChecker.checkPlayer(this);
		gamePanel.cChecker.checkEntity(this, gamePanel.mon);
		gamePanel.cChecker.checkEntity(this, gamePanel.npc);
		gamePanel.cChecker.checkEntity(this, gamePanel.mob);
		
		if(this.type == 2 && touchPlayer) {
			if(!gamePanel.player.invincible) {
				gamePanel.player.life -= 1;
				gamePanel.player.invincible = true;
			}
		}
		
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
		
		if(spriteCounter > idleTimer) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}else if(spriteNum == 2) {
				if(name == "Slime") {
					spriteNum = 3;
				} else {
					spriteNum = 1;
				}
			}else if(spriteNum == 3) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		// some invincible time for all entities
		if(invincible) {
			invincibleCounter++;
			if(invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}	
	}
	
	public void draw(Graphics2D g2) {
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		BufferedImage image = null;
		
		if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
				worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
				worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
				worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
			switch (direction) {
			case "up":
				if(spriteNum == 1) { image = up1; } 
				if(spriteNum == 2) { image = up2; }
				if(spriteNum == 3) { image = extra; }
				break;
			case "down":
				if(spriteNum == 1) { image = down1; } 
				if(spriteNum == 2) { image = down2;	}
				if(spriteNum == 3) { image = extra; }
				break;
			case "left":
				if(spriteNum == 1) { image = left1; }
				if(spriteNum == 2) { image = left2;	}
				if(spriteNum == 3) { image = extra; }
				break;
			case "right":
				if(spriteNum == 1) { image = right1; } 
				if(spriteNum == 2) { image = right2; }
				if(spriteNum == 3) { image = extra; }
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
			
			// monster health bar
			if(type == 2 && hpBarOn) {
				double oneScale = (double)gamePanel.tileSize / maxLife;
				double hpBarValue = oneScale*life;
				g2.setColor(new Color(255, 0, 30));
				changeAlpha(g2, 0.4f);
				g2.fillRect(screenX, screenY - 15, gamePanel.tileSize, 10);
				changeAlpha(g2, 1f);
				
				
				g2.setColor(new Color(255, 0, 30));
				g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
				
				hpBarCounter++;
				if(hpBarCounter > 300) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			
			// the next draw does it with 30% transparency
			if(invincible) {
				hpBarOn = true;
				hpBarCounter = 0; // continue displaying hp bar
				changeAlpha(g2, 0.4f);
			}
			if(dying) {
				dyingAnimationBlink(g2);
			}
			
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
			
			changeAlpha(g2, 1f);
		}
	}
	
	// blinking effect when monster dies
	public void dyingAnimationBlink(Graphics2D g2) {
		dyingCounter++;
		int i = 5;
		if(dyingCounter < i) { changeAlpha(g2, 0f); }
		if(dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 1f); }
		if(dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2, 0f); }
		if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f); }
		if(dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 0f); }
		if(dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 1f); }
		if(dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 0f); }
		if(dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 1f); }
		if(dyingCounter > i*8) {
			dying = false;
			alive = false;			
		}

	}
	
	public void changeAlpha(Graphics2D g2, float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	}
	
	public BufferedImage setUp(int x, int y, String selector) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		switch (selector) {
		case "npc": {
			image = npcSprite.getSubimage(x, y, 16, 16);
			image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
			break;
		}
		case "obj":{
			image = objSprite.getSubimage(x, y, 16, 16);
			image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
			break;
		}
		case "mon":{
			image = monSprite.getSubimage(x, y, 16, 16);
			image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
			break;
		}
		case "mob":{
			image = mobSprite.getSubimage(x, y, 16, 16);
			image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
			break;
		}
		}
		return image;
	}
	
	public void loadSprite() {
		try {
			npcSprite = ImageIO.read(getClass().getResourceAsStream("/npc/npc_sprite_sheet.png"));
			objSprite = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png"));
			monSprite = ImageIO.read(getClass().getResourceAsStream("/monster/monster_sprite_sheet.png"));
			mobSprite = ImageIO.read(getClass().getResourceAsStream("/mob/mob_sprite_sheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateNPCImage() {
		// TODO Auto-generated method stub
		
	}
}
