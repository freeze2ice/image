package tp6;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hamed {

	public Hamed() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame jf = new JFrame();
		JPanel panel = new CorrectionImage();
		jf.getContentPane().add(panel);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);

	}

}
