/**
 * Created by Tobias Kipp on 2014-10-23.
 *
 * The cube as table and ascii art
 *
 * +------+---+---+---+
 * | edge | x | y | z |
 * +======+===+===+===+                                       6-------7
 * |  0   | 0 | 0 | 0 |                                       |       |
 * +------+---+---+---+                                       |   6   |
 * |  1   | 1 | 0 | 0 |      6-----7                          |       |
 * +------+---+---+---+     /|    /|     z  y         6-------2-------3-------7-------6
 * |  2   | 0 | 0 | 1 |    2-----3 |     ^  ^         |       |       |       |       |
 * +------+---+---+---+    | 4---|-5     | /          |   4   |   1   |   2   |   3   |
 * |  3   | 1 | 0 | 1 |    |/    |/      |/           |       |       |       |       |
 * +------+---+---+---+    0-----1       ----->x      4-------0-------1-------5-------4
 * |  4   | 0 | 1 | 0 |                                       |       |
 * +------+---+---+---+                                       |   5   |
 * |  5   | 1 | 1 | 0 |                                       |       |
 * +------+---+---+---+                                       4-------5
 * |  6   | 0 | 1 | 1 |
 * +------+---+---+---+
 * |  7   | 1 | 1 | 1 |
 * +------+---+---+---+
 */
package model;

public class Cube {

    private Vector3 position;
    private Vector3 size;
    private String color;
    private Vector3 angle;
    private Vector3 velocity;

    public Cube(Vector3 position, Vector3 size, String color) {
        this.init(position, size, new Vector3(0.0, 0.0, 0.0), new Vector3(0.0, 0.0, 0.0), color);
    }

    public Cube(Vector3 position, Vector3 size, Vector3 angle, String color) {
        this.init(position, size, angle, new Vector3(0.0, 0.0, 0.0), color);
    }

    public Cube(Vector3 position, Vector3 size, Vector3 angle, Vector3 velocity, String color) {
        this.init(position, size, angle, velocity, color);
    }

    public void init(Vector3 position, Vector3 size, Vector3 angle, Vector3 velocity, String color) {
        this.position = position;
        this.size = size;
        this.angle = angle;
        this.velocity = velocity;
        this.color = color;
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
     * Either one of the 8 vertices of this cube is in the other cube or any point of the other cube is in
     * this cube. The later case occurs when the other cube is completely inside this cube.
     *
     * @param cube the other cube to compare with.
     * @return The two cube have shared points.
     */
    public boolean cubeInCube(Cube cube) {
        boolean inCube = this.pointInCube(cube.getPosition());
        if (!inCube) {
            Vector3[] vertices = this.getVertices();
            for (int i = 0; i < 8; i++) {
                inCube = cube.pointInCube(vertices[i]);
                if (inCube) break;
            }
        }
        return inCube;
    }

    /**
     * Get the vertices in the order specified in the table at the top.
     *
     * @return The vertices of the cube.
     */
    public Vector3[] getVertices() {
        Vector3[] vertices = new Vector3[8];
        for (int i = 0; i < 8; i++) {
            vertices[i] = this.position.copy();
        }
        vertices[1].add(new Vector3(this.size.getX(), 0.0, 0.0));
        vertices[2].add(new Vector3(0.0, 0.0, this.size.getZ()));
        vertices[3].add(new Vector3(this.size.getX(), 0.0, this.size.getZ()));
        vertices[4].add(new Vector3(0.0, this.size.getY(), 0.0));
        vertices[5].add(new Vector3(this.size.getX(), this.size.getY(), 0.0));
        vertices[6].add(new Vector3(0.0, this.size.getY(), this.size.getZ()));
        vertices[7].add(new Vector3(this.size.getX(), this.size.getY(), this.size.getZ()));
        return vertices;
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

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public void accellerate(Vector3 acceleration) {
        this.velocity.add(acceleration);
    }

    public void update(double timeDelta) {
        Vector3 dMove = this.velocity.copy();
        dMove.multiply(timeDelta);
        this.position.add(dMove);
    }
}
