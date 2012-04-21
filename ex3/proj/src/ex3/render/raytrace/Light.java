package ex3.render.raytrace;

import java.util.Map;

import math.Point3D;
import math.Vec;


/**
 * Represent a point light
 * 
 */
public class Light {

	protected Point3D pos;
	protected Vec color;
    protected double kc;
    protected double kl;
    protected double kq;    

	public Light() {
		pos = new Point3D(0, 0, 0);
		color = new Vec(1,1,1);
        kc = 0;
        kl = 0.03;
        kq = 0.001;
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
}
