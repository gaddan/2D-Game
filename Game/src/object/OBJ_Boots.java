package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boots extends SuperObject{
	
	GamePanel gamePanel;
	
	public OBJ_Boots(GamePanel gamePanel) {
		name = "Boots";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png")).getSubimage(48, 0, 16, 16);
			uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}
}
