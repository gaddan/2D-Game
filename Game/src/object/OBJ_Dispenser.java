package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Dispenser extends Entity{
	
	public OBJ_Dispenser(GamePanel gamePanel) {
		super(gamePanel);
		name = "Dispenser";
		down1 = setUp(128, 0, "obj");
		collision = true;
	}
}