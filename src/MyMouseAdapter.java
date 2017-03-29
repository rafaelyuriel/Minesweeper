import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
	public static int redflags = 14;
	public static int grayTiles = 0;
	public static int redTiles = 0;   
	
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}

		JFrame myFrame = (JFrame) c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		myPanel.mouseDownGridX = myPanel.getGridX(x, y);
		myPanel.mouseDownGridY = myPanel.getGridY(x, y);
		myPanel.repaint();

		switch (e.getButton()) {
		case 1: // Left mouse button

		case 3: // Right mouse button

			break;
		default: // Some other button (2 = Middle mouse button, etc.)
			// Do nothing
			break;
		} 
	}
	public void mouseReleased(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame) c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0); 
		Insets myInsets = myFrame.getInsets();

		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);

		switch (e.getButton()) {
		case 1: // Left mouse button

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1) || (myPanel.mouseDownGridX > 8)
					|| (myPanel.mouseDownGridY > 8)) {
				// Had pressed outside
				// Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					// Is releasing outside
					// Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						// Released the mouse button on a different cell where
						// it was pressed
						// Do nothing
					} else {
						// Released the mouse button on the same cell where it
						// was pressed
						if ((gridX > 8) && (gridY > 8)) {
						} else {
							if(myPanel.field[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED)){
								break;
							}else{

								//Check if the tile a mine
								//Paints the tile black if true

								if(myPanel.Mine(myPanel.mouseDownGridX, myPanel.mouseDownGridY)){
									Color newColor = Color.BLACK;
									for(int i = 0; i < 9 ; i++){
										for (int j = 0; j < 9 ; j++){
											if (myPanel.minesOnField[i][j] == true){
												myPanel.field[i][j] = newColor;
												myPanel.repaint();
											}
										}
									}
									//Generates Panel with losing message
									final JOptionPane pane = new JOptionPane("                   Game Over!");
									final JDialog d = pane.createDialog("You Lost!");
									d.setVisible(true);
								
									try {
										ActionListener();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
									break;	
								}
							}
					
							// Paints a non-mine tile gray
							Color newColor = Color.GRAY;
							myPanel.field[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							checkBombs(myPanel, myPanel.mouseDownGridX, myPanel.mouseDownGridY);
							myPanel.repaint();

							grayTiles++;

							//Determines if the player has won the game
							boolean Win = true;
							for (int i = 0; i < 8 ; i++){
								for (int j = 0; j < 8; j++){
									if (myPanel.field[i][j].equals(Color.WHITE)
											&& !myPanel.Mine(i, j)) {
										Win = false;
										break;
									} if(!Win){
										break;
									}														
								}
							}
							
							//Generates a Pane with the Winning message
							if (Win){
								final JOptionPane pane = new JOptionPane("YOU WIN!");
								final JDialog d = pane.createDialog("	CONGRATULATIONS!");
								d.setVisible(true);
								try {
									ActionListener();
								} catch (IOException e1) {
									e1.printStackTrace();
								}		
							}
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3: // Right mouse button

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1) || (myPanel.mouseDownGridX > 8)
					|| (myPanel.mouseDownGridY > 8)) {
				// Had pressed outside
				// Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					// Is releasing outside
					// Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						// Released the mouse button on a different cell where
						// it was pressed
						// Do nothing
					} else {
						// Released the mouse button on the same cell where it
						// was pressed
						if ((gridX > 8) && (gridY > 8)) {
						} else {
							
							//Paints tile red, representing Flags, when the original tile is white
							
							if (myPanel.field[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.WHITE)){
								if (redflags > 0){
									Color newColor = Color.RED;
									redflags--;
									redTiles ++;
									myPanel.field[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
									myPanel.repaint();
								}
							}
							//If the tile is gray, does nothing
							
							else if (myPanel.field[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.GRAY)){
								break;
							}
							//If the tile is red, it paints it white, representing a removal of flags
							
							else if(myPanel.field[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED)){
								redflags ++;
								redTiles--;
								Color newColor = Color.WHITE;
								myPanel.field[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
								myPanel.repaint();
							}
						}
					}
				}
			}
			myPanel.repaint();
			break;
		default: // Some other button (2 = Middle mouse button, etc.)
			// Do nothing
			break;
		}
	}	
	private void ActionListener() throws IOException {
		//System.exit(0);
		Main.masterFrame =  Main.reinitialize();
		Main.masterFrame.setVisible(true);
	}
 
	//Gets the number of flags
	public static String getFlags(){
		if(redflags <= 0){
			return "0";
		}
		return "" + redflags;
	}

	//Check each tile for mines
	public void checkBombs (MyPanel panel, int x, int y){

		int counter = 0;

		if((x - 1 >= 0 && x - 1 <= 8)
				&&  (y >= 0 && y <= 8) 
				&& panel.Mine(x - 1, y)){
			counter ++;
		} 
		if((x - 1 >= 0 && x - 1 < 9)
				&&  (y - 1 >= 0 && y - 1 <= 8) 
				&& panel.Mine(x - 1, y - 1 )){
			counter ++;
		} 

		if((x  >= 0 && x <= 8)
				&&  (y - 1 >= 0 && y  -1 <= 8) 
				&& panel.Mine(x, y - 1)){
			counter ++;
		} 
		if((x + 1 >= 0 && x + 1 <= 8)
				&&  (y >= 0 && y <= 8) 
				&& panel.Mine(x + 1, y)){
			counter ++;
		} 
		if((x + 1 >= 0 && x + 1 <= 8)
				&&  (y + 1>= 0 && y + 1 <= 8) 
				&& panel.Mine(x + 1, y + 1)){
			counter ++;
		} 
		if((x >= 0 && x <=8 )
				&&  (y + 1>= 0 && y + 1<= 8) 
				&& panel.Mine(x, y + 1)){
			counter ++;
		} 
		if((x -1 >= 0 && x -1 <= 8)
				&&  (y + 1 >= 0 && y + 1 <= 8) 
				&& panel.Mine(x -1, y + 1)){
			counter ++;
		} 
		if((x + 1 >= 0 && x + 1 <= 8)
				&&  (y - 1 >= 0 && y - 1 <= 8) 
				&& panel.Mine(x + 1, y - 1)){
			counter ++;
		} 
		//Set The Number on Tile
		if (counter > 0) {

			Color newColor = Color.LIGHT_GRAY;
			panel.field[x][y] = newColor;	
			panel.numInGrid[x][y] =  counter + "";

		} else {
			//If Not Mine Found
			if((x - 1 >= 0 && x - 1 <= 8)
					&&  (y >= 0 && y <= 8) 
					&& !panel.field[x - 1][y].equals(Color.GRAY) 
					&& !panel.field[x - 1][y].equals(Color.RED) 
					&& !panel.Mine(x - 1, y)){
				Color newColor =  Color.GRAY;
				panel.field[x - 1][y] = newColor;
				checkBombs(panel, x - 1, y);	
			} 
			if((x - 1 >= 0 && x - 1 <= 8)
					&&  (y -1 >= 0 && y -1 <=8 ) 
					&& !panel.field[x - 1][y - 1].equals(Color.GRAY) 
					&& !panel.field[x - 1][y - 1].equals(Color.RED) 
					&& !panel.Mine(x - 1, y - 1)){
				Color newColor =  Color.GRAY;
				panel.field[x - 1][y - 1] = newColor;
				checkBombs(panel, x - 1, y -1);
			} 
			if((x - 1 >= 0 && x - 1 <= 8)
					&&  (y + 1 >= 0 && y + 1 <= 8) 
					&& !panel.field[x - 1][y + 1].equals(Color.GRAY) 
					&& !panel.field[x - 1][y + 1].equals(Color.RED) 
					&& !panel.Mine(x - 1, y + 1)){
				Color newColor =  Color.GRAY;
				panel.field[x - 1][y + 1] = newColor;
				checkBombs(panel, x - 1, y + 1);
			} 
			if((x >= 0 && x <= 8)
					&&  (y - 1 >= 0 && y - 1 <= 8) 
					&& !panel.field[x][y - 1].equals(Color.GRAY) 
					&& !panel.field[x][y - 1].equals(Color.RED) 
					&& !panel.Mine(x, y - 1)){
				Color newColor =  Color.GRAY;
				panel.field[x][y - 1] = newColor;
				checkBombs(panel, x, y - 1);
			} 
			if((x >= 0 && x < 9)
					&&  (y + 1 >= 0 && y + 1 <= 8) 
					&& !panel.field[x][y + 1].equals(Color.GRAY) 
					&& !panel.field[x][y + 1].equals(Color.RED) 
					&& !panel.Mine(x, y + 1)){
				Color newColor =  Color.GRAY;
				panel.field[x][y + 1] = newColor;
				checkBombs(panel, x, y + 1);
			} 
			if((x + 1 >= 0 && x + 1 <= 8)
					&&  (y >= 0 && y <= 8) 
					&& !panel.field[x + 1][y].equals(Color.GRAY) 
					&& !panel.field[x + 1][y].equals(Color.RED) 
					&& !panel.Mine(x + 1, y)){
				Color newColor =  Color.GRAY;
				panel.field[x + 1][y] = newColor;
				checkBombs(panel, x + 1, y);
			} 
			if((x + 1 >= 0 && x + 1 <= 8)
					&&  (y - 1>= 0 && y -1 <= 8) 
					&& !panel.field[x + 1][y - 1].equals(Color.GRAY) 
					&& !panel.field[x + 1][y - 1].equals(Color.RED) 
					&& !panel.Mine(x + 1, y - 1)){
				Color newColor =  Color.GRAY;
				panel.field[x + 1][y - 1] = newColor;
				checkBombs(panel, x + 1, y - 1);
			} 
			if((x + 1 >= 0 && x + 1 <= 8)
					&&  (y + 1 >= 0 && y + 1 <= 8) 
					&& !panel.field[x + 1][y + 1].equals(Color.GRAY) 
					&& !panel.field[x + 1][y + 1].equals(Color.RED) 
					&& !panel.Mine(x + 1, y + 1)){
				Color newColor =  Color.GRAY;
				panel.field[x + 1][y + 1] = newColor;
				checkBombs(panel, x + 1, y + 1);
			} 
		}
	}
}