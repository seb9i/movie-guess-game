package com.frontend;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class PicturePanel extends JPanel {

    private Image i;

    public PicturePanel(String urls) {
        setImageUrl(urls);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(i, 0, 0, null);
    }
    public void setImageUrl (String urls) {
        try {
            URL url = new URL(urls);
            i = ImageIO.read(url);
            repaint();
        }
        catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}




