import java.awt.BorderLayout;
import java.io.File;

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
		File image = new File("qcms/QcmImage.jpg");
		JFrame frame = new JFrame();
		JPanel panel = new Qcm(image);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		frame.setLocation(300, 300);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	
		/*
		JFrame frame = new MyFrame();
		frame.setVisible(true);
		*/
	}

}
