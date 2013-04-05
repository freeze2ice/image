import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Qcm extends JPanel {

	public Qcm() {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());

		BufferedImage bi = null;
		try {
			bi = ImageIO.read(this.getClass().getResource("qcmImage.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		float[] matrix = {1, 1 ,1 ,1 ,1 ,1 ,1 ,1, 1};
		float[] matrix2 = {1/9, 1/9, 1/9, 1/9, 1/9, 1/9, 1/9, 1/9, 1/9 };
		//float[] matrix2 = {-1, -1, -1, -1, -9, -1, -1, -1, -1};
		float[] matrixY = {1, 2, 1, 0, 0, 0, -1, -2, -1};
		float[] matrixX = {1, 0, -1, 2, 0, -2, 1, 0, -1};
		float[] matrix1 = {0, -1, 0, -1, 5, -1, 0, -1, 0 };
		 */

		BufferedImage bi3;
		BufferedImage bi2 = grayImage(bi);
		bi2 = binarizeImage(bi2, 243);
		bi2 = filter(bi2);
		//bi2 = erosion(bi2);
		//bi2 = plotVertiHistogram(bi3);
		bi3 = extractObject(bi2);
		//bi3 = plotHorizHistogram(bi3);
		bi3 = extractObjectHoriz(bi3);

		ImageIcon icon = new ImageIcon(bi2);
		JLabel label = new JLabel();
		label.setIcon(icon);
		this.add(label, BorderLayout.WEST);

		JLabel label2 = new JLabel();
		label2.setIcon(new ImageIcon(bi3));
		this.add(label2, BorderLayout.EAST);

	}

	public static int getPixel(BufferedImage image, int x, int y){		
		int red=0, green=0, blue=0, pixel=0;
		Color color;
		color = new Color(image.getRGB(x, y));
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
		pixel =red + green +blue;
		return pixel;
	}

	public static void logConsole(Object obj){
		System.out.println(obj);
	}

	private static int mixColor(int red, int green, int blue){
		return red<<16|green<<8|blue;
	}

	public static BufferedImage grayImage(BufferedImage image){

		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

		int pixel=0;

		for(int x=0; x<image.getWidth(); x++){
			for(int y=0; y<image.getHeight(); y++){
				pixel = Qcm.getPixel(image, x, y)/3;
				pixel = Qcm.mixColor(pixel, pixel, pixel);
				output.setRGB(x, y, pixel);
			}
		}
		return output;
	}

	public static BufferedImage binarizeImage(BufferedImage grayImage, int threshold){
		BufferedImage output = new BufferedImage(grayImage.getWidth(), grayImage.getHeight(), grayImage.getType());
		int pixel=0;
		for(int x=0; x<grayImage.getWidth(); x++){
			for(int y=0; y<grayImage.getHeight(); y++){
				pixel = Qcm.getPixel(grayImage, x, y)/3;
				if(pixel > threshold){
					output.setRGB(x, y, Qcm.mixColor(255, 255, 255));
				}
				else {
					output.setRGB(x, y, Qcm.mixColor(0, 0, 0));
				}
			}
		}
		return output;
	}

	public static BufferedImage dilatation(BufferedImage image){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		int pixel;
		for(int x=0; x<image.getWidth(); x++){
			for(int y=0; y<image.getHeight(); y++){
				pixel = Qcm.getPixel(image, x, y)/3;
				if(pixel == 255){
					if(x>1) output.setRGB(x-1, y, Qcm.mixColor(255, 255, 255)); //north pixel
					if(y>1) output.setRGB(x, y-1, Qcm.mixColor(255, 255, 255)); //west pixel
					if(x+1 < output.getWidth()) output.setRGB(x+1, y, Qcm.mixColor(255, 255, 255)); //south pixel
					if(y+1 < output.getHeight()) output.setRGB(x, y+1, Qcm.mixColor(255, 255, 255)); //east pixel
				}
			}
		}
		return output;
	}

	public static BufferedImage erosion(BufferedImage image){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());	
		int pixel;
		for(int x=0; x<image.getWidth(); x++){
			for(int y=0; y<image.getHeight(); y++){
				pixel = Qcm.getPixel(image, x, y)/3;				
				//initializing output bufferedimage to white
				output.setRGB(x, y, Qcm.mixColor(255, 255, 255));
				if(pixel == 0){
					if(x>1) output.setRGB(x-1, y, Qcm.mixColor(0, 0, 0)); //north pixel
					if(y>1) output.setRGB(x, y-1, Qcm.mixColor(0, 0, 0)); //west pixel
					if(x+1 < image.getWidth()) output.setRGB(x+1, y, Qcm.mixColor(0, 0, 0)); //south pixel
					if(y+1 < image.getHeight()) output.setRGB(x, y+1, Qcm.mixColor(0, 0, 0)); //east pixel
				} 
			} 
		}
		return output;
	}

	public static BufferedImage plotHistogram(BufferedImage image){
		int width = 256;
		int height = image.getHeight();
		int pixel;
		int[] pixels = new int[256];
		BufferedImage output = new BufferedImage(width, height, image.getType());
		for(int i=0; i<pixels.length; i++){
			pixels[i]  = 0;
		}
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++){
				//initialze output to white color
				output.setRGB(i, j, Qcm.mixColor(255, 255, 255));
			}
		}
		for(int x= 0; x<image.getWidth(); x++){
			for(int y = 0; y<image.getHeight(); y++){
				pixel = Qcm.getPixel(image, x, y)/3;
				pixels[pixel] ++;
			}
		}
		for(int i=0; i<pixels.length; i++){
			if(pixels[i] > height) pixels[i] = height-1;
		}
		for(int x =0; x<256; x++){
			int y=height-1;
			do{				
				output.setRGB(x, y, Qcm.mixColor(0, 0, 0));
				y--;
			}while(y>0 && y>height-pixels[x]);
		}
		return output;
	}

	public static BufferedImage plotHorizHistogram(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		int pixel;
		int[] pixels = new int[height];
		BufferedImage output = new BufferedImage(width, height, image.getType());
		for(int i=0; i<pixels.length; i++){
			pixels[i]  = 0;
		}
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++){
				
			}
		}
		for(int x = 0; x<image.getWidth(); x++){
			for(int y = 0; y<image.getHeight(); y++){
				//initialze output to white color
				output.setRGB(x, y, Qcm.mixColor(255, 255, 255));
				//we count our black pixels in a binary image
				if(Qcm.getPixel(image, x, y) == 0)	pixels[y] ++;
			}
		}
		for(int x =width-1; x>0; x--){
			for(int y =0; y<height; y++){
				if(pixels[y] != 0){
					output.setRGB(x, y, Qcm.mixColor(0, 0, 0));
					pixels[y]--;
				}
			}
		}
		return output;
	}

	public static BufferedImage plotVertiHistogram(BufferedImage image){
		int[] pixels = new int[image.getWidth()];
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for(int i=0; i<pixels.length; i++){
			pixels[i]  = 0;
		}
		for(int x = 0; x<image.getWidth(); x++){
			for(int y = 0; y<image.getHeight(); y++){
				//initialze output to white color
				output.setRGB(x, y, Qcm.mixColor(255, 255, 255));
			}
		}
		for(int x = 0; x<image.getWidth(); x++){
			for(int y = 0; y<image.getHeight(); y++){
				//we count our black pixels in a binary image
				if(Qcm.getPixel(image, x, y) == 0) pixels[x]++;
			}
		}
		//we draw the counted black pixels
		for(int x =0; x<image.getWidth(); x++){
			for(int y =image.getHeight()-1; y>image.getHeight()-pixels[x]; y--){
				output.setRGB(x, y, Qcm.mixColor(0, 0, 0));
			}
		}
		return output;
	}

	private static BufferedImage convolve3x3(BufferedImage image, float[] matrix){
		int width = image.getWidth();
		int height = image.getHeight();
		int pixel=0;
		BufferedImage output = new BufferedImage(width, height, image.getType());
		for(int x =0; x<width; x++){
			for(int y = 0; y<height; y++){
				if((x>0 && y>0) && (x<width-1 && y<height-1))
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x-1, y-1)*matrix[0]) + (image.getRGB(x-1, y)*matrix[1]) + (image.getRGB(x-1, y+1)*matrix[2]) + (image.getRGB(x, y-1)*matrix[3]) + (image.getRGB(x, y+1)*matrix[5]) + (image.getRGB(x+1, y-1)*matrix[6]) + (image.getRGB(x+1, y)*matrix[7]) + (image.getRGB(x+1, y+1)*matrix[8]));
				if(x==0 && y==0)
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x, y+1)*matrix[5]) + (image.getRGB(x+1, y)*matrix[7]) + (image.getRGB(x+1, y+1)*matrix[8]));
				if(x==0 && y==height-1)
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x, y-1)*matrix[3]) + (image.getRGB(x+1, y-1)*matrix[6]) + (image.getRGB(x+1, y)*matrix[7]));
				if(x==width-1 && y==0)
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x-1, y)*matrix[1]) + (image.getRGB(x-1, y+1)*matrix[2]) + (image.getRGB(x, y+1)*matrix[5]));
				if(x==width-1 && y==height-1)
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x-1, y-1)*matrix[0]) + (image.getRGB(x-1, y)*matrix[1]) + (image.getRGB(x, y-1)*matrix[3]));				
				if(x>0 && x<width-1 && y==0)
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x-1, y)*matrix[1]) + (image.getRGB(x-1, y+1)*matrix[2]) + (image.getRGB(x, y+1)*matrix[5]) + (image.getRGB(x+1, y)*matrix[7]) + (image.getRGB(x+1, y+1)*matrix[8]));
				if(x>0 && x<width-1 && y==height-1)
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x-1, y-1)*matrix[0]) + (image.getRGB(x-1, y)*matrix[1]) + (image.getRGB(x, y-1)*matrix[3]) + (image.getRGB(x+1, y-1)*matrix[6]) + (image.getRGB(x+1, y)*matrix[7]));
				if(x==0 && y>0 && y<height-1)
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x, y-1)*matrix[3]) + (image.getRGB(x, y+1)*matrix[5]) + (image.getRGB(x+1, y-1)*matrix[6]) + (image.getRGB(x+1, y)*matrix[7]) + (image.getRGB(x+1, y+1)*matrix[8]));
				if(x==width-1 && y>0 && y<height-1)
					pixel = (int) ((image.getRGB(x, y)*matrix[4]) + (image.getRGB(x-1, y-1)*matrix[0]) + (image.getRGB(x-1, y)*matrix[1]) + (image.getRGB(x-1, y+1)*matrix[2]) + (image.getRGB(x, y-1)*matrix[3]) + (image.getRGB(x, y+1)*matrix[5]));
				output.setRGB(x, y, pixel);				
			}
		}
		return output;
	}

	private static BufferedImage convolveMini(BufferedImage bi, float[] matrix){
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
		BufferedImage bi2 =  new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());		
		bi2 = op.filter(bi, bi2);
		return bi2;
	}

	public static BufferedImage filter(BufferedImage image){

		int pixel=0, w=765, b=0;
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for(int x=0; x<image.getWidth(); x++){
			for(int y=0; y<image.getHeight(); y++){
				if(x>0 && y>0 && x<image.getWidth() && y<image.getHeight()){
					pixel = Qcm.getPixel(image, x, y);
					if(pixel == b && (((Qcm.getPixel(image, x-1, y) == b) && (Qcm.getPixel(image, x+1, y) == b)
							&& (Qcm.getPixel(image, x, y+1) == w) && (Qcm.getPixel(image, x, y-1) == w))
							|| ((Qcm.getPixel(image, x, y-1) == b) && (Qcm.getPixel(image, x, y+1) == b)
									&& (Qcm.getPixel(image, x-1, y) == w) && (Qcm.getPixel(image, x+1, y) == w)))){
						output.setRGB(x, y, Qcm.mixColor(b, b, b));
					}
					else output.setRGB(x, y, Qcm.mixColor(w, w, w));
				}
				else output.setRGB(x, y, Qcm.mixColor(w, w, w));
			}
		}
		return output;
	}

	public static BufferedImage filtreMedian(BufferedImage image){
		int newPixel = 0;
		int pixels[] = new int[9];
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for(int i=0; i<pixels.length; i++){
			pixels[i]=0;
		}
		for(int x=1; x<image.getWidth()-1; x++){
			for(int y=1; y<image.getHeight()-1; y++){
				pixels[0] = Qcm.getPixel(image, x, y);
				pixels[1] = Qcm.getPixel(image, x-1, y-1);
				pixels[2] = Qcm.getPixel(image, x-1, y);
				pixels[3] = Qcm.getPixel(image, x-1, y+1);
				pixels[4] = Qcm.getPixel(image, x, y-1);
				pixels[5] = Qcm.getPixel(image, x, y+1);
				pixels[6] = Qcm.getPixel(image, x+1, y-1);
				pixels[7] = Qcm.getPixel(image, x+1, y);
				pixels[8] = Qcm.getPixel(image, x+1, y+1);
				Arrays.sort(pixels);
				newPixel = pixels[4];
				output.setRGB(x, y, Qcm.mixColor(newPixel, newPixel, newPixel));
			}
		}

		return output;
	}

	public static BufferedImage extractObject(BufferedImage image){
		
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		int pixels[] = new int[image.getWidth()];

		for(int i=0; i<pixels.length; i++){
			pixels[i]  = 0;
		}
		for(int x = 0; x<image.getWidth(); x++){
			for(int y = 0; y<image.getHeight(); y++){
				output.setRGB(x, y, Qcm.mixColor(255, 255, 255));
				//we count our black pixels in a binary image
				if(Qcm.getPixel(image, x, y) == 0)	pixels[x]++;
			}
		}
		int max = pixels[0];
		int indexStart =0;
		for(int i=0; i<pixels.length; i++){
			if(pixels[i] > max){
				max = pixels[i];
				indexStart = i;
			}
		}
		System.out.println("max: " + max + " index: " + indexStart + " image width: " + image.getWidth());
		int indexEnd = 0;
		for(int i=0; i<pixels.length; i++){
			if(max - pixels[i] < 10){
				indexEnd = i;
				//System.out.print("max: " + pixels[i] + " index: " + i + " | ");
			}
		}
		System.out.println("Found object in x[" + indexStart + ", " + (indexEnd+1) + "]");
		int pixel = 0;
		for(int x = indexStart; x<indexEnd+1; x++){
			for(int y = 0; y<image.getHeight(); y++){
				pixel = Qcm.getPixel(image, x, y);
				output.setRGB(x, y, Qcm.mixColor(pixel, pixel, pixel));
			}
		}
		Graphics2D graphic = output.createGraphics();
		graphic.setColor(new Color(0));
		graphic.drawLine(indexStart, 0, indexStart, image.getHeight());
		graphic.drawLine(indexEnd, 0, indexEnd, image.getHeight());
		return output;
	}

	public static BufferedImage extractObjectHoriz(BufferedImage image){
		
		int[] pixels = new int[image.getHeight()];
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		
		for(int i=0; i<pixels.length; i++){
			pixels[i]  = 0;
		}
		for(int x = 0; x<image.getWidth(); x++){
			for(int y = 0; y<image.getHeight(); y++){
				output.setRGB(x, y, Qcm.mixColor(255, 255, 255));
				if(Qcm.getPixel(image, x, y) == 0) pixels[y] ++;
			}
		}
		
		int max = pixels[0];
		for(int i=0; i<pixels.length; i++){
			if(pixels[i] > max){
				max = pixels[i];
			}
		}
		int indexStart =0;
		for(int i=0; i<pixels.length; i++){
			if(max-pixels[i] < 3){
				indexStart = i;
				break;
			}
		}
		System.out.println("peak: " + max + ", on index: " + indexStart + ", image height: " + image.getHeight());
		int indexEnd = 0;
		for(int i=0; i<pixels.length; i++){
			if(max - pixels[i] < 6){
				indexEnd = i;
				//System.out.print("peak: " + pixels[i] + " index: " + i + " | ");
			}
		}
		System.out.println("Found object in y[" + indexStart + ", " + (indexEnd+1) + "]");
		int pixel = 0;
		for(int x =0; x<image.getWidth(); x++){
			for(int y =indexStart; y<indexEnd+1; y++){
				pixel = Qcm.getPixel(image, x, y);
				output.setRGB(x, y, Qcm.mixColor(pixel, pixel, pixel));
			}
		}
		
		//square height
		//int sh = 8;
		//space between each square approx
		//int sbes = 10;
		
		Graphics2D graphic = output.createGraphics();
		graphic.setColor(new Color(0));
		
		graphic.drawLine(5, indexStart, 13, indexStart);
		graphic.drawLine(5, indexStart+8, 13, indexStart+8);
		
		graphic.drawLine(5, indexStart+18, 13, indexStart+18);
		graphic.drawLine(5, indexStart+26, 13, indexStart+26);
		
		graphic.drawLine(5, indexStart+37, 13, indexStart+37);
		graphic.drawLine(5, indexStart+45, 13, indexStart+45);
		
		graphic.drawLine(5, indexStart+56, 13, indexStart+56);
		graphic.drawLine(5, indexStart+64, 13, indexStart+64);
		
		graphic.drawLine(5, indexStart+74, 13, indexStart+74);
		graphic.drawLine(5, indexStart+82, 13, indexStart+82);

		return output;
	}
}
