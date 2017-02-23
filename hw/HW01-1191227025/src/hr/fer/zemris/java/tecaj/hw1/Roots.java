/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

//import java.text.DecimalFormat;

/**
 * The program computes and prints all requested
 * roots of given complex number.
 * The program accepts
 * three command-line arguments: real part of complex number, imaginary part of complex number, and
 * required root to calculate (natural number greater than 1).
 * 
 * @author Mia
 * @version 1.0
 */
public class Roots {

	/**
	 * Method that is called when running the program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		int brojArgumenata = args.length;
		if(brojArgumenata != 3){
			System.err.println("Wrong number of command line arguments.%n");
			System.exit(1);
		}
		
		double real = Double.parseDouble(args[0]);
		double im = Double.parseDouble(args[1]);

		int root = Integer.parseInt(args[2]);
		if(root < 2){
			System.err.println("Invalid 3rd command line argument. It must "
					+ "be greater than one!");
			System.exit(1);
		}
		
		double r = Math.sqrt(real*real+im*im);
		double fi;
		if(real != 0){
			fi = Math.atan(Math.abs(im/real));
			// choose right angle
			if (real > 0 && im < 0) {
					fi = 2 * Math.PI - fi;
				} else {
					if (real < 0 && im > 0) {
						fi = Math.PI - fi;
					} else {
						if (real < 0 && im <= 0) {
							fi = Math.PI + fi;
						}
					}
				}
		}else{
			if (im < 0) {
				fi = (3.0/2.0) * Math.PI;
			} else {
				if ( im > 0) {
					fi = 1.0/2.0 * Math.PI;
				} else {
					fi = 0;
				}
			}
		}
		
		fi /=root;
		r = Math.pow(r, 1 / (double) root);
		
		//DecimalFormat formaterIm = new DecimalFormat(" 0.00000000i;- 0.00000000i");
		//DecimalFormat formaterR = new DecimalFormat(" 0.00000000;- 0.00000000");
		
		System.out.printf("You requested calculation of %d. roots. "
				+ "Solution are:%n",root);
		//for(int i = 0;i < root;i++){
			//System.out.println((i+1)+". "+formaterR.format(r*Math.cos(fi))+
				//	" "+formaterIm.format(r*Math.sin(fi)));
			//fi += (2.0 * Math.PI)/root;
		//}
		
		for(int i = 0;i < root;i++){
			real = r*Math.cos(fi);
			im = r*Math.sin(fi);
			//System.out.printf(" %f %f%n",real,im);
			System.out.println((i+1)+". "+stringR(real,im)+
					" "+stringIm(real,im));
			fi += (2.0 * Math.PI)/root;
		}
		
	}
	
	/**
	 * Supplementary method that creates string representation of
	 * Imaginary part of complex number real+im*i.
	 * @param real real part of complex number; double
	 * @param im imaginary part of complex number; double
	 * @return string representation of imaginary part of complex number real+im*i
	 */
	private static String stringIm(double real,double im){
		String str = "";
		if(im == 0){
			return str;
		}
		if(im > 0 && real != 0){
			str = "+";
		}
		
		str = str.concat(Double.toString(im));
		
		return str.concat("i");
	}
	
	/**
	 * Supplementary method that creates string representation of
	 * real part of complex number real+im*i.
	 * @param real real part of complex number; double
	 * @param im imaginary part of complex number; double
	 * @return string representation of real part of omplex number real+im*i
	*/
	private static String stringR(double real,double im){
		String str = "";
		if(real == 0 ){
			if(im == 0){
				return Double.toString(real);
			} else {
				return str;
			}
		}
		
		return str.concat(Double.toString(real));
	}
}
