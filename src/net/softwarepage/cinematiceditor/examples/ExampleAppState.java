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
package net.softwarepage.cinematiceditor.examples;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.cinematic.Cinematic;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBase;
import java.text.ParseException;

/**
 *
 * @author Mathias
 */
public class ExampleAppState extends BaseAppState {

    @Override
    protected void initialize(Application app) {
        CinematicLoader loader = new CinematicLoader();
        setupScene();
        Camera camera1 = new Camera();
        camera1.lookAt(Vector3f.NAN, Vector3f.UNIT_X);
        loader.register("plane", plane);
        loader.register("Cam1", camera1);
        loader.registerEvents(MotionEvent.class, ParticleEvent.class);
        try {
            Cinematic cinematic = loader.load("Scenes/cinematic.j3c");
        } catch(ParseException e) {
            
        }
        cinematic.play();
    }
    
    private void setupScene() {
        rootNode.attachChild(plane);
        plane.setLocalTranslation(50, 0, 0);
        plane.addParticles();
        initTerrain();
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
    
}
