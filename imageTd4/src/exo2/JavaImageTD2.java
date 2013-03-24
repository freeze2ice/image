package exo2;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.*;
import java.awt.geom.AffineTransform;

public class JavaImageTD2 extends JFrame {
     
    public int[] histo = new int[256];
   // public imgOutTab = [[]] ;//= new int [640][360];
   // private int c;
    
    public JavaImageTD2(){
          
      JPanel panel = new JPanel(); 
      this.setTitle("image TD2");
      this.setSize(640,360);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      BufferedImage bufferedImage = null;
      try {
                bufferedImage = ImageIO.read(this.getClass().getResource("dessin.jpg"));
      } catch (IOException ex) {
                // Error
      }
      
      initHisto();       
      
      bufferedImage = imageProcessing(bufferedImage);
      
      
      ImageIcon icon = new ImageIcon(bufferedImage); 
      JLabel label = new JLabel(); 
      label.setIcon(icon); 
     // label.addIcon(icon);
      panel.add(label);
      this.getContentPane().add(panel); 
    }
    
    
    
    
    public static void main(String[] args) {
        new JavaImageTD2().setVisible(true);
    }
    
        
    public static void doStuff(){
        
              /*       
         //   int hsorter =  Arrays.sort(h);  
            int w = img.getWidth();
            int h = img.getHeight();
            double wBins = w/256.;
            double hBins = 360.;
            double hBinsIdeal =  hBins;
            double hBinsIdealNormed = hBinsIdeal / w*h;
            //double factorM
            System.out.println(wBins);
            for (int a=0; a<256; ++a) {
               // histo[a] = histo[a];
                //System.out.println(h);
                double hh = (double) (histo[a]) /(w*h) * hBins *hBinsIdealNormed;
                System.out.println(hh);
                System.out.println((hh/(w*h)));
               // g2.fillRect ((int) (a*wBins),0, (int) wBins, (int) (histo[a]/(w*h) * hBins));//histo[a]/(w*h));
              //  g2.fillRect ((int) (a*wBins),0, (int) wBins, (int) (hh) );//histo[a]/(w*h));
                
            }
            
             float[] matrix = {
              0.0f, -1.5f, 0.0f, 
               -1.5f, 7.0f,  -1.5f, 
              0.0f, -1.5f, 0.0f, 
               };
     */
        
                  
      //     BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix) );
        
    }
    
            
            
            
    public void initHisto(){
       
        for (int a =0; a<256; ++a){
            histo[a] = 0;
        }
       
    }
    
 
    // Binarise une image selon un seuil
    // On pourrait generer un tableau de bits bcp plus leger
    public static BufferedImage binarize(BufferedImage img, int seuil){
        
        BufferedImage imgOUT = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);// img.getType());
        
         for (int y = 0; y < img.getHeight(); y++){
            for (int x = 0; x < img.getWidth(); x++) {
                  if( new Color(img.getRGB(x, y)).getRed() > seuil){  // or n import quel autre canal
                    imgOUT.setRGB(x,y,new Color(255,255,255,0).getRGB());
                  }else
                  {
                    imgOUT.setRGB(x,y,new Color(0,0,0,255).getRGB());
                  }
              }
         }
         return imgOUT;
    }
    
    // Renvoie un histo de projection horizontal d'une image
    // Tab de taille la hauteur de l'img
    public static int[] horizontalProjection(BufferedImage img){
        
        int[] histoProj = new int[img.getHeight()];
        
        for (int y = 0; y < img.getHeight(); y++){
            for (int x = 0; x < img.getWidth(); x++) {
                  Color c = new Color(img.getRGB(x, y));
                  //if(y==0)System.out.println(c.getGreen());
                  if( c.getGreen()==  0&& c.getAlpha()==255 )
                    {histoProj[y]++ ; }
              }
         }
        return histoProj;
    }
    
    
    // Effectue rotation d'une image depuis son centre avec angle en parametre
    // ex Math.PI/2
    public static BufferedImage rotateImg(BufferedImage img, double angle){
        
        
        AffineTransform tx = new AffineTransform();
        
        tx.translate(img.getHeight() / 2,img.getWidth() / 2);
        tx.rotate(angle);
        // centrer image  a l origine
        tx.translate(-img.getWidth() / 2,-img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        // Attention l image avec rotation sera une image avec un canal alpha pour 
        // permettre de remplir les zones vides après rotation(background)
        //de blanc et pas noir par defaut en JPEG.
        BufferedImage bIm = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bIm.createGraphics();
        graphics2D.setBackground(new Color(255,255,255,0));
        graphics2D.setColor(new Color(255,255,255,0));
        op.filter(img, bIm);
        
        
        return bIm;
        
    }
    
    
    public static void displayAlpha(BufferedImage img){
        
        for (int x = 0; x < img.getWidth(); x++) {
              for (int y = 0; y < img.getHeight(); y++) {
                    Color c = new Color(img.getRGB(x,y));
                    int alpha = c.getAlpha();
                    if(alpha==254) System.out.println(alpha);
              }
            }      
    }
    
    
    public static void displayHisto(int[] histo){
        
         for (int i = 0; i < histo.length; i++) {
             System.out.println(histo[i]);
         }     
    }
    
    // Cree une image noir et blanc depuis l image en parametres
    public static BufferedImage toBW(BufferedImage img){
        
        BufferedImage imgOUT = new BufferedImage(img.getWidth(), img.getHeight(),BufferedImage.TYPE_INT_ARGB);// img.getType());
        
        for (int x = 0; x < img.getWidth(); x++) {
              for (int y = 0; y < img.getHeight(); y++) {

                  
                    Color c = new Color(img.getRGB(x,y));
                    int red = c.getRed();
                    int green = c.getGreen();
                    int blue = c.getBlue();
                    int med = (red+green+blue)/3;
                    c = new Color(med,med,med,255);
                    imgOUT.setRGB(x,y,c.getRGB());   // To BW
              }
            }
        
        return imgOUT;
    }    
    
    // Renvoie un score de periodicite de l histogramme
    // le max correspond a un histo periodique 
    // typiquement du aux sauts de lignes en projection horizontales
    // Histo de taille la hauteur de l image. 
    // les val sont comprises entre 0 et largeur de l'image
    // basiquement on va tester le nombre de ligne blanche (ou quasi) de 1px de haut
    // suivies par une ligne contenant des px de texte
    public static int getPeriodicity(int[] histo, BufferedImage img){
        
          int p=0;
          for (int i = 0; i<histo.length - 1 ; ++i){
              if (histo[i]<10 && histo[i+1]>20)  // ligne blanche suivi de ligne non blanche
                  p++;
          }
          return p;
    }
    
    // Oriente automatiquement l'image dans la bonne direction
    // En binarisant puis test histo projection horizontal pour 
    // differents angles. 
    // L'histogramme si l'image est horizontal est typique avec des trous régulier
    // On supposera l'image scannee decalee de moins de 90 degres (ici 30) car sinon
    // il peut y avoir 2 orientations possible: droite ou a l'envers totalement.
    // A noter: Il existe des methodes directes.(transformee de hough par ex)
    public static double toGoodOrientation(BufferedImage img){
        
        //BufferedImage newImage = rotateImg(img,Math.PI/2);
        BufferedImage imBW = toBW(img);
        BufferedImage imBin = binarize(imBW, 128);
        int ScorePeriodMax = 0;
        double oriMax = 0;
        
        for (int i = -30; i<30 ; ++i){
           BufferedImage newImage = rotateImg(imBin, i * Math.PI/180);
           int[] histoH = horizontalProjection(newImage);
           int period = getPeriodicity(histoH,newImage);
           if (period > ScorePeriodMax) {ScorePeriodMax = period; oriMax= i*Math.PI/180;}
           //System.out.println("Testing rotation: "+ i * Math.PI/180);
        }
        
        System.out.println("Best rotation for horizontal view: "+oriMax);
        return oriMax;
    }
    
    
    

    
    
    public BufferedImage imageProcessing(BufferedImage img){
        
            Graphics2D g2;
            g2 = img.createGraphics();

         
            BufferedImage imgOut =  null;
              try {
                      imgOut = ImageIO.read(this.getClass().getResource("dessin.jpg"));
            } catch (IOException ex) {
                      // Error
            };
            
           double orimax = toGoodOrientation(img);   
           BufferedImage newImage = rotateImg(img, orimax);
           return newImage;
    }
    
}