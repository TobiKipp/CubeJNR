package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vector3Test {

    @Test
    public void testSetAndGet() throws Exception {
        Vector3 vec3 = new Vector3();//The default is the 0 vector
        for(int i = 0; i < 3; i++) {
            assertEquals(0.0, vec3.get(i), 0.0);
        }
        vec3.set(1.0, 2.0, 3.0);
        for(int i = 0; i < 3; i++) {
            assertEquals((float)i+1.0, vec3.get(i), 0.0);
        }
        vec3.set("X", 2.0);
        vec3.set("Y", 4.0);
        vec3.set(2, 6.0);
        for(int i = 0; i < 3; i++) {
            assertEquals((float)(i+1)*2.0, vec3.get(i), 0.0);
        }
        //The getter for dimension in String and Integer format should be equal.
        assertEquals(vec3.get(0), vec3.get("x"), 0.0);
        assertEquals(vec3.get(1), vec3.get("Y"), 0.0);
        assertEquals(vec3.get(2), vec3.get("z"), 0.0);
    }

    @Test
    public void testCopy() throws Exception {
        Vector3 vec3 = new Vector3(new double[]{1.0, 3.0, 5.0});
        Vector3 vec3Copy = vec3.copy();
        for(int i = 0; i < 3; i++){
        assertEquals(vec3.get(i), vec3Copy.get(i), 0.0);

        }
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