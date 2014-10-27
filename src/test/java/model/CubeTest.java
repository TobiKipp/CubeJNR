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
        testCube = new Cube(new Vector3(0.25, 0.25, 0.25), new Vector3(0.0, 0.0 , 0.0), "white");//Zero size cube
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
    }

    @Test
    public void testMove() throws Exception {
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), "white");
        cube.move(new Vector3(1.0, 0.0, 0.0));
        assertEquals(1.0, cube.getPosition().getX(), 0.0);
        assertEquals(0.0, cube.getPosition().getY(), 0.0);
        assertEquals(0.0, cube.getPosition().getZ(), 0.0);
    }

    @Test
    public void testRotate() throws Exception{
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), "white");
        cube.rotate(new Vector3(45.0, 42.0, 41.0));
        assertEquals(45.0, cube.getAngle().getX(), 0.0);
        assertEquals(42.0, cube.getAngle().getY(), 0.0);
        assertEquals(41.0, cube.getAngle().getZ(), 0.0);
    }

    @Test
    public void testRotateX(){
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        cube.rotateX(45.0);
        assertEquals(55.0, cube.getAngle().getX(), 0.0);
    }

    @Test
    public void testRotateY(){
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        cube.rotateY(45.0);
        assertEquals(65.0, cube.getAngle().getY(), 0.0);
    }

    @Test
    public void testRotateZ(){
        Cube cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(10.0, 20.0, 30.0), "white");
        cube.rotateZ(45.0);
        assertEquals(75.0, cube.getAngle().getZ(), 0.0);
    }
}
