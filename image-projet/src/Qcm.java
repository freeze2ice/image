import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Qcm extends JPanel {

	public Qcm(File image) {
		super(new BorderLayout());
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage bi3 = null;
		BufferedImage bi2 = null;

		bi3 = colorFilter(bi, 450);
		bi3 = grayImage(bi3);
		bi3 = binarizeImage(bi3, 250);
		bi2 = ouverture(bi3);
		bi3 = detectObject(bi2);

		ImageIcon icon = new ImageIcon(bi);
		JLabel label = new JLabel();
		label.setIcon(icon);
		this.add(label, BorderLayout.WEST);

		JLabel label2 = new JLabel();
		label2.setIcon(new ImageIcon(bi3));
		this.add(label2, BorderLayout.EAST);

		JLabel label3 = new JLabel();
		label3.setIcon(new ImageIcon(bi2));
		this.add(label3, BorderLayout.CENTER);

	}

	public static int getPixel(BufferedImage image, int x, int y) {
		int red = 0, green = 0, blue = 0, pixel = 0;
		Color color;
		color = new Color(image.getRGB(x, y));
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
		pixel = red + green + blue;
		return pixel;
	}

	private static int mixColor(int red, int green, int blue) {
		return red << 16 | green << 8 | blue;
	}

	public static BufferedImage grayImage(BufferedImage image) {
		BufferedImage output = new BufferedImage(image.getWidth(),image.getHeight(), image.getType());
		int pixel = 0;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel =  getPixel(image, x, y) / 3;
				pixel =  mixColor(pixel, pixel, pixel);
				output.setRGB(x, y, pixel);
			}
		}
		return output;
	}

	public static BufferedImage binarizeImage(BufferedImage grayImage, int threshold) {
		BufferedImage output = new BufferedImage(grayImage.getWidth(), grayImage.getHeight(), grayImage.getType());
		int pixel = 0;
		for (int x = 0; x < grayImage.getWidth(); x++) {
			for (int y = 0; y < grayImage.getHeight(); y++) {
				pixel =  getPixel(grayImage, x, y) / 3;
				if (pixel > threshold) {
					output.setRGB(x, y,  mixColor(0, 0, 0));
				} else {
					output.setRGB(x, y,  mixColor(255, 255, 255));
				}
			}
		}
		return output;
	}

	public static BufferedImage dilatation(BufferedImage image) {
		BufferedImage output = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());
		int[] b = { 1, 1, 1, 1 };
		int max = 0;
		int[] newPixel = new int[b.length];
		for (int i = 0; i < image.getWidth() - 1; i++) {
			for (int j = 0; j < image.getHeight() - 1; j++) {
				newPixel[0] = image.getRGB(i, j) * b[0];
				newPixel[1] = image.getRGB(i + 1, j) * b[1];
				newPixel[2] = image.getRGB(i, j + 1) * b[2];
				newPixel[3] = image.getRGB(i + 1, j + 1) * b[3];
				Arrays.sort(newPixel);
				max = newPixel[3];
				output.setRGB(i, j, max);
			}
		}
		return output;
	}

	public static BufferedImage erosion(BufferedImage image) {
		BufferedImage output = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());
		int[] b = { 1, 1, 1, 1 };
		int min = 1;
		int[] newPixel = new int[b.length];
		for (int i = 0; i < image.getWidth() - 1; i++) {
			for (int j = 0; j < image.getHeight() - 1; j++) {
				newPixel[0] = image.getRGB(i, j) * b[0];
				newPixel[1] = image.getRGB(i + 1, j) * b[1];
				newPixel[2] = image.getRGB(i, j + 1) * b[2];
				newPixel[3] = image.getRGB(i + 1, j + 1) * b[3];
				Arrays.sort(newPixel);
				min = newPixel[0];
				output.setRGB(i, j, min);
			}
		}
		return output;
	}

	public static BufferedImage ouverture(BufferedImage image){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		output = dilatation(image);
		output = erosion(image);
		return output;
	}

	public static BufferedImage fermeture(BufferedImage image){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		output = erosion(image);
		output = dilatation(image);
		return output;
	}

	public static BufferedImage plotHistogram(BufferedImage image) {
		int width = 256;
		int height = image.getHeight();
		int pixel;
		int[] pixels = new int[256];
		BufferedImage output = new BufferedImage(width, height, image.getType());
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// Initialize output to white color
				output.setRGB(i, j,  mixColor(255, 255, 255));
			}
		}
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel =  getPixel(image, x, y) / 3;
				pixels[pixel]++;
			}
		}
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i] > height)
				pixels[i] = height - 1;
		}
		for (int x = 0; x < 256; x++) {
			int y = height - 1;
			do {
				output.setRGB(x, y,  mixColor(0, 0, 0));
				y--;
			} while (y > 0 && y > height - pixels[x]);
		}
		return output;
	}

	public static BufferedImage plotHorizHistogram(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = new int[height];
		BufferedImage output = new BufferedImage(width, height, image.getType());
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// initialze output to white color
				output.setRGB(x, y,  mixColor(255, 255, 255));
				// we count our black pixels in a binary image
				if ( getPixel(image, x, y) == 0)
					pixels[y]++;
			}
		}
		for (int x = width - 1; x > 0; x--) {
			for (int y = 0; y < height; y++) {
				if (pixels[y] != 0) {
					output.setRGB(x, y,  mixColor(0, 0, 0));
					pixels[y]--;
				}
			}
		}
		return output;
	}

	public static BufferedImage plotVertiHistogram(BufferedImage image) {
		int[] pixels = new int[image.getWidth()];
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// initialze output to white color
				output.setRGB(x, y,  mixColor(255, 255, 255));
				// we count our black pixels in a binary image
				if ( getPixel(image, x, y) == 0)
					pixels[x]++;
			}
		}
		// we draw the counted black pixels
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = image.getHeight() - 1; y > image.getHeight()- pixels[x]; y--) {
				output.setRGB(x, y,  mixColor(0, 0, 0));
			}
		}
		return output;
	}

	public static BufferedImage colorFilter(BufferedImage image, int seuil){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for(int x=0; x<image.getWidth(); x++){
			for(int y=0; y<image.getHeight(); y++){	
				/*
				output.setRGB(x, y, (image.getRGB(x, y) & 0xff00ff00)
                        | ((image.getRGB(x, y) & 0xff0000) >> 16)
                        | ((image.getRGB(x, y) & 0xff) << 16)); */
				if( getPixel(image, x, y) > seuil){
					output.setRGB(x, y,  mixColor(255, 255, 255));
				}
				else output.setRGB(x, y, image.getRGB(x, y));
			}
		}
		return output;
	}

	public static BufferedImage fourConnectivity(BufferedImage image){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		int region[][] = new int[image.getWidth()][image.getHeight()];
		int cpt = 0;
		Color color;
		for(int x=0; x<image.getWidth(); x++){
			for(int y=0; y<image.getHeight(); y++){
				region[x][y] = 0;
				output.setRGB(x, y, mixColor(255, 255, 255));
			}
		}
		for(int x=1; x<image.getWidth()-1; x++){
			for(int y=1; y<image.getHeight()-1; y++){
				color = new Color(new Random().nextInt(600));
				if(getPixel(image, x, y) != 0){
					if(getPixel(image, x-1, y) !=0){
						if(getPixel(image, x, y-1) !=0) {
							System.out.println("North and West pixels belong to the same region and must be merged");
							region[x][y] = Math.min(region[x-1][y], region[x][y-1]);
						} else {
							System.out.println("we are in the same region");
							region[x][y] = region[x-1][y];
						}
					} else if(getPixel(image, x-1, y) ==0) {
						if(getPixel(image, x, y-1) !=0) {
							System.out.println("Assign the label of the North pixel to the current pixel");
							region[x][y] = region[x][y-1];
						} else if (getPixel(image, x, y-1) ==0) {
							System.out.println("Create a new label id and assign it to the current pixel");
							cpt++;
							region[x][y] = cpt;
							output.setRGB(x, y, color.getRGB());
						}
					}
					System.out.println("region["+x+"]["+y+"]=" + region[x][y]);
					output.setRGB(x, y, color.getRGB());
				}
			}
		}
		return output;
	}

	public static BufferedImage zoom(BufferedImage image, int zoomLevel){
		int newImageWidth = image.getWidth() * zoomLevel;
		int newImageHeight = image.getHeight() * zoomLevel;
		BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, image.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, newImageWidth , newImageHeight , null);
		g.dispose();
		return resizedImage;
	}

	public static BufferedImage detectObject(BufferedImage image){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		int[] pixelsY = new int[image.getHeight()];
		int[] pixelsX = new int[image.getWidth()];
		int[] startEndIndex ={-1,1};
		ArrayList<Integer> listY = new ArrayList<Integer>();
		ArrayList<Integer> listX = new ArrayList<Integer>();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// Initialize output to white color
				output.setRGB(x, y,  mixColor(255, 255, 255));
				// we count our black pixels in a binary image
				if ( getPixel(image, x, y) != 0){
					pixelsY[y]++;
					pixelsX[x]++;
				}	
			}
		}

		for(int i =0; i<pixelsY.length; i++){
			if(pixelsY[i] != 0 && startEndIndex[0] == -1) {
				startEndIndex[0] = i;
				listY.add(i);
			} else if(pixelsY[i] == 0 && startEndIndex[0] != -1){
				startEndIndex[1] = i;
				listY.add(i);
				startEndIndex[0] = -1;
			}
		}
		startEndIndex[0] =-1;
		for(int i=0; i<pixelsX.length; i++){
			if(pixelsX[i] != 0 && startEndIndex[0] == -1) {
				startEndIndex[0] = i;
				listX.add(i);
			} else if(pixelsX[i] == 0 && startEndIndex[0] != -1){
				startEndIndex[1] = i;
				listX.add(i);
				startEndIndex[0] = -1;
			}
		}
		System.out.println("Y axis:");
		Iterator<Integer> it = listY.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println("--------------------------");
		System.out.println("X axis:");
		Iterator<Integer> it2 = listX.iterator();
		while(it2.hasNext()){
			System.out.println(it2.next());
		}
		System.out.println("-----------------------");
		int i=1;
		boolean dup;
		for(int k=0; k<listY.size(); k=k+2){
			System.out.println("----------------k: " + k);
			int cpt=0;
			dup= false;
			System.out.println("cpt= " + cpt);
			for(int l=0; l<listX.size(); l=l+2){
				System.out.println("----------l: " + l);
				for(int x=listX.get(l); x<listX.get(l+1); x++){
					//System.out.println("-----x: " + x);
					for(int y=listY.get(k); y<listY.get(k+1); y++){
						//System.out.println("y: " + y);
						if(getPixel(image, x, y) != 0){
							cpt++;
							//System.out.println("in if cpt= " +  cpt);
							//System.out.println("----------" + listY.get(k+1) +" " + listX.get(l+1) );
							if(x==listX.get(l+1)-1){
									if((x ==listX.get(0) || x == listX.get(1)-1) && !dup){ 
										System.out.println("question " + (k+i) + " case A cochŽ!");
										i--;
										dup = true;
									}
									else if((x == listX.get(2) || x == listX.get(3)-1) && !dup){
										System.out.println("question " + (k+i) + " case B cochŽ!");
										i--;
										dup = true;
									}
									else if((x == listX.get(4) || x == listX.get(5)-1) && !dup){
										System.out.println("question " + (k+i) + " case C cochŽ!");
										i--;
										dup = true;
									}
									else if((x == listX.get(6) || x == listX.get(7)-1) && !dup){
										System.out.println("question " + (k+i) + " case D cochŽ!");
										i--;
										dup = true;
									}
									else if((x == listX.get(8) || x == listX.get(9)-1) && !dup){
										System.out.println("question " + (k+i) + " case E cochŽ!");
										i--;
										dup = true;
									}
									else if(!dup){
										System.out.println("je sais pas enncore!");
										System.out.println("i: " + i);
										i--;
									}
							}
							output.setRGB(x, y, mixColor(0, 0, 0));
						}
					}
				}
			}
		}
		return output;
	}

}
