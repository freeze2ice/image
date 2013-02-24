package defau;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageBinaire extends JPanel{

	public ImageBinaire() {
		// TODO Auto-generated constructor stub

		BufferedImage bi = null;
		try {
			bi = ImageIO.read(this.getClass().getResource("img.jpg"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bi= avg(bi);

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


				if(newPixel < 127)
					avg_gray.setRGB(i, j, ImageBinaire.mixColor(0, 0, 0));
				else avg_gray.setRGB(i, j, ImageBinaire.mixColor(255, 255, 255));
			}}

		return avg_gray;
	}

	private static int mixColor(int red, int green, int blue){
		return red<<16|green<<8|blue;
	}
}
