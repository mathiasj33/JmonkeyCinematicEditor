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
package net.softwarepage.cinematiceditor.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author Mathias
 */
public class JEventLine extends JPanel {
    
    private JEvent event;
    private int prevX;
    
    public JEventLine() {
        event = new JEvent();
        event.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                prevX = me.getXOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                event.setBackground(Color.black);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                event.setBackgroundToDefault();
            }
        });
        
        event.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent me) {
                int newX = event.getX() + me.getXOnScreen() - prevX;
                if(newX < 0 || newX + event.getWidth() > getWidth()) {
                    return;
                }
                event.setBounds(newX, event.getY(), event.getWidth(), event.getHeight());
                event.setBackground(new Color(210, 210, 210));
                prevX = me.getXOnScreen();
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                //TODO show which object it moves etc.
            }
        });
        this.add(event);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1270, 20);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(210, 210, 210));
        int middle = getHeight() / 2;
        g2d.drawLine(0, middle - 1, getWidth() - 1, middle - 1);
        g2d.drawLine(0, middle + 1, getWidth(), middle + 1);
        g2d.dispose();
    }
}
