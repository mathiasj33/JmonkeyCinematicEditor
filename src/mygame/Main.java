package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Main app = new Main();
                app.setSettings(createAppSettings());
                app.createCanvas();
                JmeCanvasContext ctx = (JmeCanvasContext) app.getContext();
                ctx.setSystemListener(app);
                Dimension dim = new Dimension(1280, 720);
                ctx.getCanvas().setPreferredSize(dim);

                JFrame frame = GUI.getJFrame(ctx.getCanvas());

                frame.setVisible(true);
                app.startCanvas();
            }
        });
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
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
