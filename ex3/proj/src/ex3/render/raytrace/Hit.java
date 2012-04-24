package ex3.render.raytrace;

import math.Point3D;
import math.Vec;

/**
 * Contains information regarding a ray-surface intersection.
 * 
 */
public class Hit {

	double distance;
	public Point3D intersection;
	public Object3D surface;

	public Hit(Point3D intersection, Object3D surface, double distance) {
		this.intersection = intersection;
		this.surface = surface;
		this.distance = distance;
	}
}
