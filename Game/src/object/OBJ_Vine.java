package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Vine extends Entity{
	
	public OBJ_Vine(GamePanel gamePanel) {
		super(gamePanel);
		name = "Vine";
		down1 = setUp(64, 0, "obj");
		collision = true;
	}
}