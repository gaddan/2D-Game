package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Laser extends Entity{
	
	public OBJ_Laser(GamePanel gamePanel) {
		super(gamePanel);
		name = "Laser";
		down1 = setUp(112, 0, "obj");
		collision = true;
	}
}