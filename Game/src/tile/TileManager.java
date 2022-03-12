package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.io.InputStreamReader;
import main.GamePanel;

public class TileManager {

	GamePanel gamePanel;
	public Tile[] tile;
	public int mapTileNum [][];
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		tile = new Tile[10];
		mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		getTileImage();
		loadMap("/maps/world1.txt");
	}
	
	public void getTileImage() {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/tiles_sprite_sheet.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		tile[0] = new Tile();
		tile[0].image = sprite.getSubimage(0,0,16,16); // grass

		tile[1] = new Tile();
		tile[1].image = sprite.getSubimage(32,0,16,16); // wall
		tile[1].collision = true;
		
		tile[2] = new Tile();
		tile[2].image = sprite.getSubimage(48,0,16,16); // water
		tile[2].collision = true;
		
		tile[3] = new Tile();
		tile[3].image = sprite.getSubimage(64,0,16,16); // tree
		tile[3].collision = true;
		
		tile[4] = new Tile();
		tile[4].image = sprite.getSubimage(80,0,16,16); // sand
		
		tile[5] = new Tile();
		tile[5].image = sprite.getSubimage(96,0,16,16); // dirt
		
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
				g2.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
			}
			worldCol++;
			
			if(worldCol == gamePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
}
