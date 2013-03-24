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
		JPanel panel = new Qcm();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}

}
