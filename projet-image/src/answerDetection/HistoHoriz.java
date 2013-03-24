package answerDetection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HistoHoriz extends JPanel {

	public HistoHoriz(BufferedImage BinaryImage) {
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

		//initialze avg to white color
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++){
				avg.setRGB(i, j, HistoHoriz.mixColor(255, 255, 255));
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
					pixels[j] ++;
					//System.out.println("pixels[" + j + "]: " + pixels[j]);
				}
			}
		}
		
		//we draw the counted black pixels
		for(int i =width-1; i>0; i--){
			for(int j =0; j<height; j++){
				if(pixels[j] != 0){
					avg.setRGB(i, j, HistoHoriz.mixColor(0, 0, 0));
					pixels[j]--;
				}
			}
		}

		return avg;
	}

	private static int mixColor(int red, int green, int blue){
		return red<<16|green<<8|blue;
	}
	
}