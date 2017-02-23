package hr.fer.zemris.java.tecaj.p3c3;
///trebam SAMO taj razredi i nigdje drugdje value provider!!!!
//spojiti cemo impl i deklaraciju

public class Storage {
	private double value;
	
	public Storage(double value) {
		super();
		this.value = value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public ValueProvider getValueProvider(){
		//zalim primjerak razreda koji impl sucelje
		//anonimni razred!!! ne moze impl vise sucelje, niti
		//jos i neki razred
		return new ValueProvider() {
			@Override
			public double getValue() {
				return value;
			}
			
			@Override
			public double getSquaredRoot() {
				return value*value;
			}
			
			@Override
			public double getSquared() {
				return Math.sqrt(value);
			}
		};
	}
}
