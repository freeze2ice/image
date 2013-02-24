import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
 


public class JavaImageTD2 extends JFrame {
     

    public JavaImageTD2(){
          
      JPanel panel = new JPanel(); 
      this.setTitle("image TD2");
      this.setSize(640,360);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      BufferedImage bufferedImage = null;
      try {
                bufferedImage = ImageIO.read(this.getClass().getResource("img.jpg"));
      } catch (IOException ex) {
                // Error
      }
             
      ImageIcon icon = new ImageIcon(bufferedImage); 
      JLabel label = new JLabel(); 
      label.setIcon(icon); 
      panel.add(label);
      this.getContentPane().add(panel); 
    }
    
    public static void main(String[] args) {
        new JavaImageTD2().setVisible(true);
    }
    
}