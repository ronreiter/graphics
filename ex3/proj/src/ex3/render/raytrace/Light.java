package ex3.render.raytrace;

import java.util.Map;

import raycaster.math.Vec;


/**
 * Represent a point light
 * 
 */
public class Light {

	protected Vec pos;
	protected Vec color;

	public Light() {
		pos = new Vec(0, 0, 0);
		color = new Vec(1,1,1);
	}

	public void init(Map<String, String> attributes) {
		if (attributes.containsKey("pos"))
			pos = new Vec(attributes.get("pos"));
		if (attributes.containsKey("color"))
			color = new Vec(attributes.get("color"));		
	}
}
