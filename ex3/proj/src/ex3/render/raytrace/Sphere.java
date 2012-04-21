package ex3.render.raytrace;

import java.util.Map;

import math.Point3D;
import math.Ray;
import math.Vec;


/**
 * Simple Sphere object
 * 
 */
public class Sphere extends Object3D {

	protected Vec center;
	protected double radius;

    /*
    public double nearestIntersection(Ray ray)
    {
        double a = 1;

        Vec centerCenter = new Vec(ray.origin);
        centerCenter.sub(this.center);
        Vec bTmp = new Vec(centerCenter);
        bTmp.scale(2.0D);
        double b = Vec.dotProd(bTmp, ray.direction);

        double c = Vec.dotProd(centerCenter, centerCenter) - this.radius * this.radius;

        double disc = b * b - 4.0D * a * c;

        if (disc < 0.0D) {
            return Double.POSITIVE_INFINITY;
        }
        double discSqrt = Math.sqrt(disc);
        double q;
        if (b < 0.0D)
            q = (-b - discSqrt) / 2.0D;
        else {
            q = (-b + discSqrt) / 2.0D;
        }

        double t0 = q / a;
        double t1 = c / q;

        if (t0 > t1)
        {
            double temp = t0;
            t0 = t1;
            t1 = temp;
        }

        if (t1 < 0.0D) {
            return Double.POSITIVE_INFINITY;
        }

        if (t0 < 0.0D)
        {
            return t1;
        }

        return t0;
    }
    */

    @Override
	public double nearestIntersection(Ray ray) {

		// calculate sphere intersection using the algebraic method
        double b;
        double c;
        double solution;
        double discriminant, discriminantPart;
        
        Vec directionTwice = new Vec(ray.direction);
        Vec diffVector = new Vec(ray.origin);
        directionTwice.scale(2);
        diffVector.sub(center);

        // solve the equation:
        // a = 1
        // b = 2V * (Po - O)
        // c = |Po - 0|^2 - r^2
        b = directionTwice.dotProd(diffVector);
        c = diffVector.lengthSquared() - radius * radius;

        discriminant = b * b - 4 * c;
        discriminantPart = Math.sqrt(discriminant) / 2;

        if (discriminant < 0) {
            return Double.POSITIVE_INFINITY;
        }

        if (b < 0) {
            solution = -b - discriminantPart;
        } else {
            solution = -b + discriminantPart;
        }

        return solution;
	}

	@Override
	public Vec normalAt(Point3D intersection, Ray ray) {

        Vec normal = new Vec(intersection);
        normal.sub(center);

        normal.normalize();

		return normal;
	}

	@Override
	public void init(Map<String, String> attributes) {
		if (attributes.containsKey("center"))
			center = new Vec(attributes.get("center"));
		if (attributes.containsKey("radius"))
			radius = Double.parseDouble(attributes.get("radius"));
		super.init(attributes);
	}
}
