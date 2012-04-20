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
	private Vec upDirection = null;
	private Vec rightDirection = null;
	private double screenWidth;
	private double screenDist;



	public Camera (Point3D eye, Vec direction, Vec up_direction, int screen_width, int screen_dist) {
		this.eye = eye;
		this.direction = direction;
		this.screenWidth = this.screenWidth;
		this.screenDist = screen_dist;

		rightDirection = Vec.crossProd(up_direction, direction);
		this.upDirection = Vec.crossProd(direction, rightDirection);
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

		Point3D lookAt = new Point3D(eye);
		Vec towards = new Vec(direction);
		Vec up = new Vec(upDirection);
		Vec right = new Vec(rightDirection);

		towards.scale(screenDist);
		up.scale(y);
		right.scale(x);

		lookAt.add(towards);
		lookAt.add(right);
		lookAt.add(up);

		// Note - this is a trivial Orthographic camera
		return new Ray(eye, new Vec(lookAt, eye));
	}

}
