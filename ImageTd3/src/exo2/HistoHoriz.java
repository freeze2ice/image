package exo2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HistoHoriz extends JPanel {

	public HistoHoriz() {
		// TODO Auto-generated constructor stub
		super( new BorderLayout());

		BufferedImage bi = null;
		BufferedImage bi2 = null;

		try {
			bi = ImageIO.read(this.getClass().getResource("qcm.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedImage bi3 = imageNB(bi);
		bi2 = plotHistogram(bi3);

		ImageIcon ii = new ImageIcon(bi);
		JLabel label = new JLabel();
		label.setIcon(ii);
		this.add(label, BorderLayout.WEST);

		ImageIcon ii3 = new ImageIcon(bi3);
		JLabel jl3 = new JLabel();
		jl3.setIcon(ii3);
		this.add(jl3, BorderLayout.CENTER);

		ImageIcon ii2 = new ImageIcon(bi2);
		JLabel label2 = new JLabel();
		label2.setIcon(ii2);
		this.add(label2, BorderLayout.EAST);
	}

	public static BufferedImage imageNB(BufferedImage bi){

		BufferedImage avg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		int newPixel;
		int red, blue, green;

		for(int i=0; i<avg.getWidth(); i++){
			for(int j=0; j<avg.getHeight(); j++){

				red = new Color(bi.getRGB(i, j)).getRed();
				green = new Color(bi.getRGB(i, j)).getGreen();
				blue = new Color(bi.getRGB(i, j)).getBlue();

				newPixel = (red + green + blue)/3;

				if(newPixel < 215)
					avg.setRGB(i, j, HistoHoriz.mixColor(0, 0, 0));
				else
					avg.setRGB(i, j, HistoHoriz.mixColor(255, 255, 255));
			}
		}
		return avg;
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
