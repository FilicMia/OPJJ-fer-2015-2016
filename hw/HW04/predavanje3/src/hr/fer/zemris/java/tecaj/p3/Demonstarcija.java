package hr.fer.zemris.java.tecaj.p3;

import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Demonstarcija {

	public static void main(String[] args) {
		GeometrijskiLik[] likovi = new GeometrijskiLik[]{
				new Pravokutnik(0,0,50,50),
				new Pravokutnik(-1,-1,25,25)
				};
		 Slika slika = new Slika(100,100);
		 
		 for(GeometrijskiLik lik : likovi){
			 lik.popuniLik(slika);
		 }
		 
		 PrikaznikSlike.prikaziSliku(slika,4);

	}

}
