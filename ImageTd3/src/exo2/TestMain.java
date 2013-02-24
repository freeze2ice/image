package exo2;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestMain {

	public TestMain() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame jf = new JFrame();
		JPanel panel = new Histogramme();
		jf.getContentPane().add(panel);
		jf.pack();
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
	}

}
