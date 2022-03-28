package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Floppy extends Entity{
	
	public OBJ_Floppy(GamePanel gamePanel) {
		super(gamePanel);
		name = "Floppy";
		down1 = setUp(96, 0, "obj");
		collision = true;
	}
}