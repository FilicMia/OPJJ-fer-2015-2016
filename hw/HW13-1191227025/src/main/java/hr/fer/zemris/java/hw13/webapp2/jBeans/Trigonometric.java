package hr.fer.zemris.java.hw13.webapp2.jBeans;

/**
 * Class containing and providing the calculation of cosinus and sinus value of
 * private property number.
 * Calculation of each function available at stored point {@code number} 
 * is calculated and stored the first time when the {@link #getCos()} or {@link #getSin()}
 * is called.
 */
public class Trigonometric {
	/** Number which cosinus and sinus can be calculated. */
	private int number;
	/**
	 * Sinus value of {@code number} which is set after first call of function
	 * {@link #getSin()}.
	 */
	private double sin = 2;
	/**
	 * Cosinus value of {@code number} which is set after first call of function
	 * {@link #getCos()}.
	 */
	private double cos = 2;

	/**
	 * Constructing the instance of trigonometric.
	 * 
	 * @param number
	 *            number which sinus and cosinus can be calculated.
	 * 
	 */
	public Trigonometric(int number) {
		this.number = number;
	}

	/**
	 * Returns the number held in this instance of {@link Trigonometric}.
	 * 
	 * @return number held in this instance of {@link Trigonometric}.
	 * 
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sinus value of number {@code number}.
	 * 
	 * @return sinus value of {@code number}
	 */
	public double getSin() {
		if (sin == 2)
			sin = Math.sin(Math.PI / 180 * number);
		return sin;
	}

	/**
	 * Cosinus value of number {@code number}.
	 * 
	 * @return sin sinus value of {@code number}
	 */
	public double getCos() {
		if (cos == 2)
			cos = Math.cos(Math.PI / 180 * number);
		return cos;
	}

}
