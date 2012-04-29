package ex3.render.raytrace;

import java.util.Map;

import ex3.math.Vec;


/**
 * Represent's a surface's material
 * 
 */
public class Material implements IInitable {

	protected Vec diffuse;
	protected Vec ambient;
	protected Vec emission;
	protected Vec specular;
	protected double shininess;
    protected double reflectance;

	public Material() {
		diffuse = new Vec(0.7, 0.7, 0.7);
		ambient = new Vec(0.1, 0.1, 0.1);
		specular = new Vec(1, 1, 1);
		emission = new Vec(0, 0, 0);
        reflectance = 0;
		shininess = 100;

	}

	public void init(Map<String, String> attributes) {
		if (attributes.containsKey("mtl-diffuse"))
			diffuse = new Vec(attributes.get("mtl-diffuse"));
		if (attributes.containsKey("mtl-specular"))
			specular = new Vec(attributes.get("mtl-specular"));
		if (attributes.containsKey("mtl-emission"))
			emission = new Vec(attributes.get("mtl-emission"));
		if (attributes.containsKey("mtl-ambient"))
			ambient = new Vec(attributes.get("mtl-ambient"));
		if (attributes.containsKey("mtl-shininess"))
			shininess = Double.valueOf(attributes.get("mtl-shininess"));
        if (attributes.containsKey("reflectance"))
            reflectance = Double.valueOf(attributes.get("reflectance"));
	}
}
