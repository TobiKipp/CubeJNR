package model;

import java.util.HashMap;

/**
 * Created by Tobias Kipp on 2014-10-23.
 * Vector3 is used to store a 3 Dimensional property.
 * It can go from a position to a vector or used for a velocity.
 */
public class Vector3 {

    private double[] xyz;
    private static HashMap<String, Integer> xyzmap = new HashMap<String, Integer>();
    static {
        xyzmap.put("x", 0);
        xyzmap.put("X", 0);
        xyzmap.put("y", 1);
        xyzmap.put("Y", 1);
        xyzmap.put("z", 2);
        xyzmap.put("Z", 2);
    }
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
     * Set the Vector3 values at once.
     * @param x The x-dimension value
     * @param y The y-dimension value
     * @param z The z-dimension value
     */
    public void set(double x, double y, double z){
        this.xyz = new double[]{x, y, z};
    }
    /**
     * Turn the Vector3 into an array with order x, y, z. It is a copy.
     * @return The Vector3 as double array representation
     */
    public double[] toArray(){
        return this.xyz.clone();
    }

    /**
     *
     * @return The Vector3 which can be used to modify the Object. (It is not a copy like in toArray)
     */
    public double[] get() {
        return this.xyz;
    }

    /**
     *
     * @param dimension the number of the dimension ([0, 1, 2])
     * @return The value of the dimension
     */
    public double get(int dimension){
        return this.xyz[dimension];
    }

    /**
     *
     * @param dimension one of ["x", "y", "z"] (case-insensitive)
     * @return The value of the dimension
     */
    public double get(String dimension){
        return this.get(xyzmap.get(dimension));
    }

    /**
     *
     * @param dimension the number of the dimension ([0, 1, 2])
     * @param value The value the dimension shall have.
     */
    public void set(int dimension, double value){
        this.xyz[dimension] = value;
    }
    /**
     *
     * @param dimension one of ["x", "y", "z"] (case-insensitive)
     * @param value The value the dimension shall have.
     */
    public void set(String dimension, double value) {
        this.set(xyzmap.get(dimension), value);
    }

     /**
     *
     * @return A copy of this Vector3 object
     */
    public Vector3 copy(){
        try {
            return new Vector3(this.xyz);
        }
        catch (Exception e) {//Should never occur, when copying.
            return null;
        }
    }

    /**
     * Adds the other Vector3 to this, by adding separately in each dimension.
     * @param otherVector3 The Vector3 which is added.
     */
    public void add(Vector3 otherVector3) {
        for(int i = 0; i < 3; i++){
            this.xyz[i] += otherVector3.get(i);
        }
    }

    /**
     * Multiplies each component with the same factor
     * @param factor the factor to multiply each component with.
     */
    public void multiply(double factor){
        for(int i = 0; i < 3; i++){
            this.xyz[i] *= factor;
        }
    }

    public String toString(){
        return "(" + this.xyz[0] + ", " + this.xyz[1] + ", "+ this.xyz[2] + ")";
    }
}
