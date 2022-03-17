package entity;

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
	
	GamePanel gamePanel;
	KeyHandler keyH;
	String lastDirection;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	BufferedImage sprite = null;
	
	public Player(GamePanel gamePanel, KeyHandler keyH) {
		
		this.gamePanel = gamePanel;
		this.keyH = keyH;
		
		// first part gets to top left corner, not exact middle, hence -part
		screenX =  gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
		screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
		
	}

	// spawn
	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 23;
		worldY = gamePanel.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
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
	
	public void update() {
				
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
			
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
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
	
	// how player reacts to objects
	public void pickUpObject(int i) {
		if(i != 999) {
			String objectName = gamePanel.obj[i].name;
			
			switch (objectName) {
			case "Key":
				gamePanel.playSE(1);
				hasKey++;
				gamePanel.obj[i] = null;
				gamePanel.ui.showMessage("Picked up a key!");
				break;
			case "Door":
				if(hasKey > 0) {
					gamePanel.playSE(8);
					gamePanel.obj[i] = null;
					hasKey--;
					gamePanel.ui.showMessage("Unlocked a door!");
				} else {
					gamePanel.ui.showMessage("Key required!");
				}
				break;
			case "Boots":
				gamePanel.playSE(6);
				speed += 2;
				gamePanel.obj[i] = null;
				gamePanel.ui.showMessage("Speed it is!");
				break;
			case "Chest":
				gamePanel.ui.gameFinished = true;
				gamePanel.stopMusic();
				gamePanel.playSE(3);
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
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
		g2.drawImage(image, screenX, screenY, null);
	}
	
}
