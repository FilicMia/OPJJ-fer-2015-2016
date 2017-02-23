package hr.fer.zemris.java.tecaj.p3c2;

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
		return new ValueProviderImpl();//ispod haube dobije this
	}
	
	/*public static ValueProvider vratiProvider(){
		return new ValueProviderImpl();//treba mi this,a nemam ju!
	}*/
	
	public class ValueProviderImpl implements ValueProvider{
		
		@Override
		public double getValue() {
			
			return Storage.this.value;//mozemo i napisati samo value...
		}

		@Override
		public double getSquared() {
			
			return value*value;
		}

		@Override
		public double getSquaredRoot() {
		
			return Math.sqrt(value);
		}
		
	}
}
