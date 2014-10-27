/**
 * Created by Tobias Kipp on 2014-10-23.
 */
package model;

public class Cube {

    private Vector3 position;
    private Vector3 size;
    private String color;
    private Vector3 angle;

    public Cube(Vector3 position, Vector3 size, String color) {
        this.position = position;
        this.size = size;
        this.color = color;
        this.angle = new Vector3(0.0, 0.0, 0.0);
    }

    public Cube(Vector3 position, Vector3 size, Vector3 angle, String color) {
        this.position = position;
        this.size = size;
        this.color = color;
        this.angle = angle;
    }

    /**
     * A point is in a cube if for each dimension on its own the point is in the bounds
     *
     * @param pointV3 The point to test with.
     * @return The point is in the cube.
     */
    public boolean pointInCube(Vector3 pointV3) {
        boolean inCube = true;
        double[] pointArray = pointV3.toArray();
        double[] startArray = this.position.toArray();
        Vector3 endV3 = this.size.copy();
        endV3.add(this.position);
        double[] endArray = endV3.toArray();
        //Check for each dimension if the point is in the bounds.
        for (int i = 0; i < 3; i++) {
            double start = startArray[i];
            double end = endArray[i];
            double point = pointArray[i];
            if (start > end) {
                double temp = start;
                start = end;
                end = temp;
            }
            if (!(start <= point && point <= end)) {
                inCube = false;
            }
        }
        return inCube;
    }

    /**
     * A cube is in another cube if they share any point. This can be done by using at most
     * 3 significant points.
     * <p/>
     * The first is the position of the this cube which could be in the other cube.
     * The second is the position plus the size of this cube compared with the other cube.
     * If the other cube contains this cube then any point of this cube must be in the other cube.
     *
     * @param cube the other cube to compare with.
     * @return The two cube have shared points.
     */
    public boolean cubeInCube(Cube cube) {
        //cube overlaps with this
        boolean inCube = this.pointInCube(cube.getPosition());
        if (!inCube) {
            Vector3 endV3 = cube.getPosition().copy();
            endV3.add(cube.getSize());
            inCube = this.pointInCube(endV3);
        }
        //or cube completely contains this
        if (!inCube) {
            inCube = cube.pointInCube(this.position);
        }
        //or cube is not in this
        return inCube;
    }

    public void move(Vector3 moveVector) {
        this.position.add(moveVector);
    }


    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getSize() {
        return size;
    }

    public void setSize(Vector3 size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void rotate(Vector3 degreeRotation) {
        this.angle.add(degreeRotation);
    }

    public void rotateX(double degrees) {
        this.angle.setX(this.angle.getX() + degrees);
    }

    public void rotateY(double degrees) {
        this.angle.setY(this.angle.getY() + degrees);
    }

    public void rotateZ(double degrees) {
        this.angle.setZ(this.angle.getZ() + degrees);
    }

    public void setAngle(Vector3 angle) {
        this.angle = angle;
    }

    public Vector3 getAngle() {
        return this.angle;
    }
}
