package defo;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImageGrayScale extends JPanel {

	private LoadImage image;
	private BufferedImage biGray;
	private int[][] pixelTable;
	private int alpha;
	
	public ImageGrayScale() {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());
		
		int newPixel;
		this.biGray = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		
		for(int i=0; i<bi.getWidth(); i++){
			for(int j=0; j<bi.getHeight(); j++){
				
				newPixel = colorToRGB(alpha, pixelTable[i][j], pixelTable[i][j], pixelTable[i][j]);
				//System.out.println(red +" "+green+ " "+blue+ " " + alpha + " " + newPixel);

				biGray.setRGB(i, j, newPixel);
			}
		}
		
	}

	private static int colorToRGB(int alpha, int red, int green, int blue) {
		int newPixel = 0;
		newPixel += alpha;
		//System.out.println(newPixel);
		newPixel = newPixel << 8;
		//System.out.println(newPixel);
		newPixel += red; newPixel = newPixel << 8;
		//System.out.println(newPixel);
		newPixel += green; newPixel = newPixel << 8;
		//System.out.println("green= " + green);
		//System.out.println("red= " + red);
		//System.out.println("blue= " + blue);
		//System.out.println(newPixel);
		newPixel += blue;
		//System.out.println(newPixel);
		return newPixel;
	}
	
}
