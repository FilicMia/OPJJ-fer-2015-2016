package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program that creates determinate list, prints , orders and again prints it
 * (on standard output).
 * 
 * @author Mia
 *
 */
public class ProgramListe {
	
/**
 * Static class that represents an element of an singly linked list.
 * It holds 1 value of type String.
 * @author Mia
 *
 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}

	/**
	 * Method that is called when running the program.
	 * 
	 * @param args
	 *            command line arguments
	 */	
	public static void main(String[] args) {
		CvorListe cvor = null;
		
		cvor = ubaci(cvor,"Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci (cvor, "Ivana");
	
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
		}
	
	
	/**
	 * Method calculate the length of the singly linked list.
	 * 
	 * @param cvor
	 *            first element of the list; CvorListe
	 * @return integer value that corresponds the size of the list; integer
	 */	
	private static int velicinaListe(CvorListe cvor) {
		int size = 0;
			
		for(CvorListe iter = cvor; iter != null ; iter = iter.sljedeci){
				size++;
		}
			
		return size;
	}
	
	
	/**
	 * Method that adds new element to the singly linked list with value podatak = podatak.
	 * It adds the element to the end of the list.
	 * 
	 * @param prvi
	 *            first element of the list; CvorListe
	 * @param podatak
	 *            podatak value of the new element that will be added to the list; String
	 * @return first element of the list with added element; CvorListe
	 */
	
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		if(prvi == null){
			prvi = new CvorListe();
			prvi.podatak = podatak;
			return prvi;
		}
		
		//we can assume that it has at least one element
		CvorListe iter = prvi;
		while(iter.sljedeci != null){
			 iter = iter.sljedeci;
		}
		
		iter.sljedeci = new CvorListe();
		iter.sljedeci.podatak=podatak;
		return prvi;
	}
	
	/**
	 * Method prints the singly linked list on standard output.
	 * 
	 * @param cvor
	 *            first element of the list; CvorListe
	 */
	private static void ispisiListu(CvorListe cvor) {
		int i = 1;
		for(CvorListe iter = cvor; iter != null ; iter = iter.sljedeci){
			System.out.printf("Podatak %d. : %s%n",(i++),iter.podatak);
		}
	}
	
	
	/**
	 * Method sorts given singly linked list.
	 * 
	 * @param cvor
	 *            first element of the list; CvorListe
	 * @return first element of sorted list; CvorListe
	 */
	static CvorListe sortirajListu(CvorListe cvor) {
		int vel = velicinaListe(cvor);
		if(vel < 2){
			return cvor;
		}
		
		boolean sorted = false;
		CvorListe kraj = null,iter;
		
		while(!sorted){
			sorted = true;

			//element of list that is before the first one. 
			//It moves trough list alongside iter(its first one before iter).
			CvorListe before = new CvorListe();
			before.sljedeci = cvor;
			
			for(iter = cvor; iter.sljedeci != kraj; before = before.sljedeci,
					iter = before.sljedeci){

				if( iter.podatak.compareToIgnoreCase(iter.sljedeci.podatak) > 0){
					swap(before,iter);
					sorted = false;
					
					if(before.podatak == null){//first element of the list has changed
						cvor = before.sljedeci;
					}
				}
			}
			kraj = iter;
		}
		
		return cvor;
	}
	
	/**
	 * Supplementary method that swaps 2 successive elements of the singly linked list.
	 * 
	 * @param before instance of ListaCvor Object that have sljedeci value 
	 * 			set to the first element of the two
	 * @param iter instance of ListaCvor Object that is the first element of the two
	 */
	private static void swap(CvorListe before,CvorListe iter) {
		if(iter == null || before == null){
			System.err.println("Nemoguće zamijeniti podatke koji ne postoje!");
			System.exit(1);
		} else {
			before.sljedeci = iter.sljedeci;
			iter.sljedeci = iter.sljedeci.sljedeci;
			before.sljedeci.sljedeci = iter;		
		}
	}
}

