package monster;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class MON_Slime extends Entity {
	
	BufferedImage sprite = null;
	
	GamePanel gamePanel;
	
	public MON_Slime(GamePanel gamePanel) {
		super(gamePanel);
		this.gamePanel = gamePanel;
		type = 2;
		name = "Slime";
		speed = 1;
		maxLife = 2;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getMonsterImage();
	}
	
	public void getMonsterImage() {
		idleDown = setUp(0, 0, "mon");
		down1 = setUp(0, 0, "mon");
		down2 = setUp(16, 0, "mon");
		idleUp = setUp(0, 0, "mon");
		up1 = setUp(0, 0, "mon");
		up2 = setUp(16, 0, "mon");
		right1 = setUp(0, 0, "mon");
		right2 = setUp(16, 0, "mon");
		left1 = setUp(0, 0, "mon");
		left2 = setUp(16, 0, "mon");
		extra = setUp(32, 0, "mon");
	}
	
	public void setAction() { // super simple AI for slime
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
	
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gamePanel.player.direction;
	}
}
