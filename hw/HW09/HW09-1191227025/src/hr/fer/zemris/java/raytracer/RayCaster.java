package hr.fer.zemris.java.raytracer;

import java.util.List;

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
 * Program producing the image due the created scene with
 * {@link RayTracerViewer} and selected eye and view position. For coloring of
 * each pixel we will use Phongs model which assumes that there is one or more
 * pointlight- sources present in scene. In our example there are two light
 * sources (one green and one red in the previous image). Each light source is
 * specified with intensities of r, g and b components it radiates.
 * 
 * @author mia
 *
 */
public class RayCaster {
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

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
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

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
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

		if (rgb.length != 3) {
			throw new IllegalArgumentException(
					"There should be 3 color components and you have provided only "
							+ rgb.length);
		}

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
					closestIntersection.getDistance() + 0.0005,
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