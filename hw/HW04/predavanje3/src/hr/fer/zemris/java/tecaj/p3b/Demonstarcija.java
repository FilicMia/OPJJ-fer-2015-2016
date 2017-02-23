package hr.fer.zemris.java.tecaj.p3b;

import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Demonstarcija {

	public static void main(String[] args) {
		GeometrijskiLik[] likovi = new GeometrijskiLik[] {
				new Pravokutnik(0, 0, 50, 50),
				new Pravokutnik(50, 50, 25, 25) };
		Slika slika = new Slika(100, 100);

		for (GeometrijskiLik lik : likovi) {
			renderirajSadrziocTocaka(slika, lik);
		}

		PrikaznikSlike.prikaziSliku(slika, 4);

	}

	public static Slika renderirajSadrziocTocaka(Slika slika,
			SadrziocTocaka T) {
		for (int visina = 0; visina < slika.getVisina(); ++visina) {
			for (int sirina = 0; sirina < slika.getSirina(); ++sirina) {
				if (T.sadrziTocku(sirina, visina)) {
					slika.upaliTocku(sirina, visina);
				}
			}
		}
		return slika;
	}

}
