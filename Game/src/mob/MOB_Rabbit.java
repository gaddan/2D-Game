package mob;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class MOB_Rabbit extends Entity {
	
	public MOB_Rabbit(GamePanel gamePanel) {
		super(gamePanel);
		type = 3;
		name = "Rabbit";
		speed = 2;
		maxLife = 4;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getMobImage();
	}
	
	public void getMobImage() {
		idleDown = setUp(48, 0, "mob");
		down1 = setUp(48, 0, "mob");
		down2 = setUp(32, 0, "mob");
		idleUp = setUp(16, 0, "mob");
		up1 = setUp(16, 0, "mob");
		up2 = setUp(0, 0, "mob");
		right1 = setUp(80, 0, "mob");
		right2 = setUp(64, 0, "mob");
		left1 = setUp(96, 0, "mob");
		left2 = setUp(112, 0, "mob");
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
}
