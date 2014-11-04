package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CubeTest {

    @Test
    public void testPointInCube() throws Exception {
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), "white");
        //Points inside the cube
        Vector3 testPoint = new Vector3(0.0, 0.0, 0.0);
        assertTrue(cube.pointInCube(testPoint));
        testPoint.set(1.0, 1.0, 0.5);
        assertTrue(cube.pointInCube(testPoint));
        testPoint.set(0.2, 0.1, 0.4);
        assertTrue(cube.pointInCube(testPoint));

        //test all vertices
        testPoint.set(0.0, 0.0, 1.0);
        assertTrue(cube.pointInCube(testPoint));
        testPoint.set(0.0, 1.0, 0.0);
        assertTrue(cube.pointInCube(testPoint));
        testPoint.set(0.0, 1.0, 1.0);
        assertTrue(cube.pointInCube(testPoint));
        testPoint.set(1.0, 0.0, 0.0);
        assertTrue(cube.pointInCube(testPoint));
        testPoint.set(1.0, 0.0, 1.0);
        assertTrue(cube.pointInCube(testPoint));
        testPoint.set(1.0, 1.0, 0.0);
        assertTrue(cube.pointInCube(testPoint));
        testPoint.set(1.0, 1.0, 1.0);
        assertTrue(cube.pointInCube(testPoint));
        //Points outside the Cube
        testPoint.set(-1.0, 1.0, 0.5);
        assertFalse(cube.pointInCube(testPoint));
        testPoint.set(10.0, 1.0, 0.5);
        assertFalse(cube.pointInCube(testPoint));
    }

    @Test
    public void testCubeInCube() throws Exception {
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), "white");
        //A cube should contain itself
        assertTrue(cube.cubeInCube(cube));
        //A few cubes that should have shared points with the first cube
        Cube testCube = new Cube(new Vector3(-1.0, -1.0, -1.0), new Vector3(2.0, 2.0, 2.0), "white");//-1 to +1
        assertTrue(cube.cubeInCube(testCube));
        testCube = new Cube(new Vector3(0.25, 0.25, 0.25), new Vector3(0.5, 0.5, 0.5), "white");//0.25 to 0.75
        assertTrue(cube.cubeInCube(testCube));
        testCube = new Cube(new Vector3(0.25, 0.25, 0.25), new Vector3(1.75, 1.75, 1.75), "white");//0.25 to 2.0
        assertTrue(cube.cubeInCube(testCube));
        testCube = new Cube(new Vector3(0.25, 0.25, 0.25), new Vector3(0.0, 0.0, 0.0), "white");//Zero size cube
        assertTrue(cube.cubeInCube(testCube));
        //A few cubes outside
        testCube = new Cube(new Vector3(-1.0, -1.0, -1.0), new Vector3(0.9, 0.9, 0.9), "white");
        assertFalse(cube.cubeInCube(testCube));
        testCube = new Cube(new Vector3(-1.0, -1.0, -1.0), new Vector3(1.0, 1.0, 0.9), "white");
        assertFalse(cube.cubeInCube(testCube));
        testCube = new Cube(new Vector3(-1.0, -1.0, -1.0), new Vector3(1.0, 0.9, 1.0), "white");
        assertFalse(cube.cubeInCube(testCube));
        testCube = new Cube(new Vector3(-1.0, -1.0, -1.0), new Vector3(0.9, 1.0, 1.0), "white");
        assertFalse(cube.cubeInCube(testCube));
        testCube = new Cube(new Vector3(2.0, -1.0, -1.0), new Vector3(10.9, 11.0, 11.0), "white");
        assertFalse(cube.cubeInCube(testCube));

        //The 8 vertices slightly touched.
        //To not get into the other cube if the component is at 0 the touching cube must expand to -1 and
        //for component at 1 then it must go to 2 (considering using a 1x1x1 cube). The size of the cube should
        //not be negative to have a consistent plane order.
        //The diagonal in format start -> end can be swapped out by another diagonal swapping the components.
        //e.g. -1, -1, 2 -> 0, 0, 1 would have a size of 1, 1, -1. The diagonal -1, -1, 1 -> 0, 0, 2 describes the
        //same cube.
        //
        //In summary if the edge (x,y,z) should be only touched with a 1x1x1 cube with no negative size
        //then the position for each component is -1 for 0 and 1 for 1

        //000
        testCube = new Cube(new Vector3(-1.0, -1.0, -1.0), new Vector3(1.0, 1.0, 1.0), "white");
        assertTrue(cube.cubeInCube(testCube));
        //001
        testCube = new Cube(new Vector3(-1.0, -1.0, 1.0), new Vector3(1.0, 1.0, 1.0), "white");
        assertTrue(cube.cubeInCube(testCube));
        //010
        testCube = new Cube(new Vector3(-1.0, 1.0, -1.0), new Vector3(1.0, 1.0, 1.0), "white");
        assertTrue(cube.cubeInCube(testCube));
        //011
        testCube = new Cube(new Vector3(-1.0, 1.0, 1.0), new Vector3(1.0, 1.0, 1.0), "white");
        assertTrue(cube.cubeInCube(testCube));
        //100
        testCube = new Cube(new Vector3(1.0, -1.0, -1.0), new Vector3(1.0, 1.0, 1.0), "white");
        assertTrue(cube.cubeInCube(testCube));
        //101
        testCube = new Cube(new Vector3(1.0, -1.0, 1.0), new Vector3(1.0, 1.0, 1.0), "white");
        assertTrue(cube.cubeInCube(testCube));
        //110
        testCube = new Cube(new Vector3(1.0, 1.0, -1.0), new Vector3(1.0, 1.0, 1.0), "white");
        assertTrue(cube.cubeInCube(testCube));
        //111
        testCube = new Cube(new Vector3(1.0, 1.0, 1.0), new Vector3(1.0, 1.0, 1.0), "white");
        assertTrue(cube.cubeInCube(testCube));

        //Two overlapping cubes where the edges of neither are in the other.
        // 0.25 to 0.75 for x and z leads to them being inside the cube. To get them outside the other
        // cube the y is chosen to be outside of the 1x1x1 cubes y line.
        testCube = new Cube(new Vector3(0.25, -1.0, 0.25), new Vector3(0.5, 3.0, 0.5), "white");
        assertTrue(cube.cubeInCube(testCube));

    }

    @Test
    public void testMove() throws Exception {
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), "white");
        cube.move(new Vector3(1.0, 0.0, 0.0));
        assertEquals(1.0, cube.getPosition().get("X"), 0.0);
        assertEquals(0.0, cube.getPosition().get("Y"), 0.0);
        assertEquals(0.0, cube.getPosition().get("Z"), 0.0);
    }

    @Test
    public void testRotate() throws Exception {
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), "white");
        cube.rotate(new Vector3(45.0, 42.0, 41.0));
        assertEquals(45.0, cube.getAngle().get("X"), 0.0);
        assertEquals(42.0, cube.getAngle().get("Y"), 0.0);
        assertEquals(41.0, cube.getAngle().get("Z"), 0.0);
    }

    @Test
    public void testRotateX() {
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        cube.rotate("X", 45.0);
        assertEquals(55.0, cube.getAngle().get("X"), 0.0);
    }

    @Test
    public void testRotateY() {
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        cube.rotate("Y", 45.0);
        assertEquals(65.0, cube.getAngle().get("Y"), 0.0);
    }

    @Test
    public void testRotateZ() {
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        cube.rotate("Z", 45.0);
        assertEquals(75.0, cube.getAngle().get("Z"), 0.0);
    }

    @Test
    public void testGetVertices() {
        // The order according to the cube table is xyz [000, 100, 001, 101, 010, 110, 011, 111
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        Vector3[] vertices = cube.getVertices();
        double[][] expected = new double[][]{{0.0, 0.0, 0.0},
                {1.0, 0.0, 0.0},
                {0.0, 0.0, 1.0},
                {1.0, 0.0, 1.0},
                {0.0, 1.0, 0.0},
                {1.0, 1.0, 0.0},
                {0.0, 1.0, 1.0},
                {1.0, 1.0, 1.0},
        };
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 3; i++) {
                assertEquals(expected[j][i], vertices[j].toArray()[i] , 0.0);
            }
        }

    }

    @Test
    public void testGetEnd(){
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        Vector3 end = cube.getEnd();
        for(int i = 0; i < 3; i++){
            assertEquals(1.0, end.toArray()[i], 0.0);
        }
    }

    @Test
    public void testRun(){
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        cube.prepareMove(1.0, 70.0, new Vector3(0.0, 0.0, 0.0));//ry = 90 und t = 1s => x = 1.0 and  z = 0.0
        cube.update(1.0);//move for 1 second
        assertEquals(1.0, cube.getPosition().get("X"), 1.0E-15);//due to rounding there might be minor errors.
        assertEquals(0.0, cube.getPosition().get("Z"), 1.0E-15);
    }

    @Test
    public void testGenerateMoveCube(){
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        cube.setVelocity(new Vector3(1.0, -1.0, 0.0));
        //The cube pos (0,0,0) size (1,1,1) is moved by (1,-1,1). The moveCube should be pos (0,-1,0) size (2,2,1)
        Cube expectedMoveCube = new Cube(new Vector3(0.0, -1.0, 0.0), new Vector3(2.0, 2.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        Cube moveCube = cube.generateMoveCube(1.0);
        assertTrue(expectedMoveCube.similar(moveCube));
        moveCube = cube.generateMoveCube(0.5);
        //Position (0, 0, 0) with length (1, 1, 1) extended by (0.5, -0.5, 0.0) results in (0, -0.5, 0) with length (1.5, 1.5, 1)
        expectedMoveCube = new Cube(new Vector3(0.0, -0.5, 0.0), new Vector3(1.5, 1.5, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        assertTrue(expectedMoveCube.similar(moveCube));
    }

    @Test
    public void testHandleIntersection(){
        Cube cube1 = new Cube(new Vector3(), new Vector3(1.0, 1.0, 1.0), "white");
        Cube cube2 = new Cube(new Vector3(2.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), "white");
        cube1.setVelocity(new Vector3(1.0, 0.0, 0.0));
        cube1.handleIntersection(cube2, cube1.generateMoveCube(1.0), 1.0);
        cube1.update(1.0);
        assertEquals(1.0, cube1.getPosition(0), 1E-10);
        assertEquals(1.0, cube1.getVelocity(0), 1E-10);
        //Moving cube2 to the left should not change the position.
        cube2.setVelocity(new Vector3(-100.0, 0.0, 0.0));
        cube2.handleIntersection(cube1, cube2.generateMoveCube(1.0), 1.0);
        cube2.update(1.0);
        assertEquals(2.0, cube2.getPosition(0), 1E-10);
        assertEquals(0.0, cube2.getVelocity(0), 1E-10);
        //Now move cube2 for half a second to the right with vx = 1.0. Handle intersection should
        //not change anything.


        cube2.setVelocity(new Vector3(1.0, 0.0, 0.0));
        cube2.handleIntersection(cube1, cube2.generateMoveCube(0.5), 0.5);
        System.out.println(cube2);
        cube2.update(0.5);
        System.out.println(cube2);
        assertEquals(2.5, cube2.getPosition(0), 1E-10);
        assertEquals(1.0, cube2.getVelocity(0), 1E-10);
    }

}
