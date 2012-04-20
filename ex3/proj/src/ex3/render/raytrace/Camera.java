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
	private double viewportWidth;
    private double viewportHeight;
	private double viewportDist;
    private double aspectRatio;
    private int screenWidth;
    private int screenHeight;

	public Camera (int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        aspectRatio = (double)screenWidth / (double)screenHeight;

        eye = new Point3D(0, 0, 0);
        direction = new Vec(0, 0, -1);
        upDirection = new Vec(0, 1, 0);
        viewportWidth = 2;
        viewportDist = 1;

        calculateVectors();

	}

    public void init(Map<String, String> parameters) {
        
        eye = new Point3D(parameters.get("eye"));
        direction = new Vec(parameters.get("direction"));
        viewportWidth = Double.parseDouble(parameters.get("screen-width"));
        viewportDist = Double.parseDouble(parameters.get("screen-dist"));
        upDirection = new Vec(parameters.get("up-direction"));

        calculateVectors();

    }

    private void calculateVectors() {
        viewportHeight = viewportWidth / aspectRatio;

        rightDirection = Vec.crossProd(upDirection, direction);
        upDirection = Vec.crossProd(direction, rightDirection);


        rightDirection.scale(1 / (double)screenWidth);
        upDirection.scale(1 / (double)screenHeight);
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

		towards.scale(viewportDist);
        
		right.scale(x);
        up.scale(y);

		lookAt.add(towards);
		lookAt.add(right);
		lookAt.add(up);
        
        Vec rayDirection = new Vec(lookAt, eye);
        rayDirection.normalize();
        
        System.out.println("look at: " + lookAt);
        System.out.println("direction: " + rayDirection);

		// Note - this is a trivial Orthographic camera
		return new Ray(eye, rayDirection);
	}

}
