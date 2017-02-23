package hr.fer.zemris.java.tecaj.hw2;

import java.text.DecimalFormat;

/**
 * Class represents an unmodifiable complex number.
 * 
 * @author Mia Filić
 *
 */
public class ComplexNumber {
	
	/** Real part of complex number of rectangular form.*/
	private double real;
	/** Imaginary part of complex number of rectangular form.*/
	private double imaginary;
	
	/**
	 * Constructor.
	 * 
	 * @param real real part of complex number; double
	 * @param imaginary imaginary part of complex number; double
	 */
	public ComplexNumber(double real,double imaginary){
		this.real = real;
		this.imaginary = imaginary;
		/*checks if it is small enough to be considered zero*/
		if(Math.abs(this.imaginary) < 2e-15){
			this.imaginary = 0.;
		}
		if(Math.abs(this.real) < 2e-15){
			this.real = 0.;
		}
	}
	
	/**
	 * Public static method that creates and returns new instance of
	 * ComplexNumber. The imaginary part of number of rectangular 
	 * form is considered zero.
	 * 
	 * @param real real part of complex number; double
	 * @return newly created instance of ComplexNumber.
	 */
	public static ComplexNumber fromReal(double real){
		
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Public static method that creates and returns new instance of
	 * ComplexNumber. The real part of number of rectangular form is
	 * considered zero.
	 * 
	 * @param imaginary imaginary part of complex number; double
	 * @return newly created instance of ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary){
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Public static method that creates and returns new instance of
	 * ComplexNumber c which polar representation is (magnitude,angle).
	 * 
	 * @param magnitude of c in polar representation
	 * @param angle of c in polar representation
	 * @return c; ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,double angle){
		return new ComplexNumber(magnitude*Math.cos(angle),magnitude*Math.sin(angle));
	}

	/**
	 * Method that parses string to create new instance of
	 * ComplexNumber. Accepts strings such as: "3.51", "-3.17", "-2.71i", "i", "1",
	 * "-2.71-3.15i" but not "7+7","i+36","i+i","hsd","". 
	 * 
	 * @param s sting representing complex number; string
	 * @return complex number represented by string s; ComplexNUmber
	 */
	public static ComplexNumber parse(String s){
		double real = 0;
		double imaginary = 0;
		
		String string = s.replaceAll("[-]{1}[ ]*"," -" );
		string = string.replaceAll("[+]{1}[ ]*"," +" );
		String[] rectangularForm = string.trim().split("[ ]+");
		int size = rectangularForm.length;
		
		if(size <= 0 || size > 2){
			throw new IllegalArgumentException("You have"
					+ "passed wrong argument "+s);
		}
		if(size == 2){
			if(rectangularForm[0].endsWith("i") || !rectangularForm[1].endsWith("i")){
				throw new IllegalArgumentException("You have"
						+ "passed wrong argument "+s);
			}
		}
		
		for (String string2 : rectangularForm) {
			if(string2.matches("^[+-]?[0-9]*[.]*[0-9]*[i]{1}$")){
				string2 = string2.replace("i", "");
				if(string2.matches("^[+-]?$")){
					imaginary = Double.parseDouble(string2+1);
				} else {
					imaginary = Double.parseDouble(string2);
				}
				break;
			} else {
				if(string2.matches("^[+-]?[0-9]+[.]*[0-9]*$")){
					real = Double.parseDouble(string2);
				} else {
					throw new IllegalArgumentException("You have"
							+ " passed wrong argument: "+s);
				}
			}
		}
		
		return new ComplexNumber(real, imaginary);
		
	}
	
	/**
	 * PUblic method that returns the real part of complex number of rectangular form
	 * 
	 * @return real part of complex number; double
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Public method that returns the imaginary part of complex number of rectangular form
	 * 
	 * @return imaginary part of complex number; double
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Public method that returns the magnitude of 
	 * complex number of polar form.
	 * 
	 * @return magnitude; double
	 */
	public double getMagnitude(){
		return Math.hypot(real, imaginary);
	}
	
	/**
	 * Public method that returns the angle of 
	 * complex number of polar form.
	 * Angle is between 0 an 2*PI.
	 * 
	 * @return angle; double
	 */
	public double getAngle(){
		double angle = Math.atan2(imaginary, real);
		if(angle < 0){
			angle += 2*Math.PI;
		}
		return angle;
	}
	
	/**
	 * Public method that calculates addition of two complex
	 * numbers. The instance of complex number on that the
	 * addition is called is added to complex number passed
	 * as argument of the function.
	 * 
	 * @param c complex number to be added
	 * @return result; ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c){
		return new ComplexNumber(this.real+c.getReal(),this.imaginary+c.getImaginary());
	}
	
	/**
	 * Public method that calculates subtraction of two complex
	 * numbers. The instance of complex number on that the
	 * subtraction is called is subtracted by complex number that is passed
	 * as argument of the function.
	 * 
	 * @param c complex number, subtrahend
	 * @return result; ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c){
		return new ComplexNumber(this.real-c.getReal(),this.imaginary-c.getImaginary());
	}
	
	/**
	 * Public method that calculate division of two complex
	 * numbers. The instance of complex number on that the
	 * addition is called is divided by complex number passed
	 * as argument of the function.
	 * 
	 * @param c complex number, divisor
	 * @return result; ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c){
		ComplexNumber conugate = new ComplexNumber(c.getReal(),-c.getImaginary());
		ComplexNumber divisor = c.mul(conugate);
		double div = divisor.getReal();
		if(div == 0){
			throw new ArithmeticException("Cannot divide by 0!");
		}
		ComplexNumber dividend = this.mul(conugate);
		
		return new ComplexNumber(dividend.getReal()/div,dividend.getImaginary()/div);
	}
	
	/**
	 * Public method that calculate multiplication of two complex
	 * numbers. The instance of complex number on that the
	 * addition is called is multiple by complex number passed
	 * as argument of the function.
	 * 
	 * @param c complex number to be multiple with
	 * @return result; ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c){
		double real = this.real*c.getReal()-this.imaginary*c.getImaginary();
		double im = this.real*c.getImaginary()+this.imaginary*c.getReal();
		
		return new ComplexNumber(real,im);
	}
	
	/**
	 * Public method that creates string representation of
	 * complex number.
	 * 
	 * @return string representation; String
	 */
	@Override
	public String toString(){
		DecimalFormat formaterIm = new DecimalFormat(" 0.0#######i; - 0.0#######i");
		DecimalFormat formaterR = new DecimalFormat(" 0.0#######; - 0.0#######");
		String imaginary = this.imaginary == 0.0 ? "" : formaterIm.format(this.imaginary);
		String real = this.real == 0.0 && !imaginary.equals("") ? "" : formaterR.format(this.real);
		String operator = this.imaginary > 0.0 && this.real != 0.0 ? " +" :"";
		
		return real+operator+imaginary;
	}
	
	/**
	 * Public method that calculates n-th power of complex number.
	 * n is passed as function argument.
	 * 
	 * @param n power of complex number that should
	 * 				 be calculated; int
	 * @return result; ComplexNumber
	 */
	public ComplexNumber power(int n){
		if(n < 0){
			throw new IllegalArgumentException("n should be greater than zero: "+n);
		}
		double angle = this.getAngle()*n;
		double magnitude = Math.pow(this.getMagnitude(),n);
		return fromMagnitudeAndAngle(magnitude, angle);
	}
	
	/**
	 * Public method that calculates n-th roots of complex number.
	 * n is passed as function argument.
	 * 
	 * @param n which roots of complex number should be calculated; int
	 * @return n-dim field of n-th roots; ComplexNumber[]
	 */
	public ComplexNumber[] root(int n){
		double r = getMagnitude();
		double fi = getAngle();
		double imaginary, real;
		ComplexNumber[] roots = new ComplexNumber[n];
		
		fi /= n;
		r = Math.pow(r, 1 / (double) n);
		
		for(int i = 0;i < n;i++){
			real = r*Math.cos(fi);
			imaginary = r*Math.sin(fi);
			roots[i] = new ComplexNumber(real, imaginary);
			fi += (2.0 * Math.PI)/(double)n;
		}
		
		return roots;
	}
}
