package ex3.render.raytrace;

import math.Point3D;
import math.Ray;
import math.Vec;

import java.util.Map;

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

	public Camera () {
        eye = new Point3D(0, 0, 0);
        direction = new Vec(0, 0, -1);
        upDirection = new Vec(0, 1, 0);
        screenWidth = 2;
        screenDist = 1;

        rightDirection = Vec.crossProd(upDirection, direction);

	}

    public void init(Map<String, String> parameters) {
        
        eye = new Point3D(parameters.get("eye"));
        direction = new Vec(parameters.get("direction"));
        screenWidth = Double.parseDouble(parameters.get("screen-width"));
        screenDist = Double.parseDouble(parameters.get("screen-dist"));
        upDirection = new Vec(parameters.get("up-direction"));

        rightDirection = Vec.crossProd(upDirection, direction);
        upDirection = Vec.crossProd(direction, rightDirection);
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
