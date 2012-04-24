package ex3.render.raytrace;

import math.Point3D;
import math.Ray;
import math.Vec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TriMesh extends Object3D {
	List<Triangle> mesh;

	@Override
	public Hit nearestIntersection(Ray ray) {
		double minDistance = Double.POSITIVE_INFINITY;
		Object3D nearestTri = null;
		Point3D nearestIntersection = null;
		

		for (Triangle tri : mesh) {

			Point3D intersectionPoint = tri.planeIntersection(ray);

			if (intersectionPoint != null) {
				double length = new Vec(intersectionPoint, ray.origin).length();
				length = length / ray.direction.length();

				if (length < minDistance && tri.intersectTri(intersectionPoint, ray)) {			 
					minDistance = length;
					nearestTri = tri;
					nearestIntersection = intersectionPoint;
				}

			}
		}
		return new Hit( nearestIntersection, nearestTri, minDistance);
	}


	@Override
	public Vec normalAt(Point3D intersection, Ray ray) {
		for (Triangle tri : mesh) {
			if (tri.intersectTri(intersection, ray)) {
				Vec v1 = new Vec(tri.getP1(), tri.getP0());
				Vec v2 = new Vec(tri.getP2(), tri.getP0());
				return Vec.crossProd(v1, v2);
			}
		}
		return null;
	}

	public void init(Map<String, String> attributes) {
		super.init(attributes);

		mesh = new ArrayList<Triangle>();

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

				mesh.add(new Triangle(tri, this.material));
			}
		}


	}

	

	


}
