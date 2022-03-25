package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_Computer extends Entity{
	
	BufferedImage sprite = null;
	BufferedImage dialogue = null;

	public NPC_Computer(GamePanel gamePanel) {
		super(gamePanel);
		name = "Computer";
		
		direction = "down";
		speed = 0;
		idleTimer = 24;
		
		getNPCImage();
		setDialogue();
	}
	
	public void getNPCImage() {
		loadSprite();
		down1 = setUp(32, 16);
		down2 = setUp(48, 16);
		dialogueFace = setUpDialogue(32, 0);
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
		dialogues[0] = "Beep...Boop...";
		dialogues[1] = "Help a boop... robot\nout will you? beeep..";
		dialogues[2] = "010101 011010 011000\n011011 011010 011100";
		dialogues[3] = "I have lost a 6-digit\ncode somewhere.. :(";
	}
	
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
	}
}
