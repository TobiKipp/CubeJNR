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
    private static double moveSpeed = 5.0;
    private static double angleSpeed = 90.0;

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

        double playerSpeedAbsolute = timeDiff*moveSpeed;
        double playerSpeed = 0.0;
        if (this.keyPressed.get("down")) playerSpeed += playerSpeedAbsolute;
        if (this.keyPressed.get("up")) playerSpeed -= playerSpeedAbsolute;

        double playerRotateAbsoulte = timeDiff*angleSpeed;
        double playerRotate= 0.0;
        if (this.keyPressed.get("left")) playerRotate += playerRotateAbsoulte;
        if (this.keyPressed.get("right")) playerRotate -= playerRotateAbsoulte;


        for (Cube cube : this.cubes) {
            //Vector3 translateSpeed = new Vector3(movedx, movedy, 5.0*movedz*1.0);
            //translateSpeed.multiply(timeDiff);
            //cube.move(translateSpeed);
            cube.run(playerSpeed, playerRotate);
            cube.move(new Vector3(0.0, -0.4*timeDiff, 0.0));
            //Find overlaps with the level and handle them.
            for(Cube levelCube: this.levelCubes){
                if(cube.cubeInCube(levelCube)){
                    Vector3 pos = cube.getPosition();
                    double cubey = levelCube.getPosition().getY() + levelCube.getSize().getY();
                    cube.setPosition(new Vector3(pos.getX(),  cubey, pos.getZ()));//Assuming only gravity is active moving up on collision.
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
