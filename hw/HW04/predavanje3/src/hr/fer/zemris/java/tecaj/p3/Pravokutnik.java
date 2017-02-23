package hr.fer.zemris.java.tecaj.p3;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Pravokutnik extends GeometrijskiLik {

	private int x;
	private int y;
	private int visina;
	private int sirina;

	public Pravokutnik(int x, int y, int visina, int sirina) {
		super();
		this.x = x;
		this.y = y;
		this.visina = visina;
		this.sirina = sirina;
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		if (x < this.x)
			return false;
		if (y < this.y)
			return false;
		if (x >= this.x + this.sirina)
			return false;
		if (y >= this.y + this.visina)
			return false;
		return true;
	}

	@Override
	public Slika popuniLik(Slika slika) {

		for (int i = this.x; i < this.x + this.sirina; ++i)
			for (int j = this.y; j < this.y + this.visina; ++j) {
				slika.upaliTocku(i, j);
			}
		return slika;
	}
}
