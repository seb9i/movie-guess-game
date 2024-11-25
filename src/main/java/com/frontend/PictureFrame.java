package com.frontend;
import com.backend.Movie;
import com.sun.jna.platform.WindowUtils;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sound.sampled.*;


public class PictureFrame extends JFrame implements Runnable, ActionListener, MouseListener {

    // Labels
    private PicturePanel p;
    JLabel infoLabel;
    JLabel infoLabel2;
    JLabel infoLabel3;
    JLabel description;


    // Panels
    JPanel buttonPanel; // For multiple choice
    Insets insets;

    // Overlay to display text over video
    private JWindow overlayWindow;

    String url; // Image Url
    int tries;

    // Movie Info
    String releaseDate;
    String overview;
    double movieRating;
    double marginOfError;
    HashMap<String, String> randomMovie;
    ArrayList<HashMap<String, String>> movieMultipleChoice;


    JTextField textField;
    Font font;
    Font titlefont;

    // Buttons
    JButton tryAgain;
    JButton buttonCheck;


    GridBagConstraints gbc; // For layout

    private EmbeddedMediaPlayerComponent mediaPlayerComponent; // For Video


    // Glow Variables
    private List<Color> colors;
    private int currentColorIndex = 0;
    Timer timer;


    JButton enteredButton; // For button hovering





    public PictureFrame(String display) {

        // Frame Setup
        super(display);
        insets = new Insets(1, 4, 1, 4);
        int frameWidth = 1228;
        int frameHeight = 690;
        gbc = new GridBagConstraints();
        generateColors();
        setResizable(false);
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        // Background Video

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        // Setting overlay
        mediaPlayerComponent.mediaPlayer().overlay().set(overlayWindow);

        // PS3 Font
        font = new Font("SCE-PS3 Rodin LATIN Regular", Font.PLAIN, 16);
        titlefont = font.deriveFont(Font.PLAIN, 19);
        initializeMovie();

        // Layout of the frame
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setLocation(500, 0);
        setBackground(new Color(255, 255, 9));


        setUndecorated(true);

        // Refresh and show frame
        revalidate();
        repaint();
        setContentPane(contentPane);
        setVisible(true);
        play();

        mediaPlayerComponent.mediaPlayer().overlay().enable(true);


        initializeOverlay();

    }
    private void initializeOverlay() {
        // Create the overlay window
        overlayWindow = new JWindow(this);
        overlayWindow.setBackground(new Color(0, 0, 0, 0)); // Fully transparent
        overlayWindow.setLayout(new GridBagLayout());

        // Game Title
        JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        infoLabel2 = new JLabel();
        infoLabel2.setFont(titlefont);
        infoLabel2.setForeground(Color.WHITE);
        infoLabel2.setText("<html>Guess The Movie &emsp;&emsp;</html> ");
        infoLabel3 = new JLabel();
        infoLabel3.setFont(titlefont);

        infoLabel3.setForeground(Color.RED);

        infoLabel3.setText("Tries " + String.valueOf(tries)); // Displaying Tries

        panel3.add(infoLabel2);
        panel3.add(infoLabel3);
        overlayWindow.getContentPane().add(panel3, new GridBagConstraints(1, 0, 3, 1, 1.0, 1.0, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, insets, 0, 0));

        // Game Question
        JPanel panel2 = new JPanel();
        infoLabel = new JLabel("What is the name of this movie?");
        panel2.setOpaque(false);
        infoLabel.setFont(titlefont);
        infoLabel.setForeground(new Color(145, 93, 240));
        panel2.add(infoLabel);
        panel2.setPreferredSize(new Dimension(50, 5));
        overlayWindow.getContentPane().add(panel2, new GridBagConstraints(1, 2, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, insets, 0, 30));


        // Picture Display And Design
        p = new PicturePanel(url);
        p.setOpaque(false);
        Insets d = new Insets(10, 10, 10, 10);
        p.setPreferredSize(new Dimension(780, 400));  // Set the preferred size for the panel
        overlayWindow.getContentPane().add(p, new GridBagConstraints(1, 1, 1, 1, 1, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, d, 0, 0));


        // Overview Design
        description = new JLabel("<html>" + overview + "</html>");
        description.setPreferredSize(new Dimension(300, 500)); // Enough space for text
        description.setFont(titlefont);
        description.setForeground(new Color(238, 187, 239, 255));
        description.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        overlayWindow.getContentPane().add(description, gbc);

        // Buttons for first question (multiple choice)
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        initializeMultipleChoice();

        // Submit Button Design
        buttonCheck = new JButton("Submit");
        buttonCheck.setName("Date");
        buttonCheck.setFont(font);
        buttonCheck.setBackground(new Color(0, 0, 0));
        buttonCheck.setForeground(new Color(236, 166, 237));
        buttonCheck.setPreferredSize(new Dimension(400, 50));
        buttonCheck.setBorderPainted(false);
        buttonCheck.setFocusPainted(false);
        buttonCheck.addMouseListener(this);
        buttonCheck.addActionListener(this);

        // Text Field Design
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(500, 50));
        textField.setFont(font);
        textField.setBackground(new Color(0, 0,0 ));
        textField.setForeground(Color.WHITE);

        overlayWindow.getContentPane().add(buttonCheck, new GridBagConstraints(1, 5, 3, 1, 0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, insets, 0, 0));
        overlayWindow.getContentPane().add(textField, new GridBagConstraints(1, 4, 3, 1, 0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, insets, 0, 0));
        buttonCheck.setVisible(false);
        textField.setVisible(false);





        // Make sure the overlay size matches the video player size
        overlayWindow.setSize(getWidth(), getHeight());
        overlayWindow.setLocation(getLocation());
        overlayWindow.setAlwaysOnTop(true);


        // Make the overlay visible
        overlayWindow.setVisible(true);
    }


    // Changing the image
    public void setImageUrl(String urls){
        p.setImageUrl(urls);
    }

    // Changing the question text
    public void setQuestionText(String text){
        infoLabel.setText(text);
    }


    /**
     * Play sound effects in program
     * @param soundFile file path of sound to be played
     */
    public void playSound(String soundFile){
        try {
            File audioFile = new File(soundFile); // Replace with your audio file path
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = (Clip) AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

        }
    }

    // To play background video
    public void play(){

        mediaPlayerComponent.mediaPlayer().media().play("src/main/java/videos/ps3xmb.mp4");
    }

    /**
     * Function that creates multiple choice bar for the first question
     */
    public void initializeMultipleChoice(){
        buttonPanel.removeAll();
        // Creating a button for each movie option.
        for (int i = 0; i < movieMultipleChoice.size(); i++){
            // Design of buttons
            JButton button = new JButton(movieMultipleChoice.get(i).get("title"));
            button.setBackground(Color.black);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setForeground(new Color(150, 150, 150));
            button.setFont(font);
            GridBagConstraints gbc = new GridBagConstraints(i, 0, 1, 1, 0, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE, insets, 0, 0);

            button.addActionListener(this); // Action Listener
            button.addMouseListener(this); // Glow Effect
            buttonPanel.add(button, gbc);
            button.setName("multiple");
        }
        // Add Button Panel to window
        overlayWindow.getContentPane().add(buttonPanel, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    /**
     * Function that generates a grayscale of colors simulating a "glow" effect.
     */
    public void generateColors() {
        colors = new ArrayList<>(); // Array list of colors
        for (int i = 0; i <= 40; i++) {
            int grayValue = 150 + (int) ((105 / 40) * i); // Calculate the gray value
            colors.add(new Color(grayValue, grayValue, grayValue)); // Create a grayscale color
        }
        for (int i = 40; i >=0; i--){
            colors.add(colors.get(i)); // Adding the reverse of the list for a bouncing effect.
        }
    }

    /**
     * Function to initialize movie data
     */
    public void initializeMovie(){
        // Movie
        randomMovie = Movie.returnMovieData();
        releaseDate = randomMovie.get("release_year");
        movieMultipleChoice = Movie.returnMovieData(4, String.valueOf(randomMovie.get("imdb_id")));
        url = randomMovie.get("movie_image_url");
        tries = 6;
        movieRating = Double.parseDouble(randomMovie.get("vote_average"));
        marginOfError = movieRating * 0.05;
        overview = randomMovie.get("overview");
    }
    @Override
    public void run() {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Function to handle instances where the mouse hovers over a button.
     * @param e Source of button where the mouse entered
     */
    public void mouseEntered(MouseEvent e){
        playSound("src/main/java/videos/cursor.wav");
        enteredButton = (JButton) e.getSource();
        timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent d) {
                // Change the label's color
                enteredButton.setForeground(colors.get(currentColorIndex));
                currentColorIndex = (currentColorIndex + 1) % colors.size(); // Cycle through colors
            }
        });

        timer.start();
    }

    /**
     * Override to handle mouse exit
     * @param e Source of button where the mouse left
     */
    @Override
    public void mouseExited(MouseEvent e) {
        enteredButton.setForeground(new Color(150, 150, 150));
        timer.stop();
        currentColorIndex = 0;

    }

    /**
     * Override to handle button logic in program
     * @param e Source of button that's pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        playSound("src/main/java/videos/cursor.wav");
        JButton actionButton = (JButton) e.getSource();
        String decade = "'" + releaseDate.substring(2, 3) + "0s";
        if (actionButton.getName().equals("multiple")){
            if (actionButton.getText().equals(randomMovie.get("title"))) {
                buttonPanel.setVisible(false);
                buttonCheck.setVisible(true);
                textField.setVisible(true);
                this.setQuestionText("<html>What year was this movie released? <font color=#1be0dd>Hint:" + " the " + decade + "</font></html>");
            }
            else  {
                tries -= 1;
                if (tries == 0) {
                    setQuestionText("You ran out of tries!");
                    tryAgain("multiple");
                }
            }
        }
        if (actionButton.getName().equals("Date")) {
            if (textField.getText().trim().equals(releaseDate)){
                buttonCheck.setName("Rating");
                textField.setText(null);
                this.setQuestionText("<html> What is the average rating of the movie? <font color = #eb2005>(you have a 5% margin of error)</font> </html>");
            }
            else {
                tries -= 1;
                if (tries == 0) {
                    setQuestionText("You ran out of tries!");
                    tryAgain("date");

                }
            }
        }
        if (actionButton.getName().equals("Rating")){
            double answer = Double.parseDouble(textField.getText());
            if ((answer >= movieRating - marginOfError) && (answer <= movieRating + marginOfError)){
                playSound("src/main/java/videos/tropy.wav");
                setQuestionText("WINNER");
                description.setVisible(true);
                p.setPreferredSize(new Dimension(342, 500));
                textField.setText(null);
                tryAgain("Rating");
                setImageUrl(randomMovie.get("movie_poster_url"));
            }
            else {
                tries -= 1;
                if (tries == 0) {
                    setQuestionText("You ran out of tries!");
                    tryAgain("rating");
                }
            }
        }
        if (actionButton.getName().equals("restart")){
            initializeMovie();

            initializeMultipleChoice();
            p.setImageUrl(url);
            tryAgain.setVisible(false);
            buttonPanel.setVisible(true);
            description.setText("<html>" + overview + "</html>");
            description.setVisible(false);
            setQuestionText("What is the name of this movie?");

            p.setPreferredSize(new Dimension(780, 400));  // Set the preferred size for the panel

        }
    }

    /**
     * Implementation for the try again button.
     * @param currentQuestion current question at the time the function is called.
     */

    public void tryAgain(String currentQuestion){

        // Modifying design of try again button
        tryAgain = new JButton();
        tryAgain.setText("Try Again");
        tryAgain.setPreferredSize(new Dimension(2, 30));
        tryAgain.setBackground(new Color(0, 0, 0));
        tryAgain.setFont(font);
        tryAgain.setForeground(Color.white);
        tryAgain.setName("restart");
        tryAgain.addActionListener(this); // Button Logic
        tryAgain.addMouseListener(this); // Glowing Effect
        tryAgain.setBorderPainted(false);
        tryAgain.setFocusPainted(false);

        if (currentQuestion.equals("multiple")){
            buttonPanel.setVisible(false); // If the user fails on the multiple choice
        }
        else {
            buttonCheck.setName("Date");
            buttonCheck.setVisible(false);
            textField.setText(null);
            textField.setVisible(false);
        }
        // Add button to screen
        overlayWindow.getContentPane().add(tryAgain, new GridBagConstraints(1, 5, 0, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
    }





    }