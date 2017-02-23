package hr.fer.zemris.java.tecaj.hw07.crypto;

import static org.junit.Assert.*;

import java.util.Comparator;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

public class hextobytesTests {

	@Test
	public void test4letters() {
		byte[] letters = DatatypeConverter.parseHexBinary("ffff");

		byte[] lettersHexToByte = Converter.hextobyte("ffff");

		assertTrue(new Comparator<byte[]>() {

			@Override
			public int compare(byte[] o1, byte[] o2) {
				if (o1.length != o2.length) {
					return -1;
				}
				for (int i = 0; i < o1.length; ++i) {
					if (o1[i] != o2[i]) {
						return -1;
					}
				}
				return 0;
			}
		}.compare(letters, lettersHexToByte) == 0);

	}

	@Test
	public void test32hexDigits() {
		byte[] letters = DatatypeConverter.parseHexBinary("a52217e3ee213e");
		byte[] lettersHexToByte = Converter.hextobyte("a52217e3ee213e");

		assertTrue(new Comparator<byte[]>() {

			@Override
			public int compare(byte[] o1, byte[] o2) {
				if (o1.length != o2.length) {
					return -1;
				}
				for (int i = 0; i < o1.length; ++i) {
					if (o1[i] != o2[i]) {
						return -1;
					}
				}
				return 0;
			}
		}.compare(letters, lettersHexToByte) == 0);

	}


}
