import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hamed {

	public Hamed() {}

	public static void main(String[] args) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		
		File image = new File("qcms/qcmcolor77.jpg");
		File image2 = new File("qcms/qcmcolor7.jpg");
		
		JPanel panel3 = new CompareImages(image, image2);
		JFrame frame3 = new JFrame("\tResults");
		frame3.getContentPane().add(panel3, BorderLayout.NORTH);
		frame3.pack();
		frame3.setLocation((int)screenWidth-250, 0);
		frame3.setVisible(true);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		JPanel panel4 = new MyJPanel(image, 600, 240, true);
		JFrame frame4 = new JFrame("Image1: " + image.getName());
		frame4.getContentPane().add(panel4, BorderLayout.NORTH);
		frame4.pack();
		frame4.setVisible(true);
		frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel5 = new MyJPanel(image2, 600, 240, true);
		JFrame frame5 = new JFrame("Image2: " + image2.getName());
		frame5.getContentPane().add(panel5, BorderLayout.NORTH);
		frame5.pack();
		frame5.setLocation((int)screenWidth/2, 0);
		frame5.setVisible(true);
		frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
		File image3 = new File("qcms/qcmcolor51.jpg");
		JPanel panel1 = new MyJPanel(image3, true, 550, 240);
		JFrame frame1 = new JFrame("Histogrammes: " + image3.getName());
		frame1.getContentPane().add(panel1, BorderLayout.NORTH);
		frame1.pack();
		Dimension frame1Size = frame1.size();
		int frame1Height = frame1Size.height;
		frame1.setLocation((int)screenWidth/2, (int)(screenHeight-frame1Height));
		frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
