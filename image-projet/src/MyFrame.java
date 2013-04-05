import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MyFrame extends JFrame{
	
	JPanel panel;
	
	public MyFrame() {
		// TODO Auto-generated constructor stub
		super();
			
		this.panel = new Qcm();
		
		JMenuBar myMenuBar = new JMenuBar();
		JMenu mFile = new JMenu("File"); 
		mFile.setBackground(Color.GRAY);
		myMenuBar.add(mFile);
		JMenu load = new JMenu("Load");
		mFile.add(load);
		JMenuItem iExit = new JMenuItem("Exit");
		mFile.add(iExit);
		
		iExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyFrame.this.dispose();
			}
		});
		
		final JMenuItem loadImage = new JMenuItem("Load Image");
		load.add(loadImage);
		
		loadImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//MyFrame.this.setVisible(true);
				MyFrame.this.getContentPane().add(panel, BorderLayout.SOUTH);
				repaint();
			}
		});
		
		
		this.getContentPane().add(myMenuBar, BorderLayout.NORTH);
		this.setLocation(300, 300);
		this.setSize(600, 400);
		this.setVisible(true);
		
	}
	
	public void addPanel(JPanel panel){
		MyFrame.this.getContentPane().add(panel);
	}

}
