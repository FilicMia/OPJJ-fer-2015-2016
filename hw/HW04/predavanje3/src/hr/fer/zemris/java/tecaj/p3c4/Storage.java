package hr.fer.zemris.java.tecaj.p3c4;

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
		return null;
	}
}
