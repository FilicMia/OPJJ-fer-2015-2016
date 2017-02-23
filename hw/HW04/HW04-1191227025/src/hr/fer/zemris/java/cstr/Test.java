package hr.fer.zemris.java.cstr;

public class Test {

	public static void main(String[] args) {
		String str = "bv ahoawegbav kj";
		str = str.replaceAll("", "KK");
		char[] val = {'h','d','h','d','h','d'};
		String ss =new String(val,1,4);
		System.out.println(ss);
		String nill = null;
		ss.replaceFirst("d", nill);

	}

}
