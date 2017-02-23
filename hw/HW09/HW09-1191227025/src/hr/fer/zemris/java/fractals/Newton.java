package hr.fer.zemris.java.fractals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.collections.ComplexUtil.Complex;
import hr.fer.zemris.java.collections.ComplexUtil.ComplexPolynomial;
import hr.fer.zemris.java.collections.ComplexUtil.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Program calculating fractals derived from Newton-Raphson iteration. After
 * calculation the image of fractalas is shown on screen. Program expectes that
 * the roots of the polynomial for which the fractals will be calculated will be
 * given trough standard input. Calculation is done in parallel.
 * 
 * @author mia
 *
 */
public class Newton {

	/**
	 * String indicating the end of roots input.
	 */
	private static final String DONE = "done";

	/**
	 * Polynomial on which the Newton-Raphson iterations will be callcualted.
	 */
	static ComplexRootedPolynomial polynomial;
	/** Derivate of the polynomial. */
	static ComplexPolynomial derivated;

	/**
	 * Method called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in), StandardCharsets.UTF_8));
		System.out.println(
				"Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println(
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
		String line = "";
		List<Complex> roots = new ArrayList<Complex>();
		int argc = 1;

		while (true) {
			System.out.printf("Root %d>", argc);
			argc++;
			try {
				line = br.readLine();
			} catch (IOException e) {
				System.err.println(
						"Error while geting roots trought standard input.");
			}
			if (line.trim().toLowerCase().equals(DONE)) {
				break;
			}
			roots.add(Complex.parse(line));

		}
		polynomial = new ComplexRootedPolynomial(
				roots.toArray(new Complex[roots.size()]));
		derivated = polynomial.toComplexPolynom().derive();

		FractalViewer.show(new NRProducer());

	}

	/**
	 * Class calculating the data necessary for drawing fractal. It is
	 * calculated parallel using {@link ExecutorService} based on
	 * {@link FixedThreadPool} and custom thread factory.
	 * 
	 * @author mia
	 *
	 */
	public static class CalculateNR implements Callable<Void> {

		/** Minimum real value. */
		double reMin;
		/** Maximum real value. */
		double reMax;
		/** Minimum imaginary value. */
		double imMin;
		/** Maximum imaginary value. */
		double imMax;
		/** Width of the drawing window. */
		int width;
		/** Height of the drawing window. */
		int height;
		/** Minimum value of Y-axis. */
		int yMin;
		/** Maximum value of Y-axis. */
		int yMax;
		/** Datastore for calculation results. */
		short[] data;
		/** Maximum iterations for Newton-Rapshon computation. **/
		private static final int MAX_ITERATIONS = Integer.MAX_VALUE;
		/** Convergence threshold for Newton-Rapshon computation. **/
		private static final double CONVERGENCE_THRESHOLD = 0.002;

		/**
		 * Creates new computation class with provided arguments.
		 * 
		 * @param reMin
		 *            Minimum real value.
		 * @param reMax
		 *            Maximum real value.
		 * @param imMin
		 *            Minimum imaginary value.
		 * @param imMax
		 *            Maximum imaginary value.
		 * @param width
		 *            Width of the drawing window.
		 * @param height
		 *            Height of the drawing window.
		 * @param yMin
		 *            Minimum value of Y-axis.
		 * @param yMax
		 *            Maximum value of Y-axis.
		 * @param data
		 *            Datastore for calculation results.
		 */
		public CalculateNR(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax,
				short[] data) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;

			this.data = data;
		}

		@Override
		public Void call() throws Exception {
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double cRe = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cIm = (height - 1 - y) / (height - 1.0)
							* (imMax - imMin) + imMin;
					Complex zn = new Complex(cRe, cIm);
					Complex zn1 = new Complex();

					double module = 0;
					int iters = 0;
					do {
						Complex fraction = polynomial.apply(zn)
								.divide(derivated.apply(zn));

						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;

						iters++;
					} while ((iters < MAX_ITERATIONS)
							&& (module > CONVERGENCE_THRESHOLD));
					short index = (short) polynomial.indexOfClosestRootFor(zn1,
							CONVERGENCE_THRESHOLD);
					data[offset++] = (short) (index == -1 ? 0 : index + 1);
				}
			}

			return null;
		}
	}

	/**
	 * Producer used to display and calculate fractal with the help of
	 * parallelization.
	 * 
	 * @author mia
	 */
	public static class NRProducer implements IFractalProducer {

		/** Pool of threads that will be used in fractal production. */
		ExecutorService pool;
		/** Number of threads in pool. */
		int noWorkers;
		/** Number of the jobs. */
		int noJobs;
		/** Size of each of the job. Y axis range of each job. */
		int jobSize;

		/**
		 * Constructor.
		 */
		public NRProducer() {
			noWorkers = Runtime.getRuntime().availableProcessors();
			noJobs = 8 * noWorkers;
			pool = Executors.newFixedThreadPool(noWorkers, new ThreadFactory() {
				public Thread newThread(Runnable r) {
					Thread t = Executors.defaultThreadFactory().newThread(r);
					t.setDaemon(true);
					return t;
				}
			});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin,
				double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer) {

			System.out.println("Starting...");
			short[] data = new short[width * height];
			List<Future<Void>> results = new ArrayList<Future<Void>>();

			jobSize = height / (noJobs) == Math.floor(height / noJobs)
					? height / (noJobs) : height / (noJobs) + 1;

			for (int i = 0; i < noJobs; ++i) {
				int yMin = i * jobSize;
				int yMax = (i + 1) * jobSize - 1;

				if (i == (noJobs - 1)) {
					yMax = height - 1;
				}

				CalculateNR job = new CalculateNR(reMin, reMax, imMin, imMax,
						width, height, yMin, yMax, data);
				results.add(pool.submit(job));
			}

			for (Future<Void> result : results) {
				while (true) {
					try {
						result.get();
						break;
					} catch (InterruptedException e) {
					} catch (ExecutionException e) {
						throw new RuntimeException();
					}
				}
			}
			System.out.println("Calculus done!");
			observer.acceptResult(data,
					(short) (polynomial.toComplexPolynom().order() + 1),
					requestNo);
		}

	}

}
