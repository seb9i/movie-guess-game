package com.frontend;

import javax.swing.*;
import java.awt.*;

public class PictureFrame extends JFrame implements Runnable {
    JButton button;
    JButton button2;
    JButton button3;
    JButton button4;

    private PicturePanel p;
    JLabel infoLabel;
    JTextArea textAreaOut;
    public PictureFrame(String display, String url) {
        super(display);
        Insets insets = new Insets(4, 4, 4, 4);
        int frameWidth = 1280;
        int frameHeight = 720;

        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setLocation(500, 0);
        setBackground(new Color(255, 255, 9));

        p = new PicturePanel(url);
        p.setPreferredSize(new Dimension(500, 500));  // Set the preferred size for the panel

        this.add(p, new GridBagConstraints(1, 0, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, insets, 0, 0));
        JPanel panel2 = new JPanel();
        infoLabel = new JLabel();
        infoLabel.setText("hi");

        panel2.add(infoLabel);
        this.add(panel2, new GridBagConstraints(1, 1, 3, 1, 1.0, 1.0, GridBagConstraints.SOUTH,
                GridBagConstraints.BOTH, insets, 0, 200));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(new JButton("Mid 1"), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        buttonPanel.add(new JButton("Mid 2"), new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        buttonPanel.add(new JButton("Mid 3"), new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        buttonPanel.add(new JButton("Mid 4"), new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        add(buttonPanel, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        // Refresh and show frame
        revalidate();
        repaint();
        setVisible(true);

    }
    public void setImageUrl(String urls){
        p.setImageUrl(urls);
    }
    public void setQuestionText(String text){
        infoLabel.setText(text);
    }

    @Override
    public void run() {

    }
}