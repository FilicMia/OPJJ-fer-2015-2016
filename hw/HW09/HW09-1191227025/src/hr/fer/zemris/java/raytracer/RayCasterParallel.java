package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * <p>
 * Program producing the image due the created scene with
 * {@link RayTracerViewer} and selected eye and view position. For coloring of
 * each pixel we will use Phongs model which assumes that there is one or more
 * pointlight- sources present in scene. In our example there are two light
 * sources (one green and one red in the previous image). Each light source is
 * specified with intensities of r, g and b components it radiates.
 * </p>
 * <p>
 * Program parallelizes coloring using Fork-Join framework and RecursiveAction.
 * </p>
 */
public class RayCasterParallel {
	/**
	 * Method called to when invoking an program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Static function produces the concrete {@link IRayTracerProducer}.
	 * 
	 * @return {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D OG = view.sub(eye).normalize();

				Point3D yAxis = viewUp.normalize()
						.sub(OG.scalarMultiply(
								viewUp.normalize().scalarProduct(OG)))
						.normalize();
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view
						.sub(xAxis.scalarMultiply(horizontal / 2.0))
						.add(yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool(
						Runtime.getRuntime().availableProcessors());

				pool.invoke(new CalculateColoring(scene, eye, red, green, blue,
						horizontal, vertical, xAxis, yAxis, screenCorner, width,
						height, 0, height));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Class calculating the data necessary for drawing the scene. It is
	 * calculated parallel using {@link ForkJoinPool} and
	 * {@link RecursiveAction}.
	 * 
	 * @author mia
	 *
	 */
	public static class CalculateColoring extends RecursiveAction {
		/**
		 * Default version id.
		 */
		private static final long serialVersionUID = 1L;

		/** Scene. */
		Scene scene;
		/** Eye point. */
		Point3D eye;
		/** Intensity of color red of each pixel. */
		short[] red;
		/** Intensity of color green of each pixel. */
		short[] green;
		/** Intensity of color blue of each pixel. */
		short[] blue;
		/** Horizontal size of the screen. */
		double horizontal;
		/** Vertical size of the screen. */
		double vertical;
		/** Direction of x axis of the screen. */
		Point3D xAxis;
		/** Direction y axis of the screen. */
		Point3D yAxis;
		/** Screen corner.Upper left. */
		Point3D screenCorner;
		/** Width of the drawing window. */
		int width;
		/** Height of the drawing window. */
		int height;
		/** Minimum value of Y-axis. */
		int yMin;
		/** Maximum value of Y-axis. */
		int yMax;
		/** Treshold for calculating the nessesery data direct. */
		private static int treshold = 16;

		/**
		 * Creates new computation class with provided arguments.
		 * 
		 * @param scene
		 *            scene due to which the image will be created
		 * @param eye
		 *            position of the eye on the scene
		 * 
		 * @param screenCorner
		 *            Screen corner.Upper left.
		 * @param yAxis
		 *            direction of y axis of the screen.
		 * @param xAxis
		 *            direction of the x axis of the screen
		 * @param vertical
		 *            vertical size of the screen
		 * @param horizontal
		 *            horizontal size of the screen
		 * @param blue
		 *            array of intensities of color blue of each pixel.
		 * @param green
		 *            array of intensities of color green of each pixel.
		 * @param red
		 *            array of intensities of color red of each pixel.
		 * 
		 * @param width
		 *            Width of the drawing window.
		 * @param height
		 *            Height of the drawing window.
		 * @param yMin
		 *            Minimum value of Y-axis.
		 * @param yMax
		 *            Maximum value of Y-axis.
		 */
		public CalculateColoring(Scene scene, Point3D eye, short[] red,
				short[] green, short[] blue, double horizontal, double vertical,
				Point3D xAxis, Point3D yAxis, Point3D screenCorner, int width,
				int height, int yMin, int yMax) {
			super();
			this.scene = scene;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= treshold) {
				computeDirect();
				return;
			}

			invokeAll(
					new CalculateColoring(scene, eye, red, green, blue,
							horizontal, vertical, xAxis, yAxis, screenCorner,
							width, height, yMin, yMin + (yMax - yMin) / 2),
					new CalculateColoring(scene, eye, red, green, blue,
							horizontal, vertical, xAxis, yAxis, screenCorner,
							width, height, yMin + (yMax - yMin) / 2, yMax));
		}

		/**
		 * Direct computation of the part of data.
		 */
		protected void computeDirect() {
			short[] rgb = new short[3];

			int offset = yMin * width;
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner
							.add(xAxis.scalarMultiply(
									x / (width - 1.0) * horizontal))
							.sub(yAxis.scalarMultiply(
									y / (height - 1.0) * vertical));

					Ray ray = Ray.fromPoints(eye, screenPoint);

					tracer(scene, ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

					offset++;
				}
			}

			return;
		}

		/**
		 * Seting treshold. If range of y is smaller than this treshold, the
		 * work is calculated direct, otherwise it is split into two works.
		 * 
		 * @param treshold
		 *            new threshold
		 */
		public static void setTreshold(int treshold) {
			CalculateColoring.treshold = treshold;
		}
	}

	/**
	 * Determination of the color({@code rgb}) components of the point of
	 * intersection of an ray {@code ray} and the closest object on the scene
	 * {@code scene}. Ray is specified by starting point O (e) and Normalized
	 * directional vector d = (pointxy-O)/norm(pointxy-O):
	 * 
	 * @param scene
	 *            model of scene containing graphical objects
	 * @param ray
	 *            ray for which the rgb components should be calculated.
	 * 
	 * @param rgb
	 *            calculated color components components
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		double[] rgbCalculated = new double[3];

		rgbCalculated[0] = 15;
		rgbCalculated[1] = 15;
		rgbCalculated[2] = 15;

		RayIntersection closestIntersection = findClosestIntersection(ray,
				scene);
		if (closestIntersection != null) {
			determinateColorFor(closestIntersection, ray, scene, rgbCalculated);
		}

		rgb[0] = (short) rgbCalculated[0];
		rgb[1] = (short) rgbCalculated[1];
		rgb[2] = (short) rgbCalculated[2];

	}

	/**
	 * Determinate the color for given {@link RayIntersection} on scene
	 * {@code scene}.
	 * 
	 * @param rayIntersection
	 *            ray intersection for which to determinate the Intensity of
	 *            each color component.
	 * @param scene
	 *            Environment of the ray intersection.
	 * @param ray
	 *            ray for which the rayIntersection is calculated
	 * @param rgbCalculated
	 *            result
	 */
	private static void determinateColorFor(RayIntersection rayIntersection,
			Ray ray, Scene scene, double[] rgbCalculated) {

		List<LightSource> lightSources = scene.getLights();
		Ray rayLigthIntersection;// ray from ls to
									// intersection
		for (LightSource ls : lightSources) {
			rayLigthIntersection = Ray.fromPoints(ls.getPoint(),
					rayIntersection.getPoint());
			RayIntersection closestIntersection = findClosestIntersection(
					rayLigthIntersection, scene);

			double rayIntersectionDinstance = ls.getPoint()
					.sub(rayIntersection.getPoint()).norm();// ||ls-intersection||

			if (closestIntersection != null && Double.compare(
					closestIntersection.getDistance() + 10e-10,
					rayIntersectionDinstance) >= 0) {

				addDiffusionComponent(ls, ray, rgbCalculated, rayIntersection);
				addReflectionComponent(ls, ray, rgbCalculated, rayIntersection);

			}

		}

	}

	/**
	 * Adds the diffusion component to the each color component of the lightning
	 * of the point specified with {@code rayIntersection} due to
	 * {@link LightSource} {@code ls} preferences.
	 * 
	 * @param ls
	 *            light source
	 * @param ray
	 *            ray for which the intersection is calculated
	 * @param rgbCalculated
	 *            color components
	 * @param rayIntersection
	 *            intersection of ray and one of the objects on the scene
	 */
	private static void addReflectionComponent(LightSource ls, Ray ray,
			double[] rgbCalculated, RayIntersection rayIntersection) {

		Point3D d = rayIntersection.getPoint().sub(ls.getPoint()).normalize();// light->intersection
		Point3D n = rayIntersection.getNormal();
		// r=d−2(d⋅n)n
		Point3D r = d.sub(n.scalarMultiply(2 * d.scalarProduct(n)));
		Point3D v = ray.direction.negate();
		double cosinus = Math.pow(Math.max(r.scalarProduct(v),0), rayIntersection.getKrn());

		rgbCalculated[0] += ls.getR() * rayIntersection.getKrr() * cosinus;
		rgbCalculated[1] += ls.getG() * rayIntersection.getKrg() * cosinus;
		rgbCalculated[2] += ls.getB() * rayIntersection.getKrb() * cosinus;
	}

	/**
	 * Adds the reflection component to the each color component of the
	 * lightning of the point specified with {@code rayIntersection} due to
	 * {@link LightSource} {@code ls} preferences.
	 * 
	 * @param ls
	 *            light source
	 * @param ray
	 *            ray for which the intersection is calculated
	 * @param rgbCalculated
	 *            color components
	 * @param rayIntersection
	 *            intersection of ray and one of the objects on the scene
	 */
	private static void addDiffusionComponent(LightSource ls, Ray ray,
			double[] rgbCalculated, RayIntersection rayIntersection) {
		Point3D l = ls.getPoint().sub(rayIntersection.getPoint()).normalize();// intersection->light
		Point3D n = rayIntersection.getNormal();

		double cosinus = Math.max(l.scalarProduct(n), 0);

		rgbCalculated[0] += ls.getR() * rayIntersection.getKdr() * cosinus;
		rgbCalculated[1] += ls.getG() * rayIntersection.getKdg() * cosinus;
		rgbCalculated[2] += ls.getB() * rayIntersection.getKdb() * cosinus;

	}

	/**
	 * Method finds the closest intesection of the ray and objects on the scene.
	 * 
	 * @param ray
	 *            ray for which the closest intersection ray will be found.
	 * @param scene
	 *            surrounding in which the closest intersection should be found.
	 * @return the closest {@link IntersectionRay} or null if there is no such.
	 */
	private static RayIntersection findClosestIntersection(Ray ray,
			Scene scene) {
		List<GraphicalObject> graphicalObjects = scene.getObjects();
		RayIntersection closestIntersection = null;
		for (GraphicalObject go : graphicalObjects) {
			RayIntersection intersection = go.findClosestRayIntersection(ray);
			if (intersection != null && (closestIntersection == null
					|| intersection.getDistance() < closestIntersection
							.getDistance())) {
				closestIntersection = intersection;
			}
		}

		return closestIntersection;

	}

}