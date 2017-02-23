/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program computes and prints first n prime numbers.
 * The program
 * accepts a single command-line argument: a number n (n>0).
 * 
 * @author Mia
 * @version 1.0
 */
public class PrimeNumbers {

	/**
	 * Method which starts the program.
	 * @param args 
	 * 			width and heigth of the rectangle or nothing
	 */
	public static void main(String[] args) {
		int brojArgumenata = args.length;
		if(brojArgumenata!=1){
			System.err.println("Invalid number of command line arguments.");
			System.exit(1);
		}
		int number = Integer.parseInt(args[0]);
		if(number < 0){
			System.out.println("Unio si negativan broj!");
			System.exit(1);
		}
		
		int i = 1;
		
		System.out.printf("You requested calculation of %d prime numbers. Here they are:%n",number);
		if(number >= 1){
			System.out.printf("%d. 2%n",i++);
		}
		
		for(int candidate = 3;i <= number;candidate += 2){
			if(isPrime(candidate)){
				System.out.printf("%d. %d%n",i++,candidate);
			}
		}
		
	}
	
	/**
	 * Supplementary method that checks if the number is prime or not.
	 * 
	 * @return true if the number is prime, false otherwise; boolean
	 */
	private static boolean isPrime(int number){
		if(number < 2){
			return false;
		}
		
		int root = (int) Math.sqrt(number);
		for(int div = 2;div <= root;div++){
			//if it can be divided by div, its not an prime num
			if(number%div == 0){
				return false;
			}
		}
			
		return true;	
	}

}
