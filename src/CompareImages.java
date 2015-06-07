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

		ArrayList<ArrayList<String>> answers1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> answers2 = new ArrayList<ArrayList<String>>();

		answers1 = getAnswers(image1, 600, 240, true);
		answers2 = getAnswers(image2, 600, 240, true);

		float note=0;
		int line =0;
		g.drawString("\nImage1 ---------- Image2", 5, 15);
		g.drawLine(5, 20, 200, 20);
		for(int j=0; j<answers1.size(); j++){
			for(int i=1; i<answers1.get(j).size(); i++){
				if(j<answers2.size()){
					if(i<answers2.get(j).size()){
						g.drawString((line+1) +". " + answers1.get(j).get(i) + " ------------ "+(line+1)+ ". " + answers2.get(j).get(i), 20, (line*20+40));
						if(answers1.get(j).get(i).equals(answers2.get(j).get(i)))	note++;
						line ++;
					} 
				}
			}
		}

		if((line--) != ((answers1.get(0).size()-1)*answers1.size())){
			line++;
			line++;
			g.drawString((line--) + " - " + ((answers1.get(0).size()-1)*answers1.size()) +". Pas cochŽ", 20, line*20+40);
		}
		line++;
		g.drawString("***************************", 20, line*20+40);
		line++;
		g.drawString("******* Note= " + (int)(note/((answers1.get(0).size()-1)*answers1.size())*100) + "% *******", 20, line*20+40);
		line++;
		g.drawString("***************************", 20, line*20+40);
		return output;
	}

	public ArrayList<ArrayList<String>> getAnswers(File image, int colorSeuil, int binaryThreshold, boolean secOuverture){
		qcm = new Qcm(image);
		qcm.start(colorSeuil, binaryThreshold, secOuverture);
		ArrayList<ArrayList<String>> answersSeq = new ArrayList<ArrayList<String>>();
		answersSeq = qcm.getAnswersSequence();
		return answersSeq;
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
