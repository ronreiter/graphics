package ex3.render.raytrace;

import java.util.Map;

import math.Point3D;
import math.Ray;
import math.Vec;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;


/**
 * Simple Sphere object
 * 
 */
public class Sphere extends Object3D {

	protected Point3D center;
	protected double radius;

	public double nearestIntersection(Ray ray)
	{
		double b;
		double c;
		double disc, solution;
		double t1, t2;
		
		Vec distance = new Vec(ray.origin, center);
		
		Vec distanceSquared = new Vec(distance);
		distanceSquared.scale(2);

		b = Vec.dotProd(distanceSquared, ray.direction);
		c = distance.lengthSquared() - Math.pow(radius, 2);

		disc = b * b - 4 * c;
	
		if (disc < 0) {
			return Double.POSITIVE_INFINITY;
		}
		
		if (b < 0) {
			solution = (-b - Math.sqrt(disc)) / 2;
		} else {
			solution = (-b + Math.sqrt(disc)) / 2;
		}
	
		t1 = solution;
		t2 = c / solution;

		if (t1 < t2) {
			if (t2 < 0) {
				return Double.POSITIVE_INFINITY;
			}

			if (t1 < 0) {
				return t2;
			}

			return t1;
		} else {
			if (t1 < 0) {
				return Double.POSITIVE_INFINITY;
			}

			if (t2 < 0) {
				return t1;
			}

			return t2;

		}

	}


	@Override
	public Vec normalAt(Point3D intersection, Ray ray) {

        Vec normal = new Vec(intersection);
        normal.sub(center.toVec());

        normal.normalize();

		return normal;
	}

	@Override
	public void init(Map<String, String> attributes) {
		if (attributes.containsKey("center"))
			center = new Point3D(attributes.get("center"));
		if (attributes.containsKey("radius"))
			radius = Double.parseDouble(attributes.get("radius"));
		super.init(attributes);
	}
}
