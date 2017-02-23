package hr.fer.zemris.java.tecaj.p3c2;

public class Demonstracija {
	public static void main(String[] args) {
		Storage storage = new Storage(10);
		ValueProvider provider = storage.getValueProvider();
		
		Storage storage2 = new Storage(2);
		ValueProvider provider2 = storage2.getValueProvider();
		
		/*kontekst je storage2- poput poziva clanske metode
		 * storage2 je poslan kao skriveni argument */
		ValueProvider vp = storage2.new ValueProviderImpl();
		
		ispisi(vp);
		ispisi(provider);
		ispisi(provider2);
		
		storage.setValue(11);
		ispisi(provider);
		
		storage.setValue(125);
		ispisi(provider);
	}

	private static void ispisi(ValueProvider provider) {
		System.out.printf("value = %f, value = %f, korijen = %f%n",
				provider.getValue(),provider.getSquared(),provider.getSquaredRoot());
		
	}
}
