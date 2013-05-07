import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Qcm extends JPanel {

	public Qcm(File image) {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());
		// File file = new File("qcms/qcmImage.jpg");
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedImage bi3;
		BufferedImage bi2;
		//bi2 = grayImage(bi);
		//bi2 = binarizeImage(bi2, 240);
		// bi3= plotHistogram(bi2);

		bi3 = colorFilter(bi, 450);
		bi3 = grayImage(bi3);
		bi3 = binarizeImage(bi3, 250);

		bi3 = ouverture(bi3);

		bi3 = fourConnectivity(bi3);

		ImageIcon icon = new ImageIcon(bi);
		JLabel label = new JLabel();
		label.setIcon(icon);
		this.add(label, BorderLayout.WEST);

		JLabel label2 = new JLabel();
		label2.setIcon(new ImageIcon(bi3));
		this.add(label2, BorderLayout.EAST);

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

	public static void logConsole(Object obj) {
		System.out.println(obj);
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
				// initialze output to white color
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
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

			}
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
		BufferedImage output = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// initialze output to white color
				output.setRGB(x, y,  mixColor(255, 255, 255));
			}
		}
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// we count our black pixels in a binary image
				if ( getPixel(image, x, y) == 0)
					pixels[x]++;
			}
		}
		// we draw the counted black pixels
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = image.getHeight() - 1; y > image.getHeight()
					- pixels[x]; y--) {
				output.setRGB(x, y,  mixColor(0, 0, 0));
			}
		}
		return output;
	}

	public static void point(int x, int y) {
		System.out.println("point: " + x + " " + y);
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

	public static BufferedImage extractObject(BufferedImage image, int diff) {
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		int pixels[] = new int[image.getWidth()];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				output.setRGB(x, y,  mixColor(255, 255, 255));
				// we count our black pixels in a binary image
				if ( getPixel(image, x, y) < 100)
					pixels[x]++;
			}
		}
		int max = pixels[0];
		int indexStart = 0;
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i] > max) {
				max = pixels[i];
				indexStart = i;
			}
		}
		System.out.println("peak: " + max + " index: " + indexStart
				+ " image width: " + image.getWidth());
		int indexEnd = 0;
		for (int i = 0; i < pixels.length; i++) {
			if (max - pixels[i] < diff) {
				indexEnd = i;
				// System.out.print("max: " + pixels[i] + " index: " + i +
				// " | ");
			}
		}
		System.out.println("Found object in x[" + indexStart + ", "
				+ (indexEnd + 1) + "]");
		for (int x = indexStart; x < indexEnd + 1; x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				output.setRGB(x, y, image.getRGB(x, y));
			}
		}
		return output;
	}

	public static BufferedImage extractObjectHoriz(BufferedImage image, int minDiff, int maxDiff) {

		int[] pixels = new int[image.getHeight()];
		BufferedImage output = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				output.setRGB(x, y,  mixColor(255, 255, 255));
				if ( getPixel(image, x, y) == 0)
					pixels[y]++;
			}
		}

		int max = pixels[0];
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i] > max) {
				max = pixels[i];
			}
		}
		int indexStart = 0;
		for (int i = 0; i < pixels.length; i++) {
			if (max - pixels[i] < minDiff) {
				indexStart = i;
				break;
			}
		}
		System.out.println("peak: " + max + ", on index: " + indexStart
				+ ", image height: " + image.getHeight());
		int indexEnd = 0;
		for (int i = 0; i < pixels.length; i++) {
			if (max - pixels[i] < maxDiff) {
				indexEnd = i;
				// System.out.print("peak: " + pixels[i] + " index: " + i +
				// " | ");
			}
		}
		System.out.println("Found object in y[" + indexStart + ", "
				+ (indexEnd + 1) + "]");
		int pixel = 0;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = indexStart; y < indexEnd + 1; y++) {
				pixel =  getPixel(image, x, y);
				output.setRGB(x, y,  mixColor(pixel, pixel, pixel));
			}
		}
		return output;
	}

	public static BufferedImage fourConnectivity(BufferedImage image){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		int region[][] = new int[image.getWidth()][image.getHeight()];
		int pixel = 0;
		int cpt = 0;
		int label = 0;
		String s = "";
		Font font = new Font("Lucida Grande", 0, 8);
		Graphics2D g = output.createGraphics();
		g.setColor(Color.red);
		g.setFont(font);
		for(int x=0; x<image.getWidth(); x++){
			for(int y=0; y<image.getHeight(); y++){
				region[x][y] = 0;
				output.setRGB(x, y, mixColor(255, 255, 255));
			}
		}
		for(int x=1; x<image.getWidth()-1; x++){
			for(int y=1; y<image.getHeight()-1; y++){
				if(getPixel(image, x, y) != 0){
					pixel = getPixel(image, x, y);
					label = region[x][y];
					if(pixel == getPixel(image, x-1, y) && label == region[x-1][y] ){
						System.out.println("we are in the same region");
						region[x][y] = region[x-1][y];
					} 
					else if((pixel == getPixel(image, x-1, y) && pixel == getPixel(image, x, y-1)) 
							&& (label == region[x-1][y] && label == region[x][y-1]) ){
						System.out.println("North and West pixels belong to the same region and must be merged");
						region[x][y] = Math.min(region[x-1][y], region[x][y-1]);
					}
					else if((pixel != getPixel(image, x-1, y) && pixel == getPixel(image, x, y-1)) 
							&& (label != region[x-1][y] && label == region[x][y-1]) ){
						System.out.println("Assign the label of the North pixel to the current pixel");
						region[x][y] = region[x][y-1];
					}
					else if((pixel != getPixel(image, x-1, y) && pixel != getPixel(image, x, y-1))
							&& (label != region[x-1][y] && label != region[x][y-1])){
						System.out.println("Create a new label id and assign it to the current pixel");
						cpt++;
						region[x][y] = cpt;
					}
					System.out.println("region["+x+"]["+y+"]=" + region[x][y]);
					s = new Integer(region[x][y]).toString();
					g.drawString(s, x, y);
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

}
