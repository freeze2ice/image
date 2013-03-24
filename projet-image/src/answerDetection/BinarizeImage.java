package answerDetection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BinarizeImage extends JPanel {
	
	private BufferedImage bi2;
	
	public BinarizeImage(BufferedImage greyScaleImage, int seuil) {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());
		
		this.bi2 = null;

		bi2 = imageNB(greyScaleImage, seuil);

		ImageIcon ii = new ImageIcon(greyScaleImage);
		JLabel label = new JLabel();
		label.setIcon(ii);
		this.add(label, BorderLayout.WEST);

		ImageIcon ii2 = new ImageIcon(bi2);
		JLabel label2 = new JLabel();
		label2.setIcon(ii2);
		this.add(label2, BorderLayout.EAST);

	}

	public static BufferedImage imageNB(BufferedImage bi, int seuil){

		BufferedImage avg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		int newPixel;
		int red, blue, green;
		Color color;

		for(int i=0; i<avg.getWidth(); i++){
			for(int j=0; j<avg.getHeight(); j++){

				color = new Color(bi.getRGB(i, j));
				red = color.getRed();
				green = color.getGreen();
				blue = color.getBlue();

				newPixel = (red + green + blue)/3;

				if(newPixel < seuil)
					avg.setRGB(i, j, BinarizeImage.mixColor(0, 0, 0));
				else
					avg.setRGB(i, j, BinarizeImage.mixColor(255, 255, 255));
			}
		}
		return avg;
	}

	private static int mixColor(int red, int green, int blue){
		return red<<16|green<<8|blue;
	}

	public BufferedImage getBinaryImage(){
		return bi2;
	}
	
}
