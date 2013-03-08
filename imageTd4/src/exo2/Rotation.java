package exo2;

public class Rotation {

	public Rotation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * 1) corriger rottation
	 * 2) l'idée est de faire histo projection horizontal et histo projection vertical
	 * classe AffineTransform .rotate() 
	 */

	public static void calculateVarianceCovariance(int[][] pixelTable){
		
		int X=0, Y=0;
		int varianceX=0, varianceY=0;
		int covariance =0;
		
		for(int i=0; i<pixelTable.length; i++){
			for(int j=0; j<pixelTable[0].length; j++){
				if(pixelTable[i][j] != 255) {
					X += i;
					Y += j;
				}
			}
		}
		X /= (int) (pixelTable.length * pixelTable[0].length);
		Y /= (int) (pixelTable.length * pixelTable[0].length);
		
		for(int i=0; i<pixelTable.length; i++){
			for(int j=0; j<pixelTable[0].length; j++){
				varianceX += (int) Math.pow((i - X), 2);
				varianceY += (int) Math.pow((j - Y), 2);
				covariance += ((i-X)*(j-Y));
			}
		}
		
		//showing mat(variance, covariance)
		System.out.println("| " + varianceX + "\t" + covariance + " |");
		System.out.println("| " + covariance + "\t" + varianceY + " |");
		
	}
	
	

}
