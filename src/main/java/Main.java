import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

import control.KeyboardInputController;
import control.WorldManager;
import model.Cube;
import model.Vector3;
import view.DrawHelper;

public class Main implements GLEventListener {
    private static String TITLE = "JOGL 2 with NEWT";  // window's title
    private static final int WINDOW_WIDTH = 640;  // width of the drawable
    private static final int WINDOW_HEIGHT = 480; // height of the drawable
    private static final int FPS = 60; // animator's target frames per second
    private static Cube cube;
    private double theta = 0;
    private static WorldManager worldManager;

    public static void main(String[] args) {
        worldManager = new WorldManager();
        cube = new Cube(new Vector3(0.0, 0.0, 0.0), new Vector3(1.0, 1.0, 1.0), "white");
        worldManager.addCube(cube);
        // Get the default OpenGL profile, reflecting the best for your running platform
        GLProfile glp = GLProfile.getDefault();
        // Specifies a set of OpenGL capabilities, based on your profile.
        GLCapabilities caps = new GLCapabilities(glp);
        // Create the OpenGL rendering canvas
        GLWindow window = GLWindow.create(caps);

        // Create a animator that drives canvas' display() at the specified FPS.
        final FPSAnimator animator = new FPSAnimator(window, FPS, true);

        window.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent arg0) {
                // Use a dedicate thread to run the stop() to ensure that the
                // animator stops before program exits.
                new Thread() {
                    @Override
                    public void run() {
                        animator.stop(); // stop the animator loop
                        System.exit(0);
                    }
                }.start();
            }

            ;
        });

        window.addGLEventListener(new Main());
        window.addKeyListener(new KeyboardInputController(cube, worldManager));
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setTitle(TITLE);
        window.setVisible(true);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        update();
        render(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    }

    private void update() {
        worldManager.update();
        theta += 0.5;
    }

    private void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glScaled(0.2, 0.2, 0.2);
        DrawHelper drawHelper = new DrawHelper(gl);



        drawHelper.draw(this.cube);
    }
}