package hr.fer.zemris.java.tecaj.p3c4;

public class Demonstracija {
	public static void main(String[] args) {
		Storage storage = new Storage(10);
		ValueProvider provider = storage.getValueProvider();
		
		Storage storage2 = new Storage(10);
		ValueProvider provider2 = storage2.getValueProvider();
		
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
