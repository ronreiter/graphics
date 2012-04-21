package ex3.render.raytrace;

import math.Vec;

import java.util.Map;

public class SpotLight extends Light {
    Vec direction;
    
    public SpotLight() {
        super();
        direction = new Vec(0, 0, -1);
    }

    public void init(Map<String, String> attributes) {
        super.init(attributes);
        if (attributes.containsKey("dir"))
            direction = new Vec(attributes.get("dir"));
    }
}
