import java.io.File;
import java.util.ArrayList;


public class CompareImages {
	
	public CompareImages(File image1, File image2) {
		
		Qcm qcm = new Qcm(image1);
		Qcm2 qcm2 = new Qcm2(image2);
		
		ArrayList<String> answers1 = new ArrayList<String>();
		ArrayList<String> answers2 = new ArrayList<String>();
		
		answers1 = qcm.getAnswers();
		answers2 = qcm2.getAnswers();
		int note=0;
		for(int j=1; j<answers2.size(); j++){
			if(answers2.get(j).equals(answers1.get(j))){
				note++;
			}
		}
		System.out.println("******************");
		System.out.println("**** note= " + note + " ****");
		System.out.println("******************");
	}

}
