package ex3.render.raytrace;

import math.Point3D;
import math.Ray;
import math.Vec;

import java.util.List;
import java.util.Map;

public class TriMesh extends Object3D {
    List<Point3D[]> mesh;

    @Override
    public double nearestIntersection(Ray ray) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vec normalAt(Vec intersection, Ray ray) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public void init(Map<String, String> attributes) {

    }
}
