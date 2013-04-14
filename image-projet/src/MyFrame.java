import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MyFrame extends JFrame implements ActionListener {
	
	public MyFrame() {
		// TODO Auto-generated constructor stub
		super();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		setJMenuBar(menuBar);
		
		JMenu mFile = new JMenu("File"); 
		menuBar.add(mFile);
		
		JMenu draw = new JMenu("Draw");
		menuBar.add(draw);
		
		mFile.add(createMenuItem("Open"));
		mFile.add(createMenuItem("Exit"));
		
		setLocation(300, 300);
		setSize(600, 400);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser jfc = new JFileChooser();
		File f = null;
		String command = e.getActionCommand();
		if(command.equals("Exit"))
			this.dispose();
		else if(command.equals("Open")){
			jfc.showOpenDialog(this);
			if(jfc.getSelectedFile() != null){
				f = jfc.getSelectedFile();
				JPanel panel = new Qcm(f);
				MyFrame.this.getContentPane().add(panel, BorderLayout.CENTER);
			}
		}
	}
	
	public JMenuItem createMenuItem(String name){
		JMenuItem m = new JMenuItem(name);
		m.addActionListener(this);
		return m;
	}

}
