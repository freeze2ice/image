package tp6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CorrectionImage extends JPanel {

	public CorrectionImage() {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());
		BufferedImage bi = null; 
		
		try {
			bi = ImageIO.read(this.getClass().getResource("foretC.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		findNoise(bi);
		
		JLabel jl = new JLabel();
		jl.setIcon(new ImageIcon(bi));
		this.add(jl, BorderLayout.CENTER);
	}
	
	public static void findNoise(BufferedImage bi){
		
		//BufferedImage bi2 = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Color c =null;
		int red=0, green=0, blue=0;
		int pixel[][] = new int[bi.getWidth()][bi.getHeight()];
		int alpha = 0;
		
		for(int x=0; x<bi.getWidth(); x++){
			for(int y=0; y<bi.getHeight(); y++){
				c = new Color(bi.getRGB(x, y));
				red = c.getRed();
				green = c.getGreen();
				blue = c.getBlue();
				alpha = c.getAlpha();
				pixel[x][y] = (red + green + blue)/3;				
			}
		}
		
		int moyenVoisin =0;
		int pixelG =0;
		int pixelD = 0;
		int pixelI = 0;
		int [] median= new int [9];
		for(int x=1; x<bi.getWidth()-1; x++){
			for(int y=1; y<bi.getHeight()-1; y++){
				if(pixel[x][y] == 255){
					System.out.println("noise detected on x= " +x + " y= " + +y );
					//bi.setRGB(x, y, (Color.RED).getRGB());
					//pixel de gauche
					pixelG = pixel[x-2][y];
					System.out.println("pixel de gauche: " +pixelG);
					//pixel de droite
					pixelD = pixel[x+2][y];
					System.out.println("pixel de droit: " + pixelD);
					//pixel de centre
					pixelI = pixel[x][y];
					System.out.println("pixel de centre: " + pixelI);
					moyenVoisin = (pixelG + pixelD)/2;
					System.out.println("moyenvoisin: " + moyenVoisin);
					
					median[0] = pixel[x-1][y-1];
					median[1] = pixel[x-1][y];
					median[2] = pixel[x-1][y+1];
					median[3] = pixel[x][y-1];
					median[4] = pixel[x][y];
					median[5] = pixel[x][y+1];
					median[6] = pixel[x+1][y-1];
					median[7] = pixel[x+1][y];
					median[8] = pixel[x+1][y+1];
					
					Arrays.sort(median);
					
					for(int k=0; k<median.length; k++){
						System.out.println("median[" +k + "]: " + median[k]);
					}
					
					if(pixelG != pixelI && pixelD != pixelI){
						//System.out.println("les pixels voisins sont de differents couleurs!");
						bi.setRGB(x, y, (new Color(pixelD, pixelD, pixelD).getRGB()));
					}
				}
			}
		}
		
		//return bi;
	}
}
