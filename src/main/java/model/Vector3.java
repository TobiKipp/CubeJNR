package model;
/**
 * Created by Tobias Kipp on 2014-10-23.
 * Vector3 is used to store a 3 Dimensional property.
 * It can go from a position to a vector or used for a velocity.
 */
public class Vector3 {

    private double x;
    private double y;
    private double z;

    /**
     * Default constructor creating the 0 vector.
     */
    public Vector3(){
        this.set(0.0, 0.0, 0.0);
    }

    /**
     * Constructor with components.
     * @param x The x-dimension value
     * @param y The y-dimension value
     * @param z The z-dimension value
     */
    public Vector3(double x, double y, double z){
        this.set(x, y, z);
    }

    /**
     * Constructor with array.
     * @param xyz Array in order x, y, z
     * @throws Exception The array must have exactly 3 double values.
     */
    public Vector3(double[] xyz) throws Exception {
        if(xyz.length != 3){
            throw new Exception("Vector3 requires exactly 3 double values.");
        }
        this.set(xyz[0], xyz[1], xyz[2]);

    }

    /**
     * Turn the Vector3 into an array with order x, y, z.
     * @return The Vector3 as double array representation
     */
    public double[] toArray(){
        return new double[]{this.x, this.y, this.z};
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Set the Vector3 values at once.
     * @param x The x-dimension value
     * @param y The y-dimension value
     * @param z The z-dimension value
     */
    public void set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     * @return A copy of this Vector3 object
     */
    public Vector3 copy(){
        return new Vector3(this.x, this.y, this.z);
    }

    /**
     * Adds the other Vector3 to this, by adding separately in each dimension.
     * @param otherVector3 The Vector3 which is added.
     */
    public void add(Vector3 otherVector3) {
        this.x += otherVector3.x;
        this.y += otherVector3.y;
        this.z += otherVector3.z;
    }

    /**
     * Multiplies each component with the same factor
     * @param factor the factor to multiply each component with.
     */
    public void multiply(double factor){
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
    }
}
