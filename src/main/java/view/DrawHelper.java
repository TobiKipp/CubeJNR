package view;

import control.WorldManager;
import model.Cube;
import model.Vector3;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * Created by Tobias Kipp on 2014-10-23.
 */
public class DrawHelper {
    private GL2 gl2;

    public DrawHelper(GL2 gl2) {
        this.gl2 = gl2;

    }

    /**
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
     *
     *
     * @param cube The cube to draw
     */
    public void draw(Cube cube) {
        Vector3[] edges = cube.getVertices();

        Vector3 start = cube.getPosition();
        Vector3 size = cube.getSize();
        //for (int i = 0; i < 8; i++) {
        //    edges[i] = start.copy();
        //}
        //edges[1].add(new Vector3(size.getX(), 0.0, 0.0));
        //edges[2].add(new Vector3(0.0, 0.0, size.getZ()));
        //edges[3].add(new Vector3(size.getX(), 0.0, size.getZ()));
        //edges[4].add(new Vector3(0.0, size.getY(), 0.0));
        //edges[5].add(new Vector3(size.getX(), size.getY(), 0.0));
        //edges[6].add(new Vector3(0.0, size.getY(), size.getZ()));
        //edges[7].add(new Vector3(size.getX(), size.getY(), size.getZ()));
        int[][] planes = new int[][]{{0, 1, 3, 2}, {1, 3, 7, 5}, {5, 4, 6, 7}, {4, 0, 2, 6}, {4, 5, 1, 0}, {2, 3, 7, 6}};

        gl2.glPushMatrix();
        //Go to center of the cube
        double[] center = new double[3];
        for(int i = 0; i < 3; i++){
            center[i] = start.toArray()[i] + size.toArray()[i]/2.0;
        }
        //gl2.glLoadIdentity();
        gl2.glTranslated(center[0], center[1], center[2]);
        //Rotate it
        Vector3 angle = cube.getAngle();
        gl2.glRotated(angle.get("X") , 1.0, 0.0, 0.0);
        gl2.glRotated(angle.get("Y") , 0.0, 1.0, 0.0);
        gl2.glRotated(angle.get("Z") , 0.0, 0.0, 1.0);
        //Restore the position
        gl2.glTranslated(-center[0], -center[1], -center[2]);

        for (int i = 0; i < 6; i++) {
            int[] plane = planes[i];
            String color = cube.getColor();

            if(color.equals("white")){
                gl2.glColor4d(1.0, 1.0, 1.0, 1.0);
            }
            gl2.glBegin(GL2.GL_QUADS);
            for (int j = 0; j < 4; j++) {

                if(color.equals("test")) {
                    double dj = ((double)j+1.0)/4.0;
                    if (i == 0) gl2.glColor4d(dj*1.0, dj, 0.0, 1.0);
                    if (i == 1) gl2.glColor4d(0.0, dj*1.0, dj, 1.0);
                    if (i == 2) {
                        if(j==0) gl2.glColor4d(0.0, 0.0, 1.0, 1.0);
                        if(j==1) gl2.glColor4d(0.0, 1.0, 1.0, 1.0);
                        if(j==2) gl2.glColor4d(1.0, 0.0, 0.0, 1.0);
                        if(j==3) gl2.glColor4d(0.0, 1.0, 0.0, 1.0);
                    }
                    if (i == 3) gl2.glColor4d(0.0, dj*1.0, 1.0-dj, 1.0);
                    if (i == 5) gl2.glColor4d(dj*1.0, 0.0, 1.0-dj, 1.0);
                    if (i == 4) gl2.glColor4d(dj*1.0, 1.0-dj, 0.0, 1.0);
                }
                Vector3 edge = edges[plane[j]];
                gl2.glVertex3d(edge.get("X"), edge.get("Y"), edge.get("Z"));
            }
            gl2.glEnd();
        }
        gl2.glPopMatrix();
    }

    public void draw(WorldManager worldManager){
        for(Cube cube: worldManager.getCubes()){
            this.draw(cube);
        }
        for(Cube cube: worldManager.getLevelCubes()){
            this.draw(cube);
        }

    }

}

