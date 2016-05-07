/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import effects.Fade;
import entities.GameLabel;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TestFadeLabel {

    public static void main(String[] args) {
        new TestFadeLabel();
    }

    public TestFadeLabel() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new MainPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class MainPane extends JPanel {

        private float direction = 0.05f;
        private GameLabel label = new GameLabel();

        public MainPane() {
            setLayout(new BorderLayout());
            JLabel background = new JLabel();
            background.setLayout(new GridBagLayout());
            try {
                System.out.println(getClass().getResource("/"));
                background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(File.separator+"images"+File.separator+"background.png"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            add(background);

            label = new GameLabel();
            label.setBounds(200, 200, 200, 200);
            background.add(label);
            label.setAlpha(0f);
            Fade.fadeIn(label, 5000);
        }
    }

  
}