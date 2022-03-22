package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{
	
	public OBJ_Heart(GamePanel gamePanel) {
		super(gamePanel);
		name = "Heart";
		image = setUp(0, 16, "obj");
		image2 = setUp(16, 16, "obj");
		image3 = setUp(32, 16, "obj");
	}
}
