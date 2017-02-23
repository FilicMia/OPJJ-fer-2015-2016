package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class represents a command-line application which accepts a
 * single command-line argument: string representation of arithmetic
 * expression which should be evaluated. Expression must be in postfix
 * representation.
 *
 * @author Mia Filić
 *
 */
public class StackDemo {
	
	/**
	 * Method calling when starting the program.
	 * 
	 * @param args
	 * 			command-line arguments
	 */
	public static void main(String[] args) {
		//12 6 + 3 / 12 + = 18
		
		try{
			evaluate(args[0]);
		} catch(java.lang.ArithmeticException e){
   		 	e.printStackTrace();
   		 	System.err.println(e.getMessage()+args[0]);
   		 	System.exit(-1);
   	 	}catch(IllegalArgumentException e){
   	 		System.err.println(e.getMessage()+args[0]);
   	 		System.exit(-2);
   	 	}
	}

	/**
	 * Supplementary method that evaluates arithmetic expression 
	 * given by String input and prints it on the standard output.
	 * Expression must be in postfix representation. 
	 * Allowed symbols +,*,/,-,symbols representing integer numbers. 
	 * Symbols are separated by one (or more) spaces. 
	 * 
	 * Example:
	 * 		"8 4 /" represents 8/4
	 * 		
	 * 		Method prints: "Expression evaluates to 2."
	 * 
	 * 
	 * @param input String representation of arithmetic representation 
	 * 				that should be evaluated
	 */
	private static void evaluate(String input) {
		ObjectStack storage = new ObjectStack();
		String[] expression = input.trim().split("[ ]+");
		
		for (String string : expression) {
			switch (string) {
			case "+":
				add(storage);
				break;
			case "-":
        		 sub(storage);
        		 break;
			case "/":
				divide(storage);
				break;
			case "*":
        		 mul(storage);
        		 break;
			case "%":
        		 reminder(storage);
        		 break;
			default:
				if(!isNumber(string)){
					throw new IllegalArgumentException("Invalid expression: "
							+ input);
				}
				storage.push(new Integer(string));
        	 
			}
		}
		
		if(storage.size() != 1){
			System.err.println("Invalid expression: "
    		 		+ input);
			System.exit(1);
		} else {
			System.out.println("Expression evaluates to "+storage.pop());
		}
		
	}


	/**
	 * Private method that calculates the reminder of
	 * integer division of two last-added elements that are 
	 * currently on the stack. The older-added one is divided by the 
	 * newly-added one.
	 * 
	 * @param storage stack with integer elements
	 */
	private static void reminder(ObjectStack storage) {
		if(storage.size() < 2){
			throw new IllegalArgumentException("Invalid expression: ");
		} else{
			int number2 = (int) storage.pop();
			int number1 = (int) storage.pop();
			storage.push(number1%number2);
		}
		
	}
	
	/**
	 * Private method that multiplies two last-added elements that are 
	 * currently on the stack.
	 * 
	 * @param storage stack with integer elements
	 */
	private static void mul(ObjectStack storage) {
		if(storage.size() < 2){
			throw new IllegalArgumentException("Invalid expression: ");
		} else{
			int number2 = (int) storage.pop();
			int number1 = (int) storage.pop();
			storage.push(number1*number2);
		}
		
	}
	
	/**
	 * Private method that divides two last-added elements that are 
	 * currently on the stack. The older-added one is divided by the 
	 * newly-added one.
	 * 
	 * @param storage stack with integer elements
	 */
	private static void divide(ObjectStack storage) {
		if(storage.size() < 2){
			throw new IllegalArgumentException("Invalid expression: ");
		} else{
			int number2 = (int) storage.pop();
			if(number2 == 0){
				throw new ArithmeticException("Trying to divide by zero!");
			}
			
			int number1 = (int) storage.pop();
			storage.push(number1/number2);
		}
		
	}
	
	/**
	 * Private method that subtracts two last-added elements that are 
	 * currently on the stack. The older-added one is subtracted by the 
	 * newly-added one.
	 * 
	 * @param storage stack with integer elements
	 */
	private static void sub(ObjectStack storage) {
		if(storage.size() < 2){
			throw new IllegalArgumentException("Invalid expression: ");
		} else{
			int number2 = (int) storage.pop();
			int number1 = (int) storage.pop();
			storage.push(number1-number2);
		}
		
	}
	
	/**
	 * Private method that adds two elements that are 
	 * currently on the stack. 
	 * 
	 * @param storage stack with integer elements
	 */
	private static void add(ObjectStack storage){
		if(storage.size() < 2){
			throw new IllegalArgumentException("Invalid expression: ");
		} else{
			int number2 = (int) storage.pop();
			int number1 = (int) storage.pop();
			storage.push(number1+number2);
		}
		
	}

	/**
	 * Private method that checks if an String represent a single integer number
	 * 
	 * @param string String to be checked; String
	 * @return true if the string represents an integer number; boolean
	 */
	private static boolean isNumber(String string) {
		return string.matches("[+-]?\\d+");
	}

}
