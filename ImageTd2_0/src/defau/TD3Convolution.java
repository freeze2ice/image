package defau;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TD3Convolution extends JPanel {


	public TD3Convolution() {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());

		BufferedImage bi = null;
		BufferedImage bi2 = null;

		try {
			bi = ImageIO.read(this.getClass().getResource("img2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bi2 = convolve33(bi);

		ImageIcon ii = new ImageIcon(bi2);
		JLabel jl = new JLabel();
		jl.setIcon(ii);
		this.add(jl, BorderLayout.NORTH);

	}


	private static BufferedImage convolveMini(BufferedImage bi){

		float[] matrix = {0, -1, 0, -1, 5, -1, 0, -1, 0 };
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));

		BufferedImage bi2 =  new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());		
		bi2 = op.filter(bi, bi2);
		return bi2;
	}


	private static BufferedImage convolve33(BufferedImage bi){

		float[] matrix = {1, 1 ,1 ,1 ,1 ,1 ,1 ,1, 1, 1};
		float[] matrice = {0, -1, 0, -1, 5, -1, 0, -1, 0 };
		float[] matrix2 = {-1, -1, -1, -1, -9, -1, -1, -1, -1};

		int width = bi.getWidth();
		int height = bi.getHeight();
		int newPixel=0;
		BufferedImage avg_gray = new BufferedImage(width, height, bi.getType());
		
		for(int i =0; i<width; i++){
			for(int j = 0; j<height; j++){
				if((i>0 && j>0) && (i<width-1 && j<height-1))
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i-1, j-1)*matrix[0]) + (bi.getRGB(i-1, j)*matrix[1]) + (bi.getRGB(i-1, j+1)*matrix[2]) + (bi.getRGB(i, j-1)*matrix[3]) + (bi.getRGB(i, j+1)*matrix[5]) + (bi.getRGB(i+1, j-1)*matrix[6]) + (bi.getRGB(i+1, j)*matrix[7]) + (bi.getRGB(i+1, j+1)*matrix[8]));
					//System.out.println("newpixel after= " + newPixel);
				if(i==0 && j==0)
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i, j+1)*matrix[5]) + (bi.getRGB(i+1, j)*matrix[7]) + (bi.getRGB(i+1, j+1)*matrix[8]));
				
				if(i==0 && j==height-1)
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i, j-1)*matrix[3]) + (bi.getRGB(i+1, j-1)*matrix[6]) + (bi.getRGB(i+1, j)*matrix[7]));
				
				if(i==width-1 && j==0)
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i-1, j)*matrix[1]) + (bi.getRGB(i-1, j+1)*matrix[2]) + (bi.getRGB(i, j+1)*matrix[5]));
				
				if(i==width-1 && j==height-1)
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i-1, j-1)*matrix[0]) + (bi.getRGB(i-1, j)*matrix[1]) + (bi.getRGB(i, j-1)*matrix[3]));
				
				if(i>0 && i<width-1 && j==0)
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i-1, j)*matrix[1]) + (bi.getRGB(i-1, j+1)*matrix[2]) + (bi.getRGB(i, j+1)*matrix[5]) + (bi.getRGB(i+1, j)*matrix[7]) + (bi.getRGB(i+1, j+1)*matrix[8]));
				
				if(i>0 && i<width-1 && j==height-1)
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i-1, j-1)*matrix[0]) + (bi.getRGB(i-1, j)*matrix[1]) + (bi.getRGB(i, j-1)*matrix[3]) + (bi.getRGB(i+1, j-1)*matrix[6]) + (bi.getRGB(i+1, j)*matrix[7]));
				
				if(i==0 && j>0 && j<height-1)
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i, j-1)*matrix[3]) + (bi.getRGB(i, j+1)*matrix[5]) + (bi.getRGB(i+1, j-1)*matrix[6]) + (bi.getRGB(i+1, j)*matrix[7]) + (bi.getRGB(i+1, j+1)*matrix[8]));

				if(i==width-1 && j>0 && j<height-1)
					newPixel = (int) ((bi.getRGB(i, j)*matrix[4]) + (bi.getRGB(i-1, j-1)*matrix[0]) + (bi.getRGB(i-1, j)*matrix[1]) + (bi.getRGB(i-1, j+1)*matrix[2]) + (bi.getRGB(i, j-1)*matrix[3]) + (bi.getRGB(i, j+1)*matrix[5]));

				
				avg_gray.setRGB(i, j, newPixel);
				
			}
		}
		
		return avg_gray;
	}
	
}
