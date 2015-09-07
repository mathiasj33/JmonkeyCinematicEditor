/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import java.awt.Canvas;
import java.awt.Desktop;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author Mathias
 */
public class GUI {

    public static JFrame getJFrame(Canvas canvas) {
        JFrame frame = new JFrame("JME Cinematic Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createPanel(canvas));
        frame.pack();
        frame.setLocationRelativeTo(null);
        return frame;
    }
    
    private static JPanel createPanel(Canvas canvas) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        panel.add(canvas);
        
        JSlider slider = new JSlider(0);
        panel.add(slider);
        
        return panel;
    }
}
