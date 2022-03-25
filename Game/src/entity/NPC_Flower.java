package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_Flower extends Entity{
	
	BufferedImage sprite = null;
	BufferedImage dialogue = null;

	public NPC_Flower(GamePanel gamePanel) {
		super(gamePanel);
		name = "Flower";
		
		direction = "down";
		speed = 0;
		
		getNPCImage();
		setDialogue();
	}
	
	public void getNPCImage() {
		loadSprite();
		down1 = setUp(0, 16);
		down2 = setUp(16, 16);
		
		dialogueFace = setUpDialogue(0, 0);
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
		dialogues[0] = "Hello there!";
		dialogues[1] = "You wanna pass hmm?";
		dialogues[2] = "I'll let you pass,\nif you win!!";
	}
	
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			if(!gamePanel.player.tttWon) {
				gamePanel.gameState = gamePanel.minigameState;
			}
			dialogueIndex = 0;
		}
		if(!gamePanel.player.tttWon) {
			gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
			dialogueIndex++;
		} else {
			gamePanel.gameState = gamePanel.dialogueState;
			gamePanel.ui.currentDialogue = "Ourgh, you won..";
		}
	}
}
