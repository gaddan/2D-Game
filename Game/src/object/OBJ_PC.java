package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_PC extends Entity{
	
	public OBJ_PC(GamePanel gamePanel) {
		super(gamePanel);
		name = "PC";
		down1 = setUp(80, 0, "obj");
		collision = true;
	}
}