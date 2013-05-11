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
		File image = new File("qcms/qcmcolor.jpg");
		JFrame frame = new JFrame("\tInitial Image");
		JPanel panel = new Qcm(image);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.setLocation(50, 50);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		File image2 = new File("qcms/qcmcolor.jpg");
		JFrame frame2 = new JFrame("\t2nd Image");
		JPanel panel2 = new Qcm2(image2);
		frame2.getContentPane().add(panel2, BorderLayout.NORTH);
		frame2.pack();
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel3 = new CompareImages(image, image2);
		JFrame frame3 = new JFrame("\tResults");
		frame3.getContentPane().add(panel3, BorderLayout.NORTH);
		frame3.pack();
		frame.setVisible(true);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
