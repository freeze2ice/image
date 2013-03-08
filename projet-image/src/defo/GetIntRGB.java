package defo;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GetIntRGB {

	//private BufferedImage bi;
	//private int newPixel;
	private int[][] pixelTable;
	private int alpha;
	
	public GetIntRGB(BufferedImage bi) {
		// TODO Auto-generated constructor stub
		//this.bi = bi;
		int  red, green, blue;
		int height = bi.getHeight();
		int width = bi.getWidth();
		int newPixel;

		int[] avgLUT = new int[766];
		
		pixelTable = new int[width][height];

		for(int i=0; i<avgLUT.length; i++)
			avgLUT[i] = (int)(i/3);

		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){

				alpha = new Color(bi.getRGB(i, j)).getAlpha();
				red = new Color(bi.getRGB(i, j)).getRed();
				green = new Color(bi.getRGB(i, j)).getGreen();
				blue = new Color(bi.getRGB(i, j)).getBlue();

				newPixel = (red + green + blue);

				//System.out.println("before= "+newPixel);
				newPixel = avgLUT[newPixel];
				//System.out.println("after= "+newPixel);
				pixelTable[i][j] = newPixel;
			}
		}

	}

}
