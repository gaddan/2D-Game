package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import java.io.InputStreamReader;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gamePanel;
	public Tile[] tile;
	public int mapTileNum [][];
	BufferedImage sprite = null;
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		tile = new Tile[50];
		mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		getTileImage();
		loadMap("/maps/world1.txt");
	}
	
	public void getTileImage() {
		loadSprite();
		setUp(0, 48, 16, false); // grass
		setUp(1, 32, 0, true); // water windy
		setUp(2, 48, 0, true); // water
		setUp(3, 64, 0, true); // tree
		setUp(4, 80, 0, false); // sand
		setUp(5, 96, 0, false); // dirt
		
		// grasses
		setUp(6, 0, 32, true); // up
		setUp(7, 16, 32, true); // down
		setUp(8, 32, 32, true); // right
		setUp(9, 48, 32, true); // left
		setUp(10, 64, 32, true); // left corner
		setUp(11, 80, 32, true); // L bottom corner
		setUp(12, 96, 32, true); // right corner
		setUp(13, 112, 32, true); // R bottom corner
		setUp(14, 128, 32, true); // top L edge
		setUp(15, 144, 32, true); // top R edge
		setUp(16, 128, 16, true); // bottom R edge
		setUp(17, 144, 16, true); // bottom L edge
		//setUp(18, 48, 16, false); // improved grass
	}
	
	public void loadSprite() {
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/tiles_sprite_sheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setUp(int index, int x, int y, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		tile[index] = new Tile();
		tile[index].image = sprite.getSubimage(x, y, 16, 16);
		tile[index].image = uTool.scaledImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
		tile[index].collision = collision;
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is =  getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
				String line = br.readLine();
				while(col < gamePanel.maxWorldCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gamePanel.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gamePanel.tileSize;
			int worldY = worldRow * gamePanel.tileSize;
			int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
			int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
			
			// if statement is boundary of currently visible screen of player
			// for more efficient rendering
			if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
					worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
					worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
					worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			worldCol++;
			
			if(worldCol == gamePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
}
