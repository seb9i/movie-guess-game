package com.frontend;

import javax.swing.*;
import java.awt.*;

public class PictureFrame extends JFrame implements Runnable {
    JButton button;
    private PicturePanel p;


    public PictureFrame(String display, String url) {
        super(display);
        int frameWidth = 1280;
        int frameHeight = 720;

        setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(500, 0);
        p  = new PicturePanel(url);
        p.setBounds(250, 100, 800, 800);
        this.add(p);




//        button = new JButton("Click Me");
//        button.addActionListener(e -> {
//            String newUrl = "https://image.tmdb.org/t/p/w780/bV0rWoiRo7pHUTQkh6Oio6irlXO.jpg";
//           p.setImageUrl(newUrl);
//       });
//        button.setPreferredSize(new Dimension(50, 50));
//        button.setLocation(0, 800);
//        this.add(button);

        this.setVisible(true);
    }
    public void setImageUrl(String urls){
        p.setImageUrl(urls);
    }

    @Override
    public void run() {

    }
}