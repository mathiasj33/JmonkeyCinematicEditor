package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.cinematic.Cinematic;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication implements ActionListener, ChangeListener {

    private Cinematic cinematic;
    private ValueChangedListener valueListener;
    private boolean playing;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main app = new Main();
                app.setSettings(createAppSettings());
                app.setPauseOnLostFocus(false);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }
                app.createCanvas();
                JmeCanvasContext ctx = (JmeCanvasContext) app.getContext();
                ctx.setSystemListener(app);
                Dimension dim = new Dimension(1280, 720);
                ctx.getCanvas().setPreferredSize(dim);

                JFrame frame = GUI.getJFrame(app, ctx.getCanvas());
                
                frame.setVisible(true);
                app.startCanvas();
            }
        }
        );
    }

    private static AppSettings createAppSettings() {
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setVSync(true);
        return settings;
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);

        cinematic = new Cinematic(rootNode, 5);
        MotionPath path = new MotionPath();
        path.addWayPoint(Vector3f.ZERO.clone());
        path.addWayPoint(new Vector3f(10, 0, 0));
        MotionEvent motionEvent = new MotionEvent(geom, path, 5);
        cinematic.addCinematicEvent(0, motionEvent);
        cinematic.fitDuration();
        stateManager.attach(cinematic);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(playing) {
            valueListener.valueChanged((cinematic.getTime() / cinematic.getDuration()) * 1000f);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        playing = true;
        enqueue(() -> {
            cinematic.play();
            return null;
        });
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        JSlider slider = (JSlider) ce.getSource();
        float percentage = slider.getValue() / 1000f;
        float newTime = percentage * cinematic.getDuration();
        enqueue(() -> {
            cinematic.setTime(newTime);
            return null;
        });
    }

    public void setValueListener(ValueChangedListener valueListener) {
        this.valueListener = valueListener;
    }
}
