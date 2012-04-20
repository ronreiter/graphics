package math;

/**
 * Created by IntelliJ IDEA.
 * User: ron
 * Date: 4/1/12
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Point3D {
    public double x, y, z;
    
    public Point3D() {
        x = 0;
        y = 0;
        z = 0;
    }
    
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point3D(Point3D point) {
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
    }
    
    public Point3D(Vec vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }
    
    public void add(Point3D point) {
        this.x += point.x;
        this.y += point.y;
        this.z += point.z;
    }
    
    public void sub(Point3D point) {
        this.x -= point.x;
        this.y -= point.y;
        this.z -= point.z;        
    }
    
    public void scale(double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
    }
    
    public void scale(Vec v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
    }


    
    
}