package ex3.render.raytrace;

import math.Ray;
import math.Vec;

import java.util.Map;

public class SpotLight extends Light {
    Vec direction;
    
    public SpotLight(Scene scene) {
        super(scene);
        direction = new Vec(0, 0, -1);
    }

    public void init(Map<String, String> attributes) {
        super.init(attributes);
        if (attributes.containsKey("dir")) {
			direction = new Vec(attributes.get("dir"));
			direction.normalize();
		}

    }

	public Vec getIllumination(Hit hit, Ray ray) {
		Vec lightSum = super.getIllumination(hit, ray);
		Vec lightDirection =  new Vec(hit.intersection, this.pos);
		lightDirection.normalize();
		lightSum.scale(lightDirection.dotProd(this.direction));
		
		return lightSum;
	}

}
