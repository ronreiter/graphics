package ex3.render.raytrace;

import ex3.math.Point3D;
import ex3.math.Vec;

public class BoundingBox {
	public Point3D start;
	public Point3D end;
	
	public BoundingBox(Point3D start, Point3D end) {
		this.start = start;
		this.end = end;
	}

	/**
	 *create a new bounding box from two other bounding boxes which encapsulates both
	 *
	 * @param a the first bounding box
	 * @param b the second bounding box
	 */
	public BoundingBox(BoundingBox a, BoundingBox b) {
		start = minPoint(a.start, b.start);
		end = maxPoint(a.end, b.end);
	}
	
	public boolean intersectsWith(BoundingBox box) {
		return (intersectsWith(box.start) || intersectsWith(box.end));
	}
	
	public boolean intersectsWith(Point3D point) {
		return (point.x > start.x && point.y > start.y && point.z > start.z &&
				point.x < end.x && point.y < end.y && point.z < end.z);
	}
	
	public Point3D getMiddle() {
		return new Point3D(
				(start.x + end.x) / 2,
				(start.y + end.y) / 2,
				(start.z + end.z) / 2
		);
	}

	/**
	 * get the size of the bounding box
	 * @return
	 */
	public double size() {
		Vec sizeVector = new Vec(end, start);
		return sizeVector.length();
	}

	private Point3D minPoint(Point3D a, Point3D b) {
		return new Point3D(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z));
	}

	private Point3D maxPoint(Point3D a, Point3D b) {
		return new Point3D(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z));
	}


}
