package ex3.render.raytrace;

import math.Point3D;
import math.Ray;
import math.Vec;

/**
 * Represents the scene's camera.
 * 
 */

public class Camera {

	//instance variables
	private Point3D eye = null;
	private Vec direction = null;
	private Vec up_direction = null;
	private int screen_width;
	private int screen_dist;



	public Camera (Point3D eye, Vec direction, Vec up_direction, int screen_width, int screen_dist) {
		this.eye = eye;
		this.direction = direction;
		this.up_direction = up_direction;
		this.screen_width = this.screen_width;
		this.screen_dist = screen_dist;
	}


	/**
	 * Transforms image xy coordinates to view pane xyz coordinates. Returns the
	 * ray that goes through it.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Ray constructRayThroughPixel(int x, int y) {
		
		Vec right_direction = Vec.crossProd(up_direction, direction);
		Poi
		// Note - this is a trivial Orthographic camera
		return new Ray(new Point3D(x, y, 0), new Vec(0, 0, -1));
	}

}
