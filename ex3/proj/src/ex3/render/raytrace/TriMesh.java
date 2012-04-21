package ex3.render.raytrace;

import math.Point3D;
import math.Ray;
import math.Vec;

import java.util.ArrayList;
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
        mesh = new ArrayList<Point3D[]>();
        
        for (String attribute : attributes.keySet()) {
            if (attribute.startsWith("tri")) {
                String[] triangleValues = attributes.get(attribute).split("\\s+");
                if (triangleValues.length != 9) {
                    throw new IllegalArgumentException("Triangle must have 9 values representing 3 3d points!");
                }
                Point3D[] tri = new Point3D[3];
                tri[0] = new Point3D(
                        Double.parseDouble(triangleValues[0]),
                        Double.parseDouble(triangleValues[1]),
                        Double.parseDouble(triangleValues[2]));
                tri[1] = new Point3D(
                        Double.parseDouble(triangleValues[3]),
                        Double.parseDouble(triangleValues[4]),
                        Double.parseDouble(triangleValues[5]));
                tri[2] = new Point3D(
                        Double.parseDouble(triangleValues[6]),
                        Double.parseDouble(triangleValues[7]),
                        Double.parseDouble(triangleValues[8]));
                
                mesh.add(tri);
            }
        }
    }
}
