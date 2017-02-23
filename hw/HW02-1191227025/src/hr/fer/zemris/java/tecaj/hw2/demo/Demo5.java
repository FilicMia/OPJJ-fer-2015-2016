package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

public class Demo5 {

	public static void main(String[] args) {
		String s = "2.5-3i";
		
		ComplexNumber c1 = new ComplexNumber(2, 3);
		System.out.println("Constructor whit param (2,3): "+c1);
		ComplexNumber c2 = ComplexNumber.parse(s);
		System.out.println("Also we what to be able to parse"
				+ "complex number from a string "+s+": "+c2);
		ComplexNumber cReal = ComplexNumber.fromReal(8);
		System.out.println("From real = 8 we get: "+cReal);
		ComplexNumber cImag = ComplexNumber.fromImaginary(8);
		System.out.println("From imaginary = 8 we get: "+cImag);
		ComplexNumber fromMagnitudeAndAngle = ComplexNumber.fromMagnitudeAndAngle(2,Math.PI);
		System.out.println("We can create complex number"
				+ "from magnitude and angle: 2 and Pi: "+fromMagnitudeAndAngle);
		
		System.out.println("I have number "+fromMagnitudeAndAngle+"?="+fromMagnitudeAndAngle.getReal()+" "+fromMagnitudeAndAngle.getImaginary());
		System.out.println("Its magnitude is "+fromMagnitudeAndAngle.getMagnitude());
		System.out.println("Its angle is "+fromMagnitudeAndAngle.getAngle());
		System.out.println("---------------------------------");
		System.out.println("Lets try out calculations: ");
		int n = 6;
		System.out.println(n+"th power of "+c1+" should be: 2.035 × 10^3 -8.28 × 10^2 i");
		System.out.println("but it is: "+c1.power(n)+" with magnitude(2197) "+c1.power(n).getMagnitude());
		System.out.println("add: "+c1+" + "+c2+" = "+c1.add(c2));
		
		ComplexNumber result = ComplexNumber.parse("18-6i").div(ComplexNumber.parse("1.920+73.9012i"));//pukne
		System.out.println(ComplexNumber.parse("1.920+73.9012i"));
		System.out.println("Wolfram sid that it is -0.7481-0.245512 : "+result);
		
		System.out.println("pčd one c3 --------------------------");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		System.out.println(c3);
		
		System.out.println(ComplexNumber.parse(""));//krese se :D
		/*stringI = im == 0.0 ? "" : Double.toString(im);
		stringR = real == 0.0 && !stringI.equals("") ? "" : Double.toString(real);
		String operator = im > 0.0 && real != 0.0 ? "+" : "";
		System.out.println((i+1)+". "+stringR+" " + operator + " " +stringI;*/


	}

}
