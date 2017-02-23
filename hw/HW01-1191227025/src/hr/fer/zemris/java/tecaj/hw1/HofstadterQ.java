package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program which calculates i. number from HofstadterQ sequence. I.th number is calculated following formula:
 * a[n] = a[n - a[n - 1]] + a[n - a[n - 2]] ; a[1] = a[2] = 1.
 * i is given as a command line argument.
 * 
 * @author Mia
 * @version 1.0
 */

public class HofstadterQ {

	/**
	 * Method that is called when running the program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments was provided.");
			System.exit(1);
		}
		long i = Integer.parseInt(args[0]);
		
		if(i < 0){
			System.err.println("The argument must be positive. ( > 0)");
			System.exit(2);
		}
		
		long[] all = new long[(int) (i+2)];
		all[1] = all[2] = 1;
		hosfsNumbers(i, all);
		
		System.out.format("You requested calculation of "+i+
				". number of Hofstadter's Q-sequence. The requested "
				+ "number is "+all[(int) i]+".");
		
		
		
	}
	
	/**
	 * Supplementary method that finds i. number of 
	 * the HofstadterQ sequence. It uses bottom-up dynamic programming.
	 * 
	 * @param num natural number greater than zero; long
	 * @param before ling field of first i numbers 
	 * 		of HofstadterQ sequence; natural numbers greater than zero; long[]
	 */
	private static void hosfsNumbers(long i, long[] before){
		if(i <= 2){
			return;
		}
		
	    for(int j = 3;j <= i;++j){
	    	before[(int)j] = before[(int) (j-before[(int)j-1])]+before[(int) (j-before[(int)j-2])];
	    }
	    
	}
	
}
