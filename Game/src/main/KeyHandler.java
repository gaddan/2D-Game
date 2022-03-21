package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	// debugging
	public boolean checkDrawTime;
	
	GamePanel gamePanel;
	
	public KeyHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// not used
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(gamePanel.gameState == gamePanel.titleState) {
			if(code == KeyEvent.VK_UP) {
				if(gamePanel.ui.commandNum > 0) {
					gamePanel.playSE(9);
					gamePanel.ui.commandNum--;
				}
			} else if (code == KeyEvent.VK_DOWN) {
				if(gamePanel.ui.commandNum < 2) {
					gamePanel.playSE(9);
					gamePanel.ui.commandNum++;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gamePanel.ui.commandNum == 0) { // new game
					gamePanel.gameState = gamePanel.playState;
					gamePanel.playMusic(0);
				} else if(gamePanel.ui.commandNum == 1) { // load game
					// TODO
				} else if(gamePanel.ui.commandNum == 2) { // quit
					System.exit(0);
				}
			}
		}
		
		// play state
		if(gamePanel.gameState == gamePanel.gameState) {
			if(code == KeyEvent.VK_W) {
				setUpPressed(true);
			} else if (code == KeyEvent.VK_S) {
				setDownPressed(true);
			} else if (code == KeyEvent.VK_A) {
				setLeftPressed(true);
			} else if (code == KeyEvent.VK_D) {
				setRightPressed(true);
			} else if (code == KeyEvent.VK_ESCAPE) {
				gamePanel.gameState = gamePanel.pauseState;
			} else if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			} else if (code == KeyEvent.VK_T) {
				if(checkDrawTime == false) {
					checkDrawTime = true;
				} else {
					checkDrawTime = false;
				}
			}			
		}
		
		// pause state
		if(gamePanel.gameState == gamePanel.pauseState) {
			if (code == KeyEvent.VK_ESCAPE) {
				gamePanel.gameState = gamePanel.playState;
			}
		}
		
		// dialogue state
		if(gamePanel.gameState == gamePanel.dialogueState) {
			if (code == KeyEvent.VK_ENTER) {
				gamePanel.gameState = gamePanel.playState;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			setUpPressed(false);
		} else if (code == KeyEvent.VK_S) {
			setDownPressed(false);
		} else if (code == KeyEvent.VK_A) {
			setLeftPressed(false);
		} else if (code == KeyEvent.VK_D) {
			setRightPressed(false);
		}
		
	}

	public boolean isUpPressed() {
		return upPressed;
	}

	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}
	

}
