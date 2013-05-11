import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Misc {

	public Misc() {
		// TODO Auto-generated constructor stub
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
	
}
