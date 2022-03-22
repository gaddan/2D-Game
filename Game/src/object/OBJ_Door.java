package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{
	
	public OBJ_Door(GamePanel gamePanel) {
		super(gamePanel);
		name = "Door";
		down1 = setUp(32, 0, "obj");
		collision = true;
	}
}
