package control;

import model.Cube;
import model.Vector3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tobias Kipp on 2014-10-27.
 */
public class WorldManager {
    private HashMap<String, Boolean> keyPressed;
    private List<Cube> cubes;
    private List<Cube> levelCubes;
    private double time;
    private static double moveSpeed = 1.0;
    private static double angleSpeed = 30.0;

    public static String[] availableKeys = {"left", "right", "up", "down", "page up", "page down", "home", "end",
            "insert", "delete", "minus", "plus"};

    public WorldManager() {
        this.keyPressed = new HashMap<String, Boolean>();
        for (String key : WorldManager.availableKeys) {
            this.keyPressed.put(key, false);
        }
        this.cubes = new ArrayList<Cube>();
        this.levelCubes = new ArrayList<Cube>();
        time = System.currentTimeMillis() / 1000.0;

    }

    public void addCube(Cube cube) {
        this.cubes.add(cube);
    }
    public void addLevelCube(Cube cube) {
        this.levelCubes.add(cube);
    }
    public List<Cube> getCubes(){return this.cubes;}
    public List<Cube> getLevelCubes(){return this.levelCubes;}

    public void update() {
        double timeNew = System.currentTimeMillis() / 1000.0;
        double timeDiff = timeNew - time;
        //translate
        double movedx = 0.0;
        if (this.keyPressed.get("left")) movedx -= moveSpeed;
        if (this.keyPressed.get("right")) movedx += moveSpeed;
        double movedy = 0.0;
        if (this.keyPressed.get("down")) movedy -= moveSpeed;
        if (this.keyPressed.get("up")) movedy += moveSpeed;
        double movedz = 0.0;
        if (this.keyPressed.get("minus")) movedz -= moveSpeed;
        if (this.keyPressed.get("plus")) movedz += moveSpeed;
        //rotate
        double rotatedx = 0.0;
        if (this.keyPressed.get("home")) rotatedx += angleSpeed;
        if (this.keyPressed.get("end")) rotatedx -= angleSpeed;
        double rotatedy = 0.0;
        if (this.keyPressed.get("delete")) rotatedy += angleSpeed;
        if (this.keyPressed.get("page down")) rotatedy -= angleSpeed;
        double rotatedz = 0.0;
        if (this.keyPressed.get("insert")) rotatedz += angleSpeed;
        if (this.keyPressed.get("page up")) rotatedz -= angleSpeed;

        for (Cube cube : this.cubes) {
            //Vector3 translateSpeed = new Vector3(movedx, movedy, 5.0*movedz*1.0);
            //translateSpeed.multiply(timeDiff);
            //cube.move(translateSpeed);
            cube.move(new Vector3(0.0, -0.1*timeDiff, 0.0));
            cube.setVelocity(new Vector3(movedx, movedy, 5.0*movedz));
            cube.update(timeDiff);
            Vector3 rotateSpeed = new Vector3(rotatedx, rotatedy, rotatedz);
            rotateSpeed.multiply(timeDiff);
            cube.rotate(rotateSpeed);
            //Find overlaps with the level and handle them.
            for(Cube levelCube: this.levelCubes){
                if(cube.cubeInCube(levelCube)){
                    System.out.println("Intersection");
                    cube.move(new Vector3(0.0, 0.1, 0.0));//Assuming only gravity is active moving up on collision.
                }
            }
        }
        time = timeNew;
    }

    public void keyPressed(String keyName) {
        this.keyPressed.put(keyName, true);
    }

    public void keyReleased(String keyName) {
        this.keyPressed.put(keyName, false);
    }


}
