import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Hamed {

	public Hamed() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File image = new File("qcms/qcmcolor4.jpg");
		JFrame frame = new JFrame();
		JPanel panel = new Qcm(image);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.setLocation(250, 300);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		/*
		JFrame frame = new MyFrame();
		frame.setVisible(true);
		*/
	}

}
