package ex3.render.raytrace;

import ex3.math.Point3D;
import ex3.math.Ray;
import ex3.math.Vec;

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
	private double viewportWidth;
	private double viewportDist;
    private int canvasWidth;
    private int canvasHeight;

	public Camera (int canvasWidth, int screenHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = screenHeight;
        eye = new Point3D(0, 0, 0);
        direction = new Vec(0, 0, -1);
        upDirection = new Vec(0, 1, 0);
        viewportWidth = 2;
        viewportDist = 1;

        calculateVectors();
	}

    public void init(Map<String, String> parameters) {

		if (parameters.get("eye") != null)
        	eye = new Point3D(parameters.get("eye"));

		if (parameters.get("direction") != null) {
			direction = new Vec(parameters.get("direction"));
			direction.normalize();
		}

		if (parameters.get("up-direction") != null) {
			upDirection = new Vec(parameters.get("up-direction"));
			upDirection.normalize();
		}

		if (parameters.get("look-at") != null) {
			direction = new Vec(parameters.get("look-at"));
			direction.sub(eye.toVec());
			direction.normalize();
		}

		if (parameters.get("screen-width") != null)
    	    viewportWidth = Double.parseDouble(parameters.get("screen-width"));

		if (parameters.get("screen-dist") != null)
        	viewportDist = Double.parseDouble(parameters.get("screen-dist"));


        calculateVectors();
    }

    private void calculateVectors() {
        rightDirection = Vec.crossProd(direction, upDirection);
        upDirection = Vec.crossProd(rightDirection, direction);

        // the vector size should be the size of one pixel in the 3d world instead of 1.
        // first, normalize the size according to the canvas width.
        double pixelSize = 1 / (double) canvasWidth;

        // then, increase the pixel size according to the viewport width.
        pixelSize *= viewportWidth;

        rightDirection.scale(pixelSize);
        upDirection.scale(pixelSize);
    }

	/**
	 * Transforms image xy coordinates to view pane xyz coordinates. Returns the
	 * ray that goes through it.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Ray constructRayThroughPixel(double x, double y) {
		Point3D lookAt = new Point3D(eye);
		Vec towards = new Vec(direction);
		Vec up = new Vec(upDirection);
		Vec right = new Vec(rightDirection);

        towards.scale(viewportDist);
		right.scale(x - (double) canvasWidth / 2);
        up.scale(y - (double) canvasHeight / 2);

		lookAt.add(towards);
		lookAt.add(right);
		lookAt.add(up);
        
        Vec rayDirection = new Vec(lookAt, eye);

		// Note - this is a trivial Orthographic camera
		return new Ray(eye, rayDirection);
	}

}
