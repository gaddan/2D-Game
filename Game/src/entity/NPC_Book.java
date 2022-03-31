package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_Book extends Entity{
	
	BufferedImage sprite = null;
	BufferedImage dialogue = null;

	public NPC_Book(GamePanel gamePanel) {
		super(gamePanel);
		name = "Book";
		
		direction = "down";
		speed = 0;
		
		getNPCImage();
		setDialogue();
	}
	
	public void getNPCImage() {
		loadSprite();
		down1 = setUp(128, 16);
		down2 = setUp(144, 16);
		
		dialogueFace = setUpDialogue(96, 0);
	}
	
	public void loadSprite() {
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/npc/npc_sprite_sheet.png"));
			dialogue = ImageIO.read(getClass().getResourceAsStream("/npc/npc_dialogue_sprite_sheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage setUp(int x, int y) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		image = sprite.getSubimage(x, y, 16, 16);
		image = uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
		return image;
	}
	
	public BufferedImage setUpDialogue(int x, int y) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		image = dialogue.getSubimage(x, y, 32, 32);
		image = uTool.scaledImage(image, gamePanel.tileSize*2, gamePanel.tileSize*2);
		return image;
	}
	
	public void setDialogue() {
		dialogues[0] = "Hi there, adventurer!";
		dialogues[1] = "Yes, I am a flying book..";
		dialogues[2] = "A flying guide book!!";
		dialogues[3] = "I've heard there's a\ntreasure west of here";
	}
	
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
	}
}
