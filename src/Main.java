import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.IOException;
import java.awt.Color;

public class Main {
	static JLabel lblTime;
	private static JLabel lblFlags;   
	public static JFrame masterFrame;

	public static void main(String[] args) throws IOException {
		masterFrame = initialize();
		masterFrame.setVisible(true);
	}
	public static JFrame reinitialize() throws IOException {
		MyMouseAdapter.redflags = 14;
		masterFrame.dispose();
		return initialize();
	}
	
//Initialize the frame
	private static JFrame initialize() throws IOException{

		JFrame myFrame = new JFrame("Minesweeper");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(330, 330);

		MyPanel myPanel = new MyPanel();
		myFrame.getContentPane().add(myPanel);
		myPanel.setLayout(null);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		MyMouseAdapter.getFlags();
		myFrame.addMouseListener(myMouseAdapter);

		lblFlags = new JLabel();		
		lblFlags.setBackground(Color.LIGHT_GRAY);
		lblFlags.setForeground(new Color(204, 255, 0));
		lblFlags.setBounds(339, 162, 110, 21);
		lblFlags.setFont(new Font("Goudy Stout", Font.PLAIN, 12));
		lblFlags.setHorizontalAlignment(SwingConstants.CENTER);
		myPanel.add(lblFlags);	
		return myFrame;
	}
}