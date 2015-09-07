/* 
 * The MIT License
 *
 * Copyright 2015 Mathias.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.softwarepage.cinematiceditor.main;

import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.cinematic.Cinematic;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.PlayState;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import static com.jme3.texture.Texture.WrapAxis.T;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
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

    private static GUI gui;
    private Cinematic cinematic;
    private ValueChangedListener valueListener;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
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
            app.startCanvas();
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
        JmeCanvasContext ctx = (JmeCanvasContext) getContext();
        gui = new GUI(this, ctx.getCanvas());
        gui.show();

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
        cinematic.setLoopMode(LoopMode.Loop);
        stateManager.attach(cinematic);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (cinematic.getPlayState() == PlayState.Playing) {
            valueListener.valueChanged((cinematic.getTime() / cinematic.getDuration()) * 1000f);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Component component = (Component) ae.getSource();
        switch(component.getName()) {
            case "playButton":
                Runnable runnable;
                if(cinematic.getPlayState() == PlayState.Playing) {
                    runnable = () -> cinematic.pause();
                }
                else {
                    runnable = () -> cinematic.play();
                }
                enqueue(runnable);
                break;
            case "newButton":
                gui.onNewButtonClick();
                break;
        }
    }
    
    private void enqueue(Runnable function) {
        enqueue(() -> {
            function.run();
            return null;
        });
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        JSlider slider = (JSlider) ce.getSource();
        float percentage = slider.getValue() / 1000f;
        float newTime = percentage * cinematic.getDuration();
        enqueue(() -> cinematic.setTime(newTime));
    }

    public void setValueListener(ValueChangedListener valueListener) {
        this.valueListener = valueListener;
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
    }
}
