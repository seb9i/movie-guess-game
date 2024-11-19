package com.frontend;
import com.backend.Movie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

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
    double movieRating;
    double marginOfError;



    HashMap<String, String> randomMovie;
    ArrayList<HashMap<String, String>> movieMultipleChoice;

    public PictureFrame(String display) {


        super(display);
        insets = new Insets(1, 4, 1, 4);
        int frameWidth = 1280;
        int frameHeight = 720;

        setBackground(new Color(0, 0, 0));

        JPanel jp = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(0,0,0);
                Color color2 = new Color(31,14,43);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        setContentPane(jp);

        Font font = new Font("Lucida Bright", Font.PLAIN, 16);
        // Movie
        randomMovie = Movie.returnMovieData();
        movieMultipleChoice = Movie.returnMovieData(4, String.valueOf(randomMovie.get("imdb_id")));
        url = randomMovie.get("movie_image_url");
        tries = 6;
        movieRating = Double.parseDouble(randomMovie.get("vote_average"));
        marginOfError = movieRating * 0.05;

        // Layout of the frame
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setLocation(500, 0);
        setBackground(new Color(255, 255, 9));
        setIconImage(null);

        // Game Title
        JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        infoLabel2 = new JLabel();
        infoLabel2.setFont(font);
        infoLabel2.setForeground(Color.WHITE);
        infoLabel2.setText("Movie Guess Game");

        panel3.add(infoLabel2);
        this.add(panel3, new GridBagConstraints(1, 0, 3, 1, 1.0, 1.0, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, insets, 0, 0));


        // Game Question
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(0, 0, 0));

        infoLabel = new JLabel();
        infoLabel.setText("What is the name of this movie?");
        infoLabel.setFont(font);
        infoLabel.setForeground(Color.WHITE);
        panel2.add(infoLabel);
        this.add(panel2, new GridBagConstraints(1, 2, 3, 1, 1.0, 1.0, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, insets, 0, 30));

        // Movie Picture Handling
        p = new PicturePanel(url);
        p.setOpaque(false);
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

        // Typed Answers
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
            if (textField.getText().trim().equals(randomMovie.get("release_year"))){
                buttonCheck.setName("Rating");
                textField.setText(null);
                this.setQuestionText("what is the average rating of the movie (you have a 5% margin of error)");
            }
            else {
                tries -= 1;
                if (tries == 0) {
                    System.exit(0);

                }
            }
        }
        if (actionButton.getName().equals("Rating")){
            double answer = Double.parseDouble(textField.getText());
            System.out.println(movieRating - marginOfError);
            if ((answer >= movieRating - marginOfError) && (answer <= movieRating + marginOfError)){
                setQuestionText("WINNER");
                setImageUrl(randomMovie.get("movie_poster_url"));
            }
            else {
                tries -= 1;
                if (tries == 0) {
                    System.exit(0);
                }
            }
        }
    }

//    public static class FrameDragListener extends MouseAdapter {
//
//        private final JFrame frame;
//        private Point mouseDownCompCoords = null;
//
//        public FrameDragListener(JFrame frame) {
//            this.frame = frame;
//        }
//
//        public void mouseReleased(MouseEvent e) {
//            mouseDownCompCoords = null;
//        }
//
//        public void mousePressed(MouseEvent e) {
//            mouseDownCompCoords = e.getPoint();
//        }
//
//        public void mouseDragged(MouseEvent e) {
//            Point currCoords = e.getLocationOnScreen();
//            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
//        }
//    }


}