package com.frontend;

import com.backend.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PictureFrame extends JFrame implements Runnable {


    private PicturePanel p;
    JLabel infoLabel;
    JLabel infoLabel2;
    public PictureFrame(String display, String url, ArrayList<HashMap<String, String>> movieMultipleChoice ) {
        super(display);
        Insets insets = new Insets(1, 4, 1, 4);
        int frameWidth = 1280;
        int frameHeight = 720;

        // Layout of the frame
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setLocation(500, 0);
        setBackground(new Color(255, 255, 9));
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the Flat Look and Feel
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                UIManager.put("Button.border", BorderFactory.createEmptyBorder());
            } catch (Exception e) {
                e.printStackTrace();
            }});
        UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo i: lafInfo){
            System.out.println(i);
        }

        // Game Title
        JPanel panel3 = new JPanel();
        infoLabel2 = new JLabel();
        infoLabel2.setText("Movie Guess Game");
        panel3.add(infoLabel2);
        this.add(panel3, new GridBagConstraints(1, 0, 3, 1, 1.0, 1.0, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, insets, 0, 0));


        // Game Question
        JPanel panel2 = new JPanel();
        infoLabel = new JLabel();
        infoLabel.setText("hi");
        panel2.add(infoLabel);
        this.add(panel2, new GridBagConstraints(1, 2, 3, 1, 1.0, 1.0, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, insets, 0, 40));

        // Movie Picture Handling
        p = new PicturePanel(url);
        p.setPreferredSize(new Dimension(800, 500));  // Set the preferred size for the panel
        this.add(p, new GridBagConstraints(1, 1, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, insets, 0, 0));


        // Buttons for first question (multiple choice)
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        for (int i = 0; i < movieMultipleChoice.size(); i++){
            JButton button = new JButton(movieMultipleChoice.get(i).get("title"));
            GridBagConstraints gbc = new GridBagConstraints(i, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0);
            button.addActionListener(e -> {
                JButton sourceButton = (JButton) e.getSource();
                System.out.println("You clicked " + sourceButton.getText());
            });
            buttonPanel.add(button, gbc);
        }
        add(buttonPanel, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        buttonPanel.setVisible(false);

        JButton button = new JButton("Submit");
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 50));
        add(button, new GridBagConstraints(1, 5, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        add(textField, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        button.addActionListener(e -> {
            JButton sourceButton = (JButton) e.getSource();
            System.out.println("You entered " + textField.getText());
        });
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