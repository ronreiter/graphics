package ex3.render.raytrace;

import java.util.Map;

import math.Point3D;
import math.Ray;
import math.Vec;


/**
 * A simple surface primitive
 * 
 */
public abstract class Object3D implements IInitable {

	Material material;

	public void init(Map<String, String> attributes) {
		material = new Material();
		material.init(attributes);
	}

	/**
	 * Computes ray's distance to surface. Returns PosInf if no intersection
	 * 
	 * @param ray
	 * @return Scalar positive distance
	 */
	public abstract Hit nearestIntersection(Ray ray);

	/**
	 * Computes the surface's normal at a given intersection point
	 * 
	 * @param intersection
	 *            Point where ray intersected surface
	 * @param ray
	 *            Ray originating the intersection
	 * @return Normalized vector
	 */
	public abstract Vec normalAt(Point3D intersection, Ray ray);
}
