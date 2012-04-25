package ex3.render.raytrace;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import math.Point3D;
import math.Ray;
import math.Vec;


/**
 * A Scene class containing all the scene objects including camera, lights and
 * surfaces.
 * 
 */
public class Scene implements IInitable {

	protected Vec backgroundCol;
	protected Vec ambientLight;
	protected List<Object3D> surfaces;
	protected List<Light> lights;
	protected Camera camera;

	public Scene() {
		backgroundCol = new Vec(0.5, 0.5, 0.5);
		ambientLight = new Vec(0.5, 0.5, 0.5);
		surfaces = new LinkedList<Object3D>();
		lights = new LinkedList<Light>();
	}

	public void init(Map<String, String> attributes) {
		if (attributes.containsKey("background-col"))
			backgroundCol = new Vec(attributes.get("background-col"));
		if (attributes.containsKey("ambient-light"))
			ambientLight = new Vec(attributes.get("ambient-light"));
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	/**
	 * Send ray return the nearest intersection. Return null if no intersection
	 * e 
	 * @param ray
	 * @return
	 */
	public Hit findIntersection(Ray ray) {
		double minDistance = Double.POSITIVE_INFINITY;
		Hit minSurface = null;
		for (Object3D surface : surfaces) {


			Hit hit = surface.nearestIntersection(ray);
			if (hit != null) {
				double d = hit.distance;

				if (minDistance > d) {
					minDistance = d;
					minSurface = hit;
				}
			}
		}

		if (Double.isInfinite(minDistance))
			return null;

		return minSurface;
	}

	/**
	 * Calculates the color at a given intersection point
	 * 
	 * @param hit
	 *            The intersection point and surface
	 * @param ray
	 *            Hitting ray
	 * @return
	 */
	public Vec calcColor(Hit hit, Ray ray) {
		if (hit == null)
			return backgroundCol;

		// start from the ambient light color value
		Vec lightSum = new Vec(hit.surface.material.emission);

		// add the material ambient using the global ambient
		lightSum.add(Vec.scale(hit.surface.material.ambient, ambientLight));

		for (Light light : lights) {
			lightSum.add(light.getIllumination(hit, ray));


		}

		return lightSum;
	}

	/**
	 * Add objects to the scene by name
	 * 
	 * @param name
	 *            Object's name
	 * @param attributes
	 *            Object's attributes
	 */
	public void addObjectByName(String name, Map<String, String> attributes) {
		if (name.equals("sphere")) {
			Object3D surface = new Sphere();
			surface.init(attributes);
			surfaces.add(surface);
		}

		if (name.equals("trimesh")) {
			Object3D surface = new TriMesh();
			surface.init(attributes);
			surfaces.add(surface);
		}

		if (name.equals("omni-light")) {
			Light light = new OmniLight(this);
			light.init(attributes);
			lights.add(light);
		}

		if (name.equals("spot-light")) {
			Light light = new SpotLight(this);
			light.init(attributes);
			lights.add(light);
		}

	}
}
