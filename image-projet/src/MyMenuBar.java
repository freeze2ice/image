import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MyMenuBar extends JMenuBar {
		
	public MyMenuBar() {
		// TODO Auto-generated constructor stub
		super.setBackground(Color.GRAY);
		
		JMenu mFile = new JMenu("File"); 
		mFile.setBackground(Color.GRAY);
		this.add(mFile);
		JMenu draw = new JMenu("Draw");
		this.add(draw);
		JMenuItem iExit = new JMenuItem("Exit");
		mFile.add(iExit);
		
		final JFileChooser jfc = new JFileChooser();
		iExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
			}
		});
		
		final JMenuItem open = new JMenuItem("Open");
		mFile.add(open);
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jfc.showOpenDialog(getParent());
			}
		});
		
	}

}
