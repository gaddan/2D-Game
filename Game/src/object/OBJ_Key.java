package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{
	
	public OBJ_Key(GamePanel gamePanel) {
		super(gamePanel);
		name = "Key";
		down1 = setUp(0, 0, "obj");
		image = setUp(48, 16, "obj");
	}
}
