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

	private Qcm qcm;
	
	public CompareImages(File image1, File image2) {
		super(new BorderLayout());
		BufferedImage bi = null;
		bi = calculateNote(image1, image2);
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(bi));
		this.add(label, BorderLayout.NORTH);
	}

	public BufferedImage calculateNote(File image1, File image2){
		BufferedImage output = new BufferedImage(250, 800, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = output.createGraphics();
		g.setFont(new Font("lucida", 0, 13));
		g.setColor(Color.BLACK);
		
		ArrayList<String> answers1 = new ArrayList<String>();
		ArrayList<String> answers2 = new ArrayList<String>();
		
		answers1 = getAnswers(image1, 600, 240, true);
		answers2 = getAnswers(image2, 600, 240, true);

		float note=0;
		int line =0;
		g.drawString("\nImage1 ---------- Image2", 5, 15);
		g.drawLine(5, 20, 200, 20);
		for(int j=1; j<answers1.size(); j++){
			if(j<answers2.size()){
				g.drawString(j +". " + answers1.get(j) + " ------------ "+j+ ". " + answers2.get(j), 20, (j*20+20));
				if(answers1.get(j).equals(answers2.get(j)))	note++;
				line = j;
			} 
		}
		if(line != answers1.size()-1){
			line ++;
			g.drawString((line) + " - " + (answers1.size()-1) +". Pas cochŽ", 20, line*20+20);
		}
		line++;
		g.drawString("***************************", 20, line*20+20);
		line++;
		g.drawString("******* Note= " + (int)(note/(answers1.size()-1)*100) + "% *******", 20, line*20+20);
		line++;
		g.drawString("***************************", 20, line*20+20);
		return output;
	}

	public ArrayList<String> getAnswers(File image, int colorSeuil, int binaryThreshold, boolean secOuverture){
		qcm = new Qcm(image);
		qcm.start(colorSeuil, binaryThreshold, secOuverture);
		ArrayList<String> answers = new ArrayList<String>();
		answers = qcm.getAnswers();
		return answers;
	}
	
	public boolean sameSheets(File image1, File image2){
		ArrayList<Integer> questionsSet1 = new ArrayList<Integer>();
		ArrayList<Integer> questionsSet2 = new ArrayList<Integer>();
		
		qcm = new Qcm(image1);
		qcm.start(600, 240, true);
		questionsSet1 = qcm.getQuestions();
		qcm = new Qcm(image2);
		qcm.start(600, 240, true);
		questionsSet2 = qcm.getQuestions();
		for(int i =0; i<questionsSet1.size(); i++){
			System.out.println(questionsSet1.get(i) + " " + questionsSet2.get(i));
		}
		if(questionsSet1.equals(questionsSet2)) return true;
		return false;
	}
	
	

}
