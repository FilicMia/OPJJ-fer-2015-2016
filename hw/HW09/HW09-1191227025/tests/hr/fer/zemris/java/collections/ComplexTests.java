package hr.fer.zemris.java.collections;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.collections.ComplexUtil.Complex;
import hr.fer.zemris.java.collections.ComplexUtil.ComplexPolynomial;
import hr.fer.zemris.java.collections.ComplexUtil.ComplexRootedPolynomial;

public class ComplexTests {

	@Test
	public void testToString() {
		Complex one = Complex.ONE;
		Complex da = new Complex(12.5, 55);
		assertEquals("12,5 + 55,0i", da.toString().trim());
	}

	@Test
	public void testMultiply() {
		Complex one = Complex.ONE;
		Complex da = new Complex(12.5, 55);
		assertEquals(da, da.multiply(one));
	}

	@Test
	public void testMultiplyPolynoms() {
		Complex one = Complex.ONE;
		Complex da = new Complex(12.5, 55);
		ComplexPolynomial p = new ComplexPolynomial(da, one);
		assertEquals(p.toString(), p.multiply(new ComplexPolynomial(one)).toString());

	}
	
	@Test
	public void testDividePolynoms() {
		Complex one = Complex.ONE;
		Complex da = new Complex(12.5, 55);
		assertEquals(da.toString(),da.divide(one).toString());

	}

	@Test
	public void testDerivePolynom() {
		Complex one = Complex.ONE;
		Complex da = new Complex(12.5, 55);
		ComplexPolynomial p = new ComplexPolynomial(da, Complex.ZERO, da);
		System.out.println(p);
		ComplexPolynomial derive = new ComplexPolynomial(
				da.multiply(new Complex(2, 0)),Complex.ZERO);
		assertEquals(derive.toString(), p.derive().toString());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testPolynomRootsNullRoot() {
		Complex da = new Complex(12.5, 55);
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(da, null,
				Complex.ZERO, da);

	}
}
