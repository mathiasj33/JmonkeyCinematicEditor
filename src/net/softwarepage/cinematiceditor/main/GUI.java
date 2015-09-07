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

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jme3tools.converters.ImageToAwt;
import net.softwarepage.cinematiceditor.components.JEventLine;
import net.softwarepage.cinematiceditor.components.JPlayAndPauseButton;
import net.softwarepage.cinematiceditor.components.JTimeline;

/**
 *
 * @author Mathias
 */
public class GUI {

    private Main controller;
    private JFrame frame;
    private JPanel panel;
    private JButton newButton;
    
    public GUI(Main controller, Canvas canvas) {
        this.controller = controller;
        initFrame(canvas);
    }
    
    private void initFrame(Canvas canvas) {
        frame = new JFrame("JME Cinematic Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createPanel(canvas));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
    public void show() {
        frame.setVisible(true);
    }

    private JPanel createPanel(Canvas canvas) {
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(1280, 900));
        panel.add(canvas);
        
        panel.add(createToolbar());
        panel.add(createTimeline(""));
        
        newButton = new JButton("+");
        newButton.setName("newButton");
        newButton.setFont(new Font(newButton.getFont().getName(), Font.BOLD, newButton.getFont().getSize() + 15));
        newButton.addActionListener(controller);
        panel.add(newButton);

        return panel;
    }
    
    private Box createToolbar() {
        Box box = Box.createHorizontalBox();
        JPlayAndPauseButton playButton = new JPlayAndPauseButton(getIcon("Textures/play.png"), getIcon("Textures/pause.png"));
        playButton.setPreferredSize(new Dimension(50, 40));
        playButton.setName("playButton");
        playButton.addActionListener(controller);
        box.add(playButton);
        return box;
    }
    
    private ImageIcon getIcon(String path) {
        com.jme3.texture.Image jmeImg = controller.getAssetManager().loadTexture(path).getImage();
        BufferedImage image = ImageToAwt.convert(jmeImg, false, true, 0);
        Image scaledImage = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    
    private Box createTimeline(String name) {
        Box box = Box.createHorizontalBox();
        JLabel label = new JLabel(name);
        box.add(label);
        JTimeline timeline = new JTimeline();
        timeline.addChangeListener(controller);
        controller.setValueListener(timeline);
        timeline.setPreferredSize(new Dimension(1270 - label.getWidth(), 30));
        box.add(timeline);
        return box;
    }
    
    private JEventLine createEventLine(String name) {
        JEventLine eventLine = new JEventLine();
        return eventLine;
    }
    
    public void onNewButtonClick() {
        JEventLine newTimeline = createEventLine("MotionEvents");
        panel.remove(newButton);
        panel.add(newTimeline);
        panel.add(newButton);
        frame.validate();
    }
    
    public JFrame getJFrame() {
        return frame;
    }
}
