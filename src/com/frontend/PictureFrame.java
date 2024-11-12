package com.frontend;

import javax.swing.JFrame;

public class PictureFrame extends JFrame implements Runnable {

    private PicturePanel p;
    private Thread windowThread;

    public PictureFrame(String display, String url) {
        super(display);
        int frameWidth = 1280;
        int frameHeight = 720;
        p = new PicturePanel(url);
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(0, 0);
        this.setVisible(true);
        startThread();

    }

    public void startThread() {
        windowThread = new Thread(this);
        windowThread.start();
    }

    public void run() {
        while (true) {
            p.repaint();
        }
    }
}