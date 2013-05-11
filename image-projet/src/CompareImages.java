import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
		bi = calculateNote(image1, image2);
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(bi));
		this.add(label, BorderLayout.NORTH);
	}
	
	public BufferedImage calculateNote(File image1, File image2){
		BufferedImage output = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = output.createGraphics();
		g.setFont(new Font("lucida", 0, 13));
		g.setColor(Color.BLACK);
		
		ArrayList<String> answers1 = new ArrayList<String>();
		ArrayList<String> answers2 = new ArrayList<String>();
		
		answers1 = getAnswers(image1);
		answers2 = getAnswers(image2);
		
		float note=0;
		g.drawString("\nImage1 ---------- Image2", 5, 15);
		g.drawLine(5, 25, 200, 25);
		for(int j=1; j<answers2.size(); j++){
			g.drawString(j +". " + answers1.get(j) + " ---------- "+j+ ". " + answers2.get(j), 20, (j*30+20));
			if(answers1.get(j).equals(answers2.get(j))){
				note++;
			}
		}
		g.drawString("***************************", 20, 11*30+20);
		g.drawString("******* Note= " + (int)(note/(answers1.size()-1)*100) + "% *******", 20, 12*30+20);
		g.drawString("***************************", 20, 13*30+20);
		return output;
	}
	
	public ArrayList<String> getAnswers(File image){
		Qcm qcm = new Qcm(image);
		ArrayList<String> answers = new ArrayList<String>();
		answers = qcm.getAnswers();
		return answers;
	}

}
