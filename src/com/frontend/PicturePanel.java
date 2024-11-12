package com.frontend;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class PicturePanel extends JPanel {

    private Image i;

    public PicturePanel(String urls) {
        try {
            URL url = new URL(urls);
            i = ImageIO.read(url);
        }
        catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(i, 0, 0, null);
    }


}