package hr.fer.zemris.java.tecaj.p3;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public abstract class GeometrijskiLik {

	//rezerviraj mjesto za takvu metodu
	public abstract boolean sadrziTocku(int x,int y);

	public Slika popuniLik(Slika slika) {
		for (int visina = 0; visina < slika.getVisina(); ++visina) {
			for (int sirina = 0; sirina < slika.getSirina(); ++sirina) {
				if(this.sadrziTocku(sirina,visina)){
					slika.upaliTocku(sirina, visina);
				}
			}
		}
		return slika;
	}
}
