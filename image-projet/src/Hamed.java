import java.awt.BorderLayout;

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

		JFrame frame = new JFrame();
		//JMenuBar menuBar = new MyMenuBar();
		JPanel panel = new Qcm();
		//frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		frame.setLocation(300, 300);
		//frame.setSize(600, 200);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}

}
