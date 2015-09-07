/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import javax.swing.JSlider;

/**
 *
 * @author Mathias
 */
public class JTimeline extends JSlider  implements ValueChangedListener {

    public JTimeline() {
        super();
        setValue(0);
        setMaximum(1000);
    }

    @Override
    public void valueChanged(float newValue) {
        setValue((int) newValue);
    }
    
}
