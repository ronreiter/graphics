package ex3.render.raytrace;
import ex3.math.Point3D;

import java.util.ArrayList;
import java.util.List;

public class OctNode {
	private BoundingBox box;
	private OctNode[] subnodes;
	private List<Object3D> objects;
	private final static int MAX_OBJECTS_IN_NODE = 2;
	private Point3D middle;
	private boolean split;

	public OctNode(BoundingBox box) {
		this.box = box;
		this.subnodes = new OctNode[8];
		this.objects = new ArrayList<Object3D>();
		this.middle = box.getMiddle();
		this.split = false;
	}

	private void splitToChildren() {
		split = true;

		for (Object3D previouslyAdded : objects) {
			addObjectToSubNode(previouslyAdded);
		}
	}
	
	public void addChild(Object3D object) {
		objects.add(object);

		if ((objects.size() <= MAX_OBJECTS_IN_NODE) && !split) {
			return;
		} else {

			if (!split) {
				// add all objects in node
				splitToChildren();
				return;
			}
		}

		addObjectToSubNode(object);
	}
	
	public void addObjectToSubNode(Object3D object) {
		BoundingBox objectBoundingBox = object.getBoundingBox();

		for (int i = 0; i < 8; i++) {
			BoundingBox octBox;
			Point3D start = this.box.start.clone();
			Point3D end = this.box.end.clone();

			if ((i & 1) == 0) {
				end.x = middle.x;
			} else {
				start.x = middle.x;
			}
			if ((i & 2) == 0) {
				end.y = middle.y;
			} else {
				start.y = middle.y;
			}
			if ((i & 4) == 0) {
				end.z = middle.z;
			} else {
				start.z = middle.z;
			}

			octBox = new BoundingBox(start, end);

			if (octBox.intersectsWith(objectBoundingBox)) {
				if (this.subnodes[i] == null) {
					this.subnodes[i] = new OctNode(octBox);
				}
				this.subnodes[i].addChild(object);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("{objects : [");
		for (Object3D object : objects) {
			str.append(object.getBoundingBox().getMiddle());
			str.append(",");
		}
		str.append("}, subnodes : {");
		for (int i = 0; i < 8; i++) {
			if (subnodes[i] != null) {
				str.append("subnode" + i + " : " + subnodes[i].toString() + ",");
			}
		}
		str.append("}}");
		
		return str.toString();
	}
}

