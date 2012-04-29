from xml.dom import minidom
import sys

data = minidom.parse(sys.argv[1])

indices = data.getElementsByTagName("IndexedFaceSet")[0].getAttribute("coordIndex").split()
coordinates = data.getElementsByTagName("Coordinate")[0].getAttribute("point").split()

indices_int = [int(x) for x in indices]
coordinates_float = [float(x) for x in coordinates]

def get_coords(i):
	return coordinates_float[i * 3:i * 3+3]

for i in xrange(0, len(indices_int), 4):
	tri_data = get_coords(indices_int[i]) + get_coords(indices_int[i+1]) + get_coords(indices_int[i+2])
	print 'tri%s = "%s"' % (i, " ".join([str(x) for x in tri_data]))
