package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;
/**
 * Class represents usage of class ComplexNUmber.
 * @author Mia Filić
 *
 */
public class Demo {

	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("-i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		System.out.println(c3); // 1.6181754193833349-0.06878563085611379i
		System.out.println(c2);

	}

}
