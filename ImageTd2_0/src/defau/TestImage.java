package defau;

import java.awt.BorderLayout;
import java.rmi.server.Operation;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestImage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame jf = new JFrame();
		JFrame jf2 = new JFrame();
		
		JPanel panel = new ImageNoirBlanc();
		JPanel panel2 = new ImageBinaire();
		JPanel panel3 = new TD3Convolution();
		
		jf.getContentPane().add(panel, BorderLayout.NORTH);
		jf.getContentPane().add(panel2, BorderLayout.SOUTH);
		
		jf2.getContentPane().add(panel3);
		
		jf.pack();
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
		
		jf2.pack();
		jf2.setLocationRelativeTo(jf);
		jf2.setDefaultCloseOperation(jf2.EXIT_ON_CLOSE);
		jf2.setVisible(true);

	}

}
