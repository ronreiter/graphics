package ex3.render.raytrace;

import raycaster.math.Vec;

/**
 * Contains information regarding a ray-surface intersection.
 * 
 */
public class Hit {

	public Vec intersection;
	public Object3D surface;

	public Hit(Vec intersection, Object3D surface) {
		this.intersection = intersection;
		this.surface = surface;
	}
}
