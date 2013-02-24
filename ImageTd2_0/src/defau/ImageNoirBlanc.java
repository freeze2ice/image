package defau;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImageNoirBlanc extends JPanel {
	


	public ImageNoirBlanc(){
		super(new BorderLayout());
		
		BufferedImage bi = null;
		//ColorConvertOp cco= null;
		try {
			bi = ImageIO.read(this.getClass().getResource("img.jpg"));
			//cco = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
			//cco.filter(bi, bi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bi= avg(bi);

		/*
		try {
			ImageIO.write(bi,"jpg", new File("img2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		ImageIcon ii = new ImageIcon(bi);
		JLabel jl = new JLabel();
		jl.setIcon(ii);
		this.add(jl, BorderLayout.NORTH);
	}

	public static BufferedImage avg(BufferedImage bi){
		int alpha, red, green, blue;
		int newPixel;
		int height = bi.getHeight();
		int width = bi.getWidth();

		BufferedImage avg_gray = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		int[] avgLUT = new int[766];

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
				newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
				//System.out.println(red +" "+green+ " "+blue+ " " + alpha + " " + newPixel);

				avg_gray.setRGB(i, j, newPixel);
			}
		}
		return avg_gray;
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
