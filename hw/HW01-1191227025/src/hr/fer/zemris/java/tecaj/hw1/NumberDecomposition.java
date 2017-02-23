package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program calculates and prints the decomposition of number n onto prime numbers.
 * N is passed as command line argument.
 * The program accepts a single command line argument, integer grater than one.
 * 
 * @author Mia
 * @version 1.0
 */
public class NumberDecomposition {
	
	/**
	 * Method which starts the program.
	 * @param args 
	 * 			command line arguments
	 */
	public static void main(String[] args){
		int brojArgumenata = args.length;
		if(brojArgumenata!=1){
			System.err.println("Invalid number of command line arguments.");
			System.exit(1);
		}
		int number = Integer.parseInt(args[0]);
		if(number < 2){
			System.err.println("Invalid command line argument. It should be greater than 1");
			System.exit(1);
		}
		
		int prime = 2,i = 1;
		
		System.out.printf("You requested decomposition of number %d onto prime factors. Here they are:%n",number);
		while(number != 1){
			if(isPrime(number)){
				System.out.printf("%d. %d%n",i++,number);
				break;
			}
			
			if((number%prime) == 0){
				number /= prime;
				System.out.printf("%d. %d%n",i++,prime);
			}else{
				prime = findNextPrime(prime);
			}
		}
	}
	
	/**
	 * Supplementary method which finds the first prime number grater than given number.
	 * 
	 * @param number number after which the first prime number will be found; integer
	 * @return next prime number grater than integer number; integer
	 */
	private static int findNextPrime(int number){
		int start = number+1;
		if(start == 2){
			return start;
		}
		
		if(start%2 == 0){
			start +=1;
		}
		
		for(int candidate = start;;candidate += 2){
			if(isPrime(candidate)){
				return candidate;
			}
		}
		
	}
	
	
	/**
	 * Supplementary method which checks if the number is prime.
	 * 
	 * @param number to be checked; integer
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
