package defo;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadImage {

	private BufferedImage bi;
	
	public LoadImage() {
		// TODO Auto-generated constructor stub
			loadImage();
	}
	
	public void loadImage(){
		try {
			this.bi = ImageIO.read(this.getClass().getResource("qcm.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
