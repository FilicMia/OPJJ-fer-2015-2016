package hr.fer.zemris.java.tecaj.p3d;

public class Demonstracija {

	static interface Function {
		double valueAt(double x);
	}

	static class Kvadriranje implements Function {

		@Override
		public double valueAt(double x) {
			return x * x;
		}

	}

	static class Korjenovanje implements Function {

		@Override
		public double valueAt(double x) {
			return Math.sqrt(x);
		}

	}

	public static void main(String[] args) {
		double[] polje = { 0.0, 3.14, 2.71, 15.0, 25 };
		ispisi(polje, new Function() {

			@Override
			public double valueAt(double broj) {
				return Math.pow(Math.sin(broj) + 4.5, 1.3);
			}

		});

		// od jave8 mozemo i obako ako:
		/*
		 * LAMBDA IZRAZOM :D -Ako impl samo 1 metodu. -
		 */
		ispisi(polje, (double broj) -> {
			return Math.pow(Math.sin(broj) + 4.5, 1.3);
		});

		/*
		 * Jos krace u (lista parametara)
		 */

		ispisi(polje, (broj) -> {
			return Math.pow(Math.sin(broj) + 4.5, 1.3);
		});

		/*
		 * Ako prima samo jedan args
		 */
		ispisi(polje, broj -> {
			return Math.pow(Math.sin(broj) + 4.5, 1.3);
		});

		/*
		 * Ako prima je jednolinijska. Ovo je dekl anonimnog razreda. Svaki put
		 * se stvara objekt!!
		 * 
		 */
		ispisi(polje, broj -> Math.pow(Math.sin(broj) + 4.5, 1.3));

		/* METHOD HANDLE
		 * Ako prima 1 var i jednolinijskaje i koristi samo Math::sin nad
		 * varijablom broj
		 * 
		 */
		ispisi(polje, Math::sin);

	}

	public static void ispisi(double[] polje, Function f) {
		for (double d : polje) {
			double r = f.valueAt(d);
			System.out.printf("f(%f) = %f%n", d, r);
		}
	}

}
