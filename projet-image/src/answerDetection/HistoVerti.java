package answerDetection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HistoVerti extends JPanel {

	public HistoVerti(BufferedImage BinaryImage) {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());
		
		BufferedImage bi2 = null;

		bi2 = plotHistogram(BinaryImage);

		ImageIcon ii = new ImageIcon(BinaryImage);
		JLabel label = new JLabel();
		label.setIcon(ii);
		this.add(label, BorderLayout.WEST);

		ImageIcon ii2 = new ImageIcon(bi2);
		JLabel label2 = new JLabel();
		label2.setIcon(ii2);
		this.add(label2, BorderLayout.EAST);
		
	}
	
	public static BufferedImage plotHistogram(BufferedImage bi){

		int width = bi.getWidth();
		int height = bi.getHeight();
		int red, green, blue;
		int pixel;
		int[] pixels = new int[height];
		Color color;

		BufferedImage avg = new BufferedImage(width, height, bi.getType());

		for(int i=0; i<pixels.length; i++){
			pixels[i]  = 0;
		}

		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++){
				//initialze avg to white color
				avg.setRGB(i, j, HistoVerti.mixColor(255, 255, 255));
			}
		}

		for(int i = 0; i<bi.getWidth(); i++){
			for(int j = 0; j<bi.getHeight(); j++){

				color = new Color(bi.getRGB(i, j));
				red = color.getRed();
				green = color.getGreen();
				blue = color.getBlue();

				pixel = (red + green + blue)/3;
				//System.out.println("pixel[" +i+", " +j+ "]= " + pixel);

				//we count our black pixels in a binary image
				if(pixel == 0){ 
					pixels[i] ++;
					//System.out.println("pixels[" + j + "]: " + pixels[i]);
				}
			}
		}

		//we draw the counted black pixels
		for(int i =0; i<width; i++){
			for(int j =height-1; j>height-pixels[i]; j--){
				avg.setRGB(i, j, HistoVerti.mixColor(0, 0, 0));
			}
		}

		return avg;
	}

	private static int mixColor(int red, int green, int blue){
		return red<<16|green<<8|blue;
	}

}
