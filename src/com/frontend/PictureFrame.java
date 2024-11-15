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
    GridBagConstraints gbc = new GridBagConstraints();
    public PictureFrame(String display, String url) {
        super(display);
        Insets insets = new Insets(4, 4, 4, 4);
        int frameWidth = 1280;
        int frameHeight = 720;
        button = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(500, 0);
        this.setBackground(new Color(255, 255, 9));

        p = new PicturePanel(url);
        add(p, new GridBagConstraints(1, 1, 3, 3, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH,  insets, 0, 0));




//        button = new JButton("Click Me");
//        button.addActionListener(e -> {
//            String newUrl = "https://image.tmdb.org/t/p/w780/bV0rWoiRo7pHUTQkh6Oio6irlXO.jpg";
//           p.setImageUrl(newUrl);
//       });
//        button.setPreferredSize(new Dimension(50, 50));
//        button.setLocation(0, 800);
//        this.add(button);
        JPanel panel = new JPanel(new GridBagLayout());

        panel.add(new JButton("Mid 1"), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        panel.add(new JButton("Mid 2"), new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        panel.add(new JButton("Mid 3"), new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        panel.add(new JButton("Mid 4"), new GridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        add(panel, new GridBagConstraints(3, 1, 4, 4, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        this.setVisible(true);

    }
    public void setImageUrl(String urls){
        p.setImageUrl(urls);
    }
    public void setQuestionText(String text){
        textAreaOut.setText(text);
    }

    @Override
    public void run() {

    }
}