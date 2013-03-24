package answerDetection;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
		
		
		/**
		 * initial qcm
		 */
		BufferedImage initialQCM = null;
		
		try {
			initialQCM = ImageIO.read(new File("qcm.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel binarize = new BinarizeImage(initialQCM, 215);
		JPanel histoVerti = new HistoVerti(((BinarizeImage) binarize).getBinaryImage());
		JPanel histoHoriz = new HistoHoriz(((BinarizeImage) binarize).getBinaryImage());
		
		JFrame frame = new JFrame("initial qcm");
		//frame.getContentPane().add(binarize, BorderLayout.WEST);
		frame.getContentPane().add(histoHoriz, BorderLayout.CENTER);
		frame.getContentPane().add(histoVerti, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);	
		
		/**
		 * qcm Answer sheet
		 */
		BufferedImage answersQCM = null;
		
		try {
			answersQCM = ImageIO.read(new File("answers-qcm.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel binarize2 = new BinarizeImage(answersQCM, 215);
		JPanel histoVerti2 = new HistoVerti(((BinarizeImage) binarize2).getBinaryImage());
		JPanel histoHoriz2 = new HistoHoriz(((BinarizeImage) binarize2).getBinaryImage());
		
		JFrame frame2 = new JFrame("answersQCM");
		//frame.getContentPane().add(binarize, BorderLayout.WEST);
		frame2.getContentPane().add(histoHoriz2, BorderLayout.CENTER);
		frame2.getContentPane().add(histoVerti2, BorderLayout.EAST);
		frame2.pack();
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);	

		/**
		 * user qcm anwsere sheet
		 */
		BufferedImage userQCM = null;
		
		try {
			userQCM = ImageIO.read(new File("user-qcm.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel binarize3 = new BinarizeImage(userQCM, 215);
		JPanel histoVerti3 = new HistoVerti(((BinarizeImage) binarize3).getBinaryImage());
		JPanel histoHoriz3 = new HistoHoriz(((BinarizeImage) binarize3).getBinaryImage());
		
		JFrame frame3 = new JFrame("answersQCM");
		//frame.getContentPane().add(binarize, BorderLayout.WEST);
		frame3.getContentPane().add(histoHoriz3, BorderLayout.CENTER);
		frame3.getContentPane().add(histoVerti3, BorderLayout.EAST);
		frame3.pack();
		frame3.setVisible(true);
		frame3.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);	
		
		
	}

}
