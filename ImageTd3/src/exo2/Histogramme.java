package exo2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Histogramme extends JPanel {

	public Histogramme() {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());

		BufferedImage bi = null;
		BufferedImage bi2 = null;

		try {
			bi = ImageIO.read(this.getClass().getResource("qcm.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bi2 = plotHistogramHorizontal(bi);

		ImageIcon ii = new ImageIcon(bi);
		JLabel label = new JLabel();
		label.setIcon(ii);
		this.add(label, BorderLayout.CENTER);
		
		ImageIcon ii2 = new ImageIcon(bi2);
		JLabel label2 = new JLabel();
		label2.setIcon(ii2);
		this.add(label2, BorderLayout.EAST);

	}


	public static int[] getPixelsHorizontal(BufferedImage bi){
		int red, green, blue;
		int width = bi.getWidth();
		int height = bi.getHeight();
		int pixel = 0;
		int[] pixels = new int[height];

		for(int i=0; i<pixels.length; i++){
			pixels[i]  = 0;
		}

		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++){

				red = new Color(bi.getRGB(i, j)).getRed();
				green = new Color(bi.getRGB(i, j)).getGreen();
				blue = new Color(bi.getRGB(i, j)).getBlue();

				pixel = (red + green + blue)/3;

				//System.out.println("pixel= " + pixel);
				if(pixel < 127){
					pixels[i] ++;
					System.out.println("pixel[" + i + "]= " + pixels[i]);
				}
			}
		}
		return pixels;
	}

	public static BufferedImage plotHistogramHorizontal(BufferedImage bi){

		BufferedImage bi2 = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		int pixels[] = getPixelsHorizontal(bi);

		for(int i = 0; i<pixels.length; i++)
			System.out.println("pixel["+ i+ "]= "+pixels[i]);
		
		for(int i = 0; i<pixels.length; i++){
				for(int j=0; j>pixels[i]; j++){
					if(pixels[i] == 0)
					bi2.setRGB(i, j, 255+255+255);
				}
			}
		return bi2;
	}
}
