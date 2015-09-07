/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import java.awt.Canvas;
import java.awt.Component;
import java.lang.reflect.Field;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author Mathias
 */
public class GUI {

    public static JFrame getJFrame(Main main, Canvas canvas) {
        JFrame frame = new JFrame("JME Cinematic Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createPanel(main, canvas));
        frame.pack();
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private static JPanel createPanel(Main main, Canvas canvas) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(canvas);

        JTimeline timeline = new JTimeline();
        timeline.addChangeListener(main);
        main.setValueListener(timeline);
        panel.add(timeline);

        JButton button = new JButton("Play");
        button.addActionListener(main);
        panel.add(button);

        return panel;
    }
}
