import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyJPanel extends JPanel {

	private Qcm qcm;
	
	public MyJPanel(File image, int colorThreshold, int binaryThreshold, boolean secOuverture) {
		super(new BorderLayout());		
		
		qcm = new Qcm(image);
		BufferedImage bi = qcm.getBi();
		
		JLabel label1 = new JLabel();
		label1.setIcon(new ImageIcon(bi));
		this.add(label1, BorderLayout.NORTH);
		
		bi = qcm.colorFilter(bi, colorThreshold);
		
		JLabel label2 = new JLabel();
		label2.setIcon(new ImageIcon(bi));
		this.add(label2, BorderLayout.WEST);
		
		bi = qcm.grayImage(bi);
		bi = qcm.binarizeImage(bi, binaryThreshold);
		
		JLabel label3 = new JLabel();
		label3.setIcon(new ImageIcon(bi));
		this.add(label3, BorderLayout.CENTER);
		
		bi = qcm.ouverture(bi);
		if(secOuverture) bi = qcm.ouverture(bi);
		
		JLabel label4 = new JLabel();
		label4.setIcon(new ImageIcon(bi));
		this.add(label4, BorderLayout.EAST);
		
	}
	
}
