package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import tile.Tile;

public class Player extends Entity{
	
	KeyHandler keyH;
	String lastDirection;
	
	public final int screenX;
	public final int screenY;
	BufferedImage sprite = null;
	int seCounter = 0;
	public boolean tttWon = false;
	public boolean digitComplete = false;
	public int keys = 0;
	
	public Player(GamePanel gamePanel, KeyHandler keyH) {
		
		super(gamePanel);

		this.keyH = keyH;
		
		// first part gets to top left corner, not exact middle, hence -part
		screenX =  gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
		screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		// "attack range"
		attackArea.width = 36;
		attackArea.height = 36;
		
		
		setDefaultValues();
		loadSprite();
		getPlayerImage();
		getPlayerAttackImage();
		
	}

	// spawn
	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 23;
		worldY = gamePanel.tileSize * 21;
		speed = 4;
		direction = "down";
		// status
		maxLife = 6;
		life = maxLife;
	}
	
	public void getPlayerImage() {
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
	
	public void getPlayerAttackImage() {
		attackUp1 = setUp(48, 16, 16, 32, gamePanel.tileSize, gamePanel.tileSize*2);
		attackUp2 = setUp(64, 16, 16, 32, gamePanel.tileSize, gamePanel.tileSize*2);
		attackDown1 = setUp(0, 16, 16, 32, gamePanel.tileSize, gamePanel.tileSize*2);
		attackDown2 = setUp(16, 16, 16, 32, gamePanel.tileSize, gamePanel.tileSize*2);
		attackRight1 = setUp(96, 16, 32, 16, gamePanel.tileSize*2, gamePanel.tileSize);
		attackRight2 = setUp(96, 32, 32, 16, gamePanel.tileSize*2, gamePanel.tileSize);
		attackLeft1 = setUp(128, 16, 32, 16, gamePanel.tileSize*2, gamePanel.tileSize);
		attackLeft2 = setUp(128, 32, 32, 16, gamePanel.tileSize*2, gamePanel.tileSize);
	}
	
	public void loadSprite() {
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/player/player_sprite_sheet.png"));
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
	
	public BufferedImage setUp(int x, int y, int x2, int y2, int scaleX, int scaleY) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		image = sprite.getSubimage(x, y, x2, y2);
		image = uTool.scaledImage(image, scaleX, scaleY);
		return image;
	}
	
	public void update() {
		
		if(keyH.isSpacePressed()) {
			attacking = true;
			if(seCounter == 0) {
				gamePanel.playSE(10);
				seCounter++;
			}
		}
		
		if(attacking) {
			attacking();
		
			// TODO ep 23, enter for dialogue without having to press move buttons
		} else if(keyH.isRightPressed() || keyH.isLeftPressed() || keyH.isDownPressed() || 
					keyH.isUpPressed() || keyH.isEnterPressed() || lastDirection != null) {
				if(keyH.isUpPressed()) {
					direction = "up";
				} else if (keyH.isDownPressed()) {
					direction = "down";
				} else if (keyH.isLeftPressed()) {
					direction = "left";
				} else if (keyH.isRightPressed()) {
					direction = "right";
				} else if(lastDirection == "up") {
					direction = "idleUp";
				} else if(lastDirection == "down") {
					direction = "idleDown";
				} else if(lastDirection == "left") {
					direction = "idleLeft";
				} else if(lastDirection == "right") {
					direction = "idleRight";
				}
				
				// check tile collision
				collisionOn = false;
				gamePanel.cChecker.checkTile(this);
				
				// check object collision
				int objIndex = gamePanel.cChecker.checkObject(this, true);
				pickUpObject(objIndex);
				
				// cbeck npc collision
				int npcIndex = gamePanel.cChecker.checkEntity(this, gamePanel.npc);
				interactNPC(npcIndex);
				
				// check monster collision
				int monsterIndex = gamePanel.cChecker.checkEntity(this, gamePanel.mon);
				touchMonster(monsterIndex);
				
				// check monster collision
				int mobIndex = gamePanel.cChecker.checkEntity(this, gamePanel.mob);
				touchMob(mobIndex);
				
				// check events
				gamePanel.eventH.checkEvent();
				
				// if collision false and enter is not pressed player can move
				if(!collisionOn && !keyH.isEnterPressed()) {
					switch(direction) {
					case "up": worldY -= speed; break;
					case "down": worldY += speed; break;
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
					}
				}
				
				gamePanel.keyH.setEnterPressed(false);
				
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
				
				if(invincible) {
					invincibleCounter++;
					if(invincibleCounter > 45) {
						invincible = false;
						invincibleCounter = 0;
					}
				}			
			}

	}
	
	public void attacking() {
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			// check here in middle of animation if performed attack landed on monster
			// save players current info
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// adjusting players x, y for attackarea
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			// attackarea becomes solidarea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			// check monster collision with updated x, y, solidarea
			int monsterIndex = gamePanel.cChecker.checkEntity(this, gamePanel.mon);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
			// handle damage to monster
			damageMonster(monsterIndex);
		}
		
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
			seCounter = 0;
			keyH.setSpacePressed(false);
		}
		
	}
	
	public void touchMob(int i) {
		if(i != 999) {
			
		}
	}
	
	// how player reacts to objects
	public void pickUpObject(int i) {
		if(i != 999) {
			if(gamePanel.obj.get(i).name == "Key") {
				keys++;
				gamePanel.obj.remove(i);
				gamePanel.playSE(1);
				return;
			}
			if(gamePanel.obj.get(i).name == "Door" && keys > 0) {
				keys--;
				gamePanel.obj.remove(i);
				gamePanel.playSE(8);
				return;
			}
		}
	}
	
	public void interactNPC(int i) {
		if(i != 999) {
			if(keyH.isEnterPressed()) {
				gamePanel.gameState = gamePanel.dialogueState;
				gamePanel.npc[i].speak();		
			}
		}
		keyH.setEnterPressed(false);
	}
	
	public void touchMonster(int i) {
		if(i != 999) {
			if(!invincible && !gamePanel.mon[i].dying) {
				gamePanel.playSE(7);
				life -= 1;
				invincible = true;
			}	
		}
	}
	
	public void damageMonster(int i) {
		if(i != 999) {
			if(!gamePanel.mon[i].invincible) {
				gamePanel.playSE(11);
				gamePanel.mon[i].life -= 1;
				gamePanel.mon[i].invincible = true;
				gamePanel.mon[i].damageReaction();
				if(gamePanel.mon[i].life <= 0) {
					gamePanel.mon[i].dying = true;
				}
				
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		// attack animations up and left need adjustment therefore temps
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		BufferedImage image = null;
		switch (direction) {
		case "up":
			if(!attacking) {
				if(spriteNum == 1) { image = up1; }
				if(spriteNum == 2) { image = up2; }				
			}
			if(attacking) {
				tempScreenY = screenY - gamePanel.tileSize;
				if(spriteNum == 1) { image = attackUp1; }
				if(spriteNum == 2) { image = attackUp2; }					
			}
			break;
		case "down":
			if(!attacking) {
				if(spriteNum == 1) { image = down1; } 
				if(spriteNum == 2) { image = down2; }
			}
			if(attacking) {
				if(spriteNum == 1) { image = attackDown1; }
				if(spriteNum == 2) { image = attackDown2; }					
			}
			break;
		case "left":
			if(!attacking) {
				if(spriteNum == 1) { image = left1; } 
				if(spriteNum == 2) { image = left2; }
			}
			if(attacking) {
				tempScreenX = screenX - gamePanel.tileSize;
				if(spriteNum == 1) { image = attackLeft1; }
				if(spriteNum == 2) { image = attackLeft2; }					
			}
			break;
		case "right":
			if(!attacking) {
				if(spriteNum == 1) { image = right1; } 
				if(spriteNum == 2) { image = right2; }
			}
			if(attacking) {
				if(spriteNum == 1) { image = attackRight1; }
				if(spriteNum == 2) { image = attackRight2; }					
			}
			break;
		case "idleUp":
			if(!attacking) {
				image = idleUp;
			}
			if(attacking) {
				tempScreenY = screenY - gamePanel.tileSize;
				if(spriteNum == 1) { image = attackUp1; }
				if(spriteNum == 2) { image = attackUp2; }					
			}
			break;
		case "idleDown":
			if(!attacking) {
				image = idleDown;
			}
			if(attacking) {
				if(spriteNum == 1) { image = attackDown1; }
				if(spriteNum == 2) { image = attackDown2; }					
			}
			break;
		case "idleLeft":
			if(!attacking) {
				image = left1;
			}
			if(attacking) {
				tempScreenX = screenX - gamePanel.tileSize;
				if(spriteNum == 1) { image = attackLeft1; }
				if(spriteNum == 2) { image = attackLeft2; }					
			}
			break;
		case "idleRight":
			if(!attacking) {
				image = right1;
			}
			if(attacking) {
				if(spriteNum == 1) { image = attackRight1; }
				if(spriteNum == 2) { image = attackRight2; }					
			}
			break;
		}
		
		// the next draw does it with 30% transparency
		if(invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
}
