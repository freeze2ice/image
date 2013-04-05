import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MyMenuBar extends JMenuBar {
	
	JPanel panel;
	JFrame frame;

	public MyMenuBar() {
		// TODO Auto-generated constructor stub
		super.setBackground(Color.GRAY);
		
		this.panel = new Qcm();
		this.frame = new MyFrame();
		
		JMenu mFile = new JMenu("File"); 
		mFile.setBackground(Color.GRAY);
		this.add(mFile);
		JMenu load = new JMenu("Load");
		mFile.add(load);
		JMenuItem iExit = new JMenuItem("Exit");
		mFile.add(iExit);
		
		iExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});
		
		final JMenuItem loadImage = new JMenuItem("Load Image");
		load.add(loadImage);
		
		loadImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(true);
				frame.getContentPane().add(panel, BorderLayout.SOUTH);
			}
		});
		
		
		//frame.setSize(400, 200);
		frame.setVisible(true);
	}

}
