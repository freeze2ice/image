import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		BufferedImage bi4 = null;

		bi2 = colorFilter(bi, 450);
		bi3 = grayImage(bi2);
		bi3 = binarizeImage(bi3, 250);
		bi4 = ouverture(bi3);
		bi4 = ouverture(bi4);
		detectObject(bi4);

		ImageIcon icon = new ImageIcon(bi);
		JLabel label = new JLabel();
		label.setIcon(icon);
		this.add(label, BorderLayout.NORTH);

		JLabel label2 = new JLabel();
		label2.setIcon(new ImageIcon(bi2));
		this.add(label2, BorderLayout.WEST);

		JLabel label3 = new JLabel();
		label3.setIcon(new ImageIcon(bi3));
		this.add(label3, BorderLayout.CENTER);

		JLabel label4 = new JLabel();
		label4.setIcon(new ImageIcon(bi4));
		this.add(label4, BorderLayout.EAST);

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

	public static BufferedImage zoom(BufferedImage image, int zoomLevel){
		int newImageWidth = image.getWidth() * zoomLevel;
		int newImageHeight = image.getHeight() * zoomLevel;
		BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, image.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, newImageWidth , newImageHeight , null);
		g.dispose();
		return resizedImage;
	}

	public static int[] getPixelsX(BufferedImage image){
		int[] pixelsX = new int[image.getWidth()];
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// we count our black pixels in a binary image
				if ( getPixel(image, x, y) != 0){
					pixelsX[x]++;
				}	
			}
		}
		return pixelsX;
	}
	
	public static int[] getPixelsY(BufferedImage image){
		int[] pixelsY = new int[image.getHeight()];
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// we count our black pixels in a binary image
				if ( getPixel(image, x, y) != 0){
					pixelsY[y]++;
				}	
			}
		}
		return pixelsY;
	}
	
	public static ArrayList<Integer> getListX(int[] pixelsX){
		ArrayList<Integer> listX = new ArrayList<Integer>();
		boolean start = false;
		for(int i=0; i<pixelsX.length; i++){
			if(pixelsX[i] != 0 && !start) {
				start =true;
				listX.add(i);
			} else if(pixelsX[i] == 0 && start){
				listX.add(i);
				start = false;
			}
		}
		return listX;
	}
	
	public static ArrayList<Integer> getListY(int[] pixelsY){
		ArrayList<Integer> listY = new ArrayList<Integer>();
		boolean start = false;
		for(int i =0; i<pixelsY.length; i++){
			if(pixelsY[i] != 0 && !start) {
				start =true;
				listY.add(i);
			} else if(pixelsY[i] == 0 && start){
				listY.add(i);
				start=false;
			}
		}
		return listY;
	}
	
	public static void detectObject(BufferedImage image){
		ArrayList<Integer> listY = new ArrayList<Integer>();
		ArrayList<Integer> listX = new ArrayList<Integer>();
		ArrayList<String> answers = new ArrayList<String>();
		listX = getListX(getPixelsX(image));
		listY = getListY(getPixelsY(image));
		answers = findAnswers(image, listX, listY);
		System.out.println("-----------------");
		for(int i=0; i<answers.size(); i++){
			System.out.println(i + ". " +answers.get(i));
		}
	}

	public static ArrayList<String> findAnswers(BufferedImage image, ArrayList<Integer> listX, ArrayList<Integer> listY){
		ArrayList<String> answers = new ArrayList<String>();
		answers.add(0, "");
		int i=1, cpt, casePosition;
		boolean dup;
		for(int k=0; k<listY.size(); k=k+2){
			cpt=0;
			dup= false;
			casePosition = 0;
			for(int l=0; l<listX.size(); l=l+2){
				for(int x=listX.get(l); x<listX.get(l+1); x++){
					for(int y=listY.get(k); y<listY.get(k+1); y++){
						if(getPixel(image, x, y) != 0){
							if(x >= listX.get(l) && x <= listX.get(l+1) ){
								cpt++;
								casePosition = l;
							}
						}
					}
				}
				if(cpt!=0){
					if(casePosition == 0 && !dup){
						System.out.println("question " + (k+i) + " case A cochŽ!");
						answers.add(k+i, "A");
						i--;
						dup = true;
					}
					else if(casePosition == 2 && !dup){
						System.out.println("question " + (k+i) + " case B cochŽ!");
						answers.add(k+i, "B");
						i--;
						dup = true;
					}
					else if(casePosition == 4 && !dup){
						System.out.println("question " + (k+i) + " case C cochŽ!");
						answers.add(k+i, "C");
						i--;
						dup = true;
					}
					else if(casePosition == 6 && !dup){
						System.out.println("question " + (k+i) + " case D cochŽ!");
						answers.add(k+i, "D");
						i--;
						dup = true;
					}
					else if(casePosition == 8 && !dup){
						System.out.println("question " + (k+i) + " case E cochŽ!");
						answers.add(k+i, "E");
						i--;
						dup = true;
					}
					else if(!dup){
						System.out.println("je sais pas enncore!");
						System.out.println("i: " + i);
						i--;
					}
				}
			}
		}
		return answers;
	}

}
