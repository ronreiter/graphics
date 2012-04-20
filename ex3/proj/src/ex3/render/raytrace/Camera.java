package ex3.render.raytrace;

import math.Point3D;
import math.Ray;
import math.Vec;

/**
 * Represents the scene's camera.
 * 
 */
public class Camera {

	/**
	 * Transforms image xy coordinates to view pane xyz coordinates. Returns the
	 * ray that goes through it.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Ray constructRayThroughPixel(int x, int y) {

		// Note - this is a trivial Orthographic camera
		return new Ray(new Point3D(x, y, 0), new Vec(0, 0, -1));
	}

}
