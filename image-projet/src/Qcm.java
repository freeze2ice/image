import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

		BufferedImage bi2 = binarizeImage(grayImage(bi), 725);

		ImageIcon icon = new ImageIcon(bi2);
		JLabel label = new JLabel();
		label.setIcon(icon);
		this.add(label);

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
				//Qcm.logConsole(pixel);
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
				pixel = Qcm.getPixel(grayImage, x, y);
				//System.out.println("binarize: ");
				//Qcm.logConsole(pixel);
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

}
