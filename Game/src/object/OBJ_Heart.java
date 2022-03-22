package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject{
	
	GamePanel gamePanel;
	
	public OBJ_Heart(GamePanel gamePanel) {
		name = "Heart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png")).getSubimage(0, 16, 16, 16);
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png")).getSubimage(16, 16, 16, 16);
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png")).getSubimage(32, 16, 16, 16);
			image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
			image2 = uTool.scaledImage(image2, gamePanel.tileSize, gamePanel.tileSize);
			image3 = uTool.scaledImage(image3, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
