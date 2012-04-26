package ex3.render.raytrace;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import math.Point3D;
import math.Ray;
import math.Vec;
import sun.jvm.hotspot.utilities.BitMap;

import javax.imageio.ImageIO;


/**
 * A Scene class containing all the scene objects including camera, lights and
 * surfaces.
 * 
 */
public class Scene implements IInitable {
	protected BufferedImage backgroundTexture;
	protected String backgroundTextureFileName;
	protected int superSamplingWidth;
	protected Vec backgroundCol;
	protected Vec ambientLight;
	protected List<Object3D> surfaces;
	protected List<Light> lights;
	protected Camera camera;
	protected int width;
	protected int height;
	
	private final static double EPSILON_DISTANCE = 0.0001;

	public Scene(int width, int height) {
		this.backgroundCol = new Vec(0.5, 0.5, 0.5);
		this.ambientLight = new Vec(0.5, 0.5, 0.5);
		this.surfaces = new LinkedList<Object3D>();
		this.lights = new LinkedList<Light>();
		this.superSamplingWidth = 1;
		this.backgroundTexture = null;
		this.width = width;
		this.height = height;
	}

	public void init(Map<String, String> attributes) {
		if (attributes.containsKey("background-col"))
			backgroundCol = new Vec(attributes.get("background-col"));
		if (attributes.containsKey("ambient-light"))
			ambientLight = new Vec(attributes.get("ambient-light"));
		if (attributes.containsKey("super-samp-width"))
			superSamplingWidth = Integer.parseInt(attributes.get("super-samp-width"));
		if (attributes.containsKey("background-tex"))
			backgroundTextureFileName = attributes.get("background-tex");
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

	public Vec getBackgroundColor(int x, int y) {
		if (backgroundTexture != null) {
			return new Vec(new Color(backgroundTexture.getRGB(x, backgroundTexture.getHeight() - y - 1)));
		}
		return backgroundCol;

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
	public Vec calcColor(Hit hit, Ray ray, int x, int y, int iteration) {
		if (iteration == 0) {
			return new Vec();
		}
		
		if (hit == null) {
			return getBackgroundColor(x, y);
		}

		// start from the emission light color value
		Vec lightSum = new Vec(hit.surface.material.emission);

		// add the material ambient using the global ambient
		lightSum.add(Vec.scale(hit.surface.material.ambient, ambientLight));

		for (Light light : lights) {
			lightSum.add(light.getIllumination(hit, ray));

		}

		// add recusive reflection
		if (hit.surface.material.reflectance > 0) {
			Vec reflection = new Vec(ray.direction);
			reflection.reflect(hit.surface.normalAt(hit.intersection, ray));
			Ray reflectionRay = new Ray(hit.intersection, reflection);
			
			// the reflection needs to occur outside the intersection point
			Vec epsilon = reflectionRay.direction.clone();
			epsilon.scale(EPSILON_DISTANCE);
			reflectionRay.origin.add(epsilon);
			
			Hit reflectionHit = findIntersection(reflectionRay);

			// why do we need to create a new vector?
			Vec recursiveRayColor = new Vec(calcColor(reflectionHit, reflectionRay, x, y, iteration - 1));

			recursiveRayColor.scale(hit.surface.material.reflectance);
			lightSum.add(recursiveRayColor);
		}

		lightSum.limit();

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

	public void loadResources(File path) {
		// load the resources
		if (backgroundTextureFileName != null) {
			try {
				File textureFile = new File(path.getParent(), backgroundTextureFileName);
				if (textureFile.exists()) {
					// scale the background to the size of the scene
					BufferedImage scaledImage = new BufferedImage(width, height, 1);
					Graphics2D graphics2D = scaledImage.createGraphics();
					graphics2D.drawImage(ImageIO.read(textureFile), 0, 0, width, height, null);
					graphics2D.dispose();

					backgroundTexture = scaledImage;
				} else {
					System.out.println("Can't find texture at " + textureFile.getAbsolutePath());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
