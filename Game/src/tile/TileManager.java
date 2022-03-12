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
	Tile[] tile;
	int mapTileNum [][];
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		tile = new Tile[10];
		mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
		getTileImage();
		loadMap("/maps/map1.txt");
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
		
		tile[2] = new Tile();
		tile[2].image = sprite.getSubimage(48,0,16,16); // water	
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is =  getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
				String line = br.readLine();
				while(col < gamePanel.maxScreenCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gamePanel.maxScreenCol) {
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
		
		int col = 0;
		int row = 0;
		int y = 0;
		int x = 0;
		
		while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			g2.drawImage(tile[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
			col++;
			x += gamePanel.tileSize;
			
			if(col == gamePanel.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gamePanel.tileSize;
			}
		}
		
	}
}
