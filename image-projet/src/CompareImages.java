import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CompareImages extends JPanel {
	
	public CompareImages(File image1, File image2) {
		super(new BorderLayout());
		
		BufferedImage bi = null;
		bi = printToScreen(toString(calculateNote(image1, image2)));
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(bi));
		this.add(label, BorderLayout.NORTH);
	}
	
	public static long calculateNote(File image1, File image2){
		
		Qcm qcm = new Qcm(image1);
		Qcm2 qcm2 = new Qcm2(image2);
		
		ArrayList<String> answers1 = new ArrayList<String>();
		ArrayList<String> answers2 = new ArrayList<String>();
		
		answers1 = qcm.getAnswers();
		answers2 = qcm2.getAnswers();
		long note=0;
		for(int j=1; j<answers2.size(); j++){
			if(answers2.get(j).equals(answers1.get(j))){
				note++;
			}
		}
		System.out.println("******************");
		System.out.println("**** note= " + note + " ****");
		System.out.println("******************");
		return note;
	}
	
	public String toString(long note){
		StringBuffer sb = new StringBuffer();
		return sb.append(note).toString();
	}
	
	public static BufferedImage printToScreen(String s){
		BufferedImage output = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		for(int x=0; x<output.getWidth(); x++){
			for(int y=0; y<output.getHeight(); y++){
				output.setRGB(x, y, Color.WHITE.getRGB());
			}
		}
		Graphics2D g = output.createGraphics();
		g.drawString(s, 5, 5);
		return output;
	}

}
