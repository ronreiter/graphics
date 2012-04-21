package ex3.render.raytrace;

import math.Point3D;
import math.Vec;

/**
 * Contains information regarding a ray-surface intersection.
 * 
 */
public class Hit {

	public Point3D intersection;
	public Object3D surface;

	public Hit(Point3D intersection, Object3D surface) {
		this.intersection = intersection;
		this.surface = surface;
	}
}
