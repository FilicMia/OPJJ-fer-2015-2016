package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

/**
 * Implementing object sphere which can be placed in the scene.
 * 
 * @author mia
 *
 */
public class Sphere extends GraphicalObject {
	/** Center of the sphere. */
	Point3D center;
	/** Radius of the sphere. */
	double radius;
	/**
	 * Coefficient for diffuse component for red color; used in lightning model
	 * to calculate point color. Legal values are [0.0,1.0].
	 */

	double kdr;
	/**
	 * Coefficient for diffuse component for green color; used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	double kdg;

	/**
	 * Coefficient for diffuse component for blue color; used in lightning model
	 * to calculate point color. Legal values are [0.0,1.0].
	 */
	double kdb;

	/**
	 * Coefficient for reflection component for red color; used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	double krr;

	/**
	 * Coefficient for reflection component for green color; used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	double krg;

	/**
	 * Coefficient for reflection component for blue color; used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 */
	double krb;

	/** Coefficient <code>n</code> for reflective component. */
	double krn;

	/**
	 * Constructor. Setting the center and the radius of the sphere as well as
	 * the coefficients for reflection and diffusion component used in lightning
	 * model to calculate point color. Legal values are [0.0,1.0].
	 * 
	 * @param center
	 *            Center of the sphere.
	 * @param radius
	 *            Radius of the sphere.
	 * @param kdr
	 *            Coefficient for diffusion component for red color.
	 * @param kdg
	 *            Coefficient for diffusion component for green color.
	 * @param kdb
	 *            Coefficient for diffusion component for blue color.
	 * @param krr
	 *            Coefficient for reflection component for red color.
	 * @param krg
	 *            Coefficient for reflection component for green color.
	 * @param krb
	 *            Coefficient for reflection component for blue color.
	 * @param krn
	 *            Coefficient <code>n</code> for reflective component.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	public RayIntersection findClosestRayIntersection(Ray ray) {
		boolean isOuter = false;
		
		Point3D D = ray.direction;
		Point3D C = center;
		Point3D O = ray.start; 
		Point3D CO = O.sub(C);
		double r = radius;
		
		
		double a = 1;
		double b = (D.scalarMultiply(2)).scalarProduct(O.sub(C));
		double c = CO.scalarProduct(CO)-r*r;
		
		double discriminant = b*b-4*a*c;
		if(discriminant < 0){
			return null;
		}
		
		double t0 = (-b-Math.sqrt(discriminant))/(2*a); 
		double t1 = (-b+Math.sqrt(discriminant))/(2*a); 
		if(t0 < 0 && t1 < 0){
			return null;
		}
    	
		Point3D firstInter = ray.start.add(D.scalarMultiply(t0));
		Point3D secondInter = ray.start.add(D.scalarMultiply(t1));
		
		double firstDistance = ray.start.sub(firstInter).norm();
		double secondDistance = ray.start.sub(secondInter).norm();
		
		Point3D closerInter = firstInter;
		double closerDistance = firstDistance;
		
		if(secondDistance < firstDistance){
			closerInter = secondInter;
			closerDistance = secondDistance;
			
		}
		
		isOuter = C.sub(closerInter).norm() < r;

		return new RayIntersection(closerInter, closerDistance,
				isOuter) {

			@Override
			public Point3D getNormal() {
				return this.getPoint().sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;

			}
		};
	}
	
	
}