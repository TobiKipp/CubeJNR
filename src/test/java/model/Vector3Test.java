package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vector3Test {

    @Test
    public void testSet() throws Exception {
        Vector3 vec3 = new Vector3();
        assertEquals(0.0, vec3.getX(), 0.0);
        assertEquals(0.0, vec3.getY(), 0.0);
        assertEquals(0.0, vec3.getZ(), 0.0);
        vec3.set(1.0, 2.0, 3.0);
        assertEquals(1.0, vec3.getX(), 0.0);
        assertEquals(2.0, vec3.getY(), 0.0);
        assertEquals(3.0, vec3.getZ(), 0.0);
        vec3.setX(2.0);
        vec3.setY(4.0);
        vec3.setZ(6.0);
        assertEquals(2.0, vec3.getX(), 0.0);
        assertEquals(4.0, vec3.getY(), 0.0);
        assertEquals(6.0, vec3.getZ(), 0.0);
    }

    @Test
    public void testCopy() throws Exception {
        Vector3 vec3 = new Vector3(new double[]{1.0, 3.0, 5.0});
        Vector3 vec3Copy = vec3.copy();
        assertEquals(vec3.getX(), vec3Copy.getX(), 0.0);
        assertEquals(vec3.getY(), vec3Copy.getY(), 0.0);
        assertEquals(vec3.getZ(), vec3Copy.getZ(), 0.0);
        assertNotEquals(vec3, vec3Copy);
    }

    @Test
    public void testToArray() throws Exception {
        Vector3 vec3 = new Vector3(1.0, 2.0, 3.0);
        double[] expectedArray = new double[]{1.0, 2.0, 3.0};
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedArray[i], vec3.toArray()[i], 0.0);
        }
    }

    @Test
    public void testAdd() throws Exception {
        Vector3 vec1 = new Vector3(1.0, 1.0, 1.0);
        Vector3 vec2 = new Vector3(2.0, 2.0, 2.0);
        Vector3 vec3 = vec1.copy();
        vec3.add(vec2);
        for (int i = 0; i < 3; i++) {
            assertEquals(3.0, vec3.toArray()[i], 0.0);
        }
    }

    @Test
    public void testMultiply() throws Exception {
        Vector3 vec1 = new Vector3(1.0, 1.0, 1.0);
        vec1.multiply(4.12);
        for (int i = 0; i < 3; i++) {
            assertEquals(4.12, vec1.toArray()[i], 0.0);
        }
    }

}