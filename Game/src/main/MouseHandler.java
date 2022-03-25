package main;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
	GamePanel gamePanel;
	private Rectangle selectedCell = null;
	
	
	public MouseHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(gamePanel.gameState == gamePanel.minigameState) {
	        for(Rectangle index : gamePanel.ui.ttt.keySet()) {
	        	// player attempts
	        	if(index.contains(e.getPoint()) && !gamePanel.ui.ttt.get(index).equals("player") 
	        			&& !gamePanel.ui.ttt.get(index).equals("flower") && !gamePanel.ui.playerMoved) {
	        		gamePanel.ui.ttt.put(index, "player");
	        		gamePanel.ui.movesPlayer.add(index);
	        		gamePanel.ui.playerMoved = true;
	        		
	    	        if(gamePanel.ui.playerMoves > 2) {
	    	        	gamePanel.ui.ttt.put(gamePanel.ui.movesPlayer.removeFirst(), "");
	    	        }
	    	        gamePanel.ui.playerMoves++;
	        	}
	        	// simple ai, just takes the next available spot in matrix
	        	if(!gamePanel.ui.ttt.get(index).equals("player") 
	        			&& !gamePanel.ui.ttt.get(index).equals("flower") && gamePanel.ui.playerMoved) {
	        		gamePanel.ui.ttt.put(index, "flower");
	        		gamePanel.ui.playerMoved = false;
	        		gamePanel.ui.movesFlower.add(index);
	        		
	    	        if(gamePanel.ui.flowerMoves > 2) {
	    	        	gamePanel.ui.ttt.put(gamePanel.ui.movesFlower.removeFirst(), "");
	    	        }
	    	        gamePanel.ui.flowerMoves++;
	        	}
	        }
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
