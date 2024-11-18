package com.frontend;

import com.backend.Movie;
import com.backend.QuestionAnswer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PictureFrame extends JFrame implements Runnable, ActionListener {

    // Picture and Two Labels
    private PicturePanel p;
    JLabel infoLabel;
    JLabel infoLabel2;
    JPanel buttonPanel;
    Insets insets;
    String url;
    int tries;
    JTextField textField;
    JButton buttonCheck;

    ArrayList<QuestionAnswer> questions = new ArrayList<>();

    HashMap<String, String> randomMovie;
    ArrayList<HashMap<String, String>> movieMultipleChoice;

    public PictureFrame(String display) {


        super(display);
        insets = new Insets(1, 4, 1, 4);
        int frameWidth = 1280;
        int frameHeight = 720;

        // Movie
        randomMovie = Movie.returnMovieData();
        movieMultipleChoice = Movie.returnMovieData(4, String.valueOf(randomMovie.get("imdb_id")));
        url = randomMovie.get("movie_image_url");
        tries = 6;


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
        buttonPanel = new JPanel(new GridBagLayout());
        for (int i = 0; i < movieMultipleChoice.size(); i++){
            JButton button = new JButton(movieMultipleChoice.get(i).get("title"));
            GridBagConstraints gbc = new GridBagConstraints(i, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0);
            button.addActionListener(this);
            buttonPanel.add(button, gbc);
            button.setName("multiple");
        }
        add(buttonPanel, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        buttonCheck = new JButton("Submit");
        buttonCheck.setName("Date");
        buttonCheck.addActionListener(this);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 50));
        add(buttonCheck, new GridBagConstraints(1, 5, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        add(textField, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        buttonCheck.setVisible(false);
        textField.setVisible(false);



        // Refresh and show frame
        revalidate();
        repaint();
        setVisible(true);

    }
    public void initializeQuestions(){
        questions.add(new QuestionAnswer("What is the name of this movie? (choose one)",randomMovie.get("title")));
        questions.add(new QuestionAnswer("What year was this movie released?", randomMovie.get("release_year")));
        questions.add(new QuestionAnswer("What is the average rating of the movie?", randomMovie.get("vote_average")));
    }
    public void setImageUrl(String urls){
        p.setImageUrl(urls);
    }
    public void setQuestionText(String text){
        infoLabel.setText(text);
    }





    @Override
    public void run() {
        if (tries == 0){
            System.out.println("no");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton actionButton = (JButton) e.getSource();
        System.out.println("hi");
        if (actionButton.getName().equals("multiple")){
            if (actionButton.getText().equals(randomMovie.get("title"))) {
                buttonPanel.setVisible(false);
                buttonCheck.setVisible(true);
                textField.setVisible(true);
                this.setQuestionText("what year was the movie released");
            }
            else {
                tries -= 1;
                if (tries == 0) {
                    System.out.println("YOU LOST!!!!!");
                    System.exit(0);
                }
            }
        }
        if (actionButton.getName().equals("Date")) {
            if (textField.getText().equals(randomMovie.get("release_year"))){
                System.out.printf("YES!!");
            }
            else {
                tries -= 1;
                if (tries == 0) {
                    System.exit(0);

                }
            }
        }
    }
}