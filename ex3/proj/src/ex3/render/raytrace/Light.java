package ex3.render.raytrace;

import java.util.Map;

import math.Point3D;
import math.Ray;
import math.Vec;


/**
 * Represent a point light
 * 
 */
public class Light {

	protected Scene scene;
	protected Point3D pos;
	protected Vec color;
    protected double kc;
    protected double kl;
    protected double kq;    

	public Light(Scene scene) {
		this.scene = scene;
		this.pos = new Point3D(0, 0, 0);
		this.color = new Vec(1,1,1);
        this.kc = 1;
        this.kl = 0;
        this.kq = 0;
	}

	public void init(Map<String, String> attributes) {
		if (attributes.containsKey("pos"))
			pos = new Point3D(attributes.get("pos"));
		if (attributes.containsKey("color"))
			color = new Vec(attributes.get("color"));
        if (attributes.containsKey("kc"))
            kc = Double.parseDouble(attributes.get("kc"));
        if (attributes.containsKey("kl"))
            kl = Double.parseDouble(attributes.get("kl"));
        if (attributes.containsKey("kq"))
            kq = Double.parseDouble(attributes.get("kq"));
    }

	private boolean lightBlocked(Hit hit, Ray lightRay) {
		// shoot a ray from the light and make sure that we hit what we saw.
		Hit lightHit = scene.findIntersection(lightRay);

		if (lightHit == null) {
			return false;
		}

		return (lightHit.surface != hit.surface);
	}
	
	public Vec getIllumination(Hit hit, Ray ray) {
		Vec lightSum = new Vec();

		Vec lightDirection =  new Vec(hit.intersection, this.pos);

		// it's more efficient to get both distance types
		double lightDistanceAttenuation = 1 / (
				this.kc +
						this.kl * lightDirection.length() +
						this.kq * lightDirection.lengthSquared());


		lightDirection.normalize();
		Ray lightRay = new Ray(this.pos, lightDirection);

		if (lightBlocked(hit, lightRay)) {
			return lightSum;
		}

		Vec lightHitNormal = hit.surface.normalAt(hit.intersection, lightRay);
		lightHitNormal.normalize();

		// calculate the dot product for diffusive shading
		Vec diffuse = hit.surface.material.diffuse.clone();
		Vec specular = hit.surface.material.specular.clone();

		// Lambert diffusion
		// multiply by the light color, diffusion amount by angle, and inverse square (3d distance) light attenuation
		// we negate the result because the light direction is facing the normal of the plane
		double diffusionAmount = -lightHitNormal.dotProd(lightDirection);
		
		if (diffusionAmount < 0) {
			diffusionAmount = 0;
		}

		diffuse.scale(this.color);
		diffuse.scale(diffusionAmount);
		
		lightSum.add(diffuse);

		// Phong specular model
		// first, calculate the reflection direction vector
		Vec reflection = lightDirection.reflect(lightHitNormal);

		// first we multiply by the light color
		specular.scale(this.color);

		// then, we multiply by the cosine of the angle between the reflected ray from the light and the viewport.
		// we then multiply the scalar with the shininess factor to achieve greater shininiess.
		if (reflection.dotProd(ray.direction) < 0) {
			double specularAmount = Math.pow(-reflection.dotProd(ray.direction), hit.surface.material.shininess);

			specular.scale(specularAmount);
			lightSum.add(specular);
		}

		lightSum.scale(lightDistanceAttenuation);

		return lightSum;
	}
}
