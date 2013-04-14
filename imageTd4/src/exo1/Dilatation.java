package exo1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dilatation extends JPanel {

	public Dilatation() {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());

		BufferedImage bi = null;

		try {
			bi = ImageIO.read(this.getClass().getResource("dessin.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bi = imageNB(bi);
		BufferedImage bi2 = null;
		// fermeture
		//bi2 = dilatation(bi);
		//bi2 = erosion(bi2);

		// ouverture
		bi2 = erosion(bi);
		bi2 = dilatation(bi2);

		/*
		ImageIcon ii = new ImageIcon(bi);
		JLabel jl = new JLabel();
		jl.setIcon(ii);
		this.add(jl, BorderLayout.WEST);	
		 */
		ImageIcon ii2 = new ImageIcon(bi2);
		JLabel jl2 = new JLabel();
		jl2.setIcon(ii2);
		this.add(jl2, BorderLayout.WEST);

		ImageIcon ii3 = new ImageIcon(bi);
		JLabel jl3 = new JLabel();
		jl3.setIcon(ii3);
		this.add(jl3, BorderLayout.EAST);

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

				if(newPixel < 150)
					avg.setRGB(i, j, Dilatation.mixColor(0, 0, 0));
				else
					avg.setRGB(i, j, Dilatation.mixColor(255, 255, 255));
			}
		}
		return avg;
	}

	private static int mixColor(int red, int green, int blue){
		return red<<16|green<<8|blue;
	}

	public static BufferedImage dilatation(BufferedImage bi){

		BufferedImage avg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		int[] b = {1,1,1,1};
		int max = 0;
		int[] newPixel = new int[b.length];
		for(int i=0; i<bi.getWidth()-1; i++){
			for(int j=0; j<bi.getHeight()-1; j++){
				newPixel[0] = bi.getRGB(i, j)*b[0];
				newPixel[1] = bi.getRGB(i+1, j)*b[1];
				newPixel[2] = bi.getRGB(i, j+1)*b[2];
				newPixel[3] = bi.getRGB(i+1, j+1)*b[3];
				Arrays.sort(newPixel);
				max = newPixel[3];
				avg.setRGB(i, j, max);
			}
		}
		return avg;
	}

	public static BufferedImage dilatation1(BufferedImage bi){

		BufferedImage avg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());

		int red, green, blue;
		int newpixel;

		for(int i=0; i<bi.getWidth(); i++){
			for(int j=0; j<bi.getHeight(); j++){

				red = new Color(bi.getRGB(i, j)).getRed();
				green = new Color(bi.getRGB(i, j)).getGreen();
				blue = new Color(bi.getRGB(i, j)).getBlue();

				newpixel = (red + green + blue)/3;

				if(newpixel == 255){
					if(i>1) avg.setRGB(i-1, j, Dilatation.mixColor(255, 255, 255)); //north pixel
					if(j>1) avg.setRGB(i, j-1, Dilatation.mixColor(255, 255, 255)); //west pixel
					if(i+1 < avg.getWidth()) avg.setRGB(i+1, j, Dilatation.mixColor(255, 255, 255)); //south pixel
					if(j+1 < avg.getHeight()) avg.setRGB(i, j+1, Dilatation.mixColor(255, 255, 255)); //east pixel
				}
			}
		}

		return avg;
	}

	public static BufferedImage erosion1(BufferedImage bi){

		BufferedImage avg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());

		int red, green, blue;
		int newpixel;

		for(int i=0; i<bi.getWidth(); i++){
			for(int j=0; j<bi.getHeight(); j++){

				red = new Color(bi.getRGB(i, j)).getRed();
				green = new Color(bi.getRGB(i, j)).getGreen();
				blue = new Color(bi.getRGB(i, j)).getBlue();

				newpixel = (red + green + blue)/3;
				//System.out.println("newpixel: " + newpixel);

				//initializing avg bufferedimage to white
				avg.setRGB(i, j, Dilatation.mixColor(255, 255, 255));

				if(newpixel == 0){
					if(i>1) avg.setRGB(i-1, j, Dilatation.mixColor(0, 0, 0)); //north pixel
					if(j>1) avg.setRGB(i, j-1, Dilatation.mixColor(0, 0, 0)); //west pixel
					if(i+1 < bi.getWidth()) avg.setRGB(i+1, j, Dilatation.mixColor(0, 0, 0)); //south pixel
					if(j+1 < bi.getHeight()) avg.setRGB(i, j+1, Dilatation.mixColor(0, 0, 0)); //east pixel
				} 
			} 
		}

		return avg;
	}

	public static BufferedImage erosion(BufferedImage bi){

		BufferedImage avg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		int[] b = {1,1,1,1};
		int min = 1;
		int[] newPixel = new int[b.length];
		for(int i=0; i<bi.getWidth()-1; i++){
			for(int j=0; j<bi.getHeight()-1; j++){
				newPixel[0] = bi.getRGB(i, j)*b[0];
				newPixel[1] = bi.getRGB(i+1, j)*b[1];
				newPixel[2] = bi.getRGB(i, j+1)*b[2];
				newPixel[3] = bi.getRGB(i+1, j+1)*b[3];
				Arrays.sort(newPixel);
				min = newPixel[0];
				avg.setRGB(i, j, min);
			}
		}
		return avg;
	}

}
