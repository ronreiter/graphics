package ex3.render.raytrace;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import ex3.parser.Element;
import ex3.parser.SceneDescriptor;
import ex3.render.IRenderer;
import math.Ray;
import math.Vec;

public class RayTracer implements IRenderer {

    protected int width;
    protected int height;
    protected Scene scene;
    protected Camera camera;

    /**
     * Inits the renderer with scene description and sets the target canvas to
     * size (width X height). After init renderLine may be called
     *
     * @param sceneDesc
     *            Description data structure of the scene
     * @param width
     *            Width of the canvas
     * @param height
     *            Height of the canvas
     * @param path
     *            File path to the location of the scene. Should be used as a
     *            basis to load external resources (e.g. background image)
     */
    @Override
    public void init(SceneDescriptor sceneDesc, int width, int height, File path) {
        this.width = width;
        this.height = height;
        scene = new Scene(width, height);
        camera = new Camera(width, height);

        scene.init(sceneDesc.getSceneAttributes());
        camera.init(sceneDesc.getCameraAttributes());

        scene.setCamera(camera);
		scene.loadResources(path);

        for (Element e : sceneDesc.getObjects()) {
            scene.addObjectByName(e.getName(), e.getAttributes());
        }
    }

    /**
     * Renders the given line to the given canvas. Canvas is of the exact size
     * given to init. This method must be called only after init.
     *
     * @param canvas
     *            BufferedImage containing the partial image
     * @param line
     *            The line of the image that should be rendered.
     */
    @Override
    public void renderLine(BufferedImage canvas, int line) {
		for (int x = 0; x < width; ++x) {
			Color rayColor = castRay(x, height - line - 1);
           	canvas.setRGB(x, line, rayColor.getRGB());
        }
    }

    /**
     * Compute color for given image coordinates (x,y)
     *
     * @param x the x coordinate of the view port
     * @param y the y coordinate of the view port
     * @return Color at coordinate
     */
    protected Color castRay(int x, int y) {
    	Vec averageColor = new Vec();
		double stepSize = (double)1 / (double)scene.superSamplingWidth;
		double scaleDown = (double)1 / ((double)scene.superSamplingWidth * (double)scene.superSamplingWidth);
    	for (double i = (double)x; i < (double)(x + 1); i += stepSize) {
    		for (double j = (double)y; j < (double)(y + 1); j += stepSize) {
    	        Ray ray = scene.camera.constructRayThroughPixel(i, j);
    	        Hit hit = scene.findIntersection(ray);

    			averageColor.add(scene.calcColor(hit, ray, x, y, 4));
    		}
    	}
    	averageColor.scale(scaleDown);
    	
        return averageColor.toColor();
    }
}
