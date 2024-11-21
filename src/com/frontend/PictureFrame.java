package com.frontend;
import com.backend.Movie;
import javax.swing.*;
import javax.swing.border.Border;
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
    Font font;
    JButton tryAgain;
    String releaseDate;
    String overview;
    JLabel description;
    GridBagConstraints gbc;



    HashMap<String, String> randomMovie;
    ArrayList<HashMap<String, String>> movieMultipleChoice;

    public PictureFrame(String display) {


        super(display);
        insets = new Insets(1, 4, 1, 4);
        int frameWidth = 1280;
        int frameHeight = 720;
        gbc = new GridBagConstraints();
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

        font = new Font("Eurostile Extended", Font.PLAIN, 14);
        Font titlefont = font.deriveFont(Font.PLAIN, 19);
        initializeMovie();

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
        infoLabel2.setFont(titlefont);
        infoLabel2.setForeground(Color.WHITE);
        infoLabel2.setText("Guess The Movie");

        panel3.add(infoLabel2);
        this.add(panel3, new GridBagConstraints(1, 0, 3, 1, 1.0, 1.0, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, insets, 0, 0));


        // Game Question
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(34, 17, 66, 255));

        infoLabel = new JLabel("What is the name of this movie?");
        panel2.setOpaque(false);
        infoLabel.setFont(titlefont);
        infoLabel.setForeground(new Color(145, 93, 240));
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel2.add(infoLabel);
        panel2.setPreferredSize(new Dimension(50, 5));
        this.add(panel2, new GridBagConstraints(1, 2, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, insets, 0, 30));


        // Movie Picture Handling
        p = new PicturePanel(url);
        p.setOpaque(false);
        Insets d = new Insets(10, 10, 10, 10);
        p.setPreferredSize(new Dimension(780, 400));  // Set the preferred size for the panel
        this.add(p, new GridBagConstraints(1, 1, 1, 1, 1, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, d, 0, 0));
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
        this.add(description, gbc);

        // Buttons for first question (multiple choice)
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        initializeMultipleChoice();

        // Typed Answers
        buttonCheck = new JButton("Submit");
        buttonCheck.setName("Date");
        buttonCheck.setFont(font);
        buttonCheck.setBackground(new Color(47, 4, 99));
        buttonCheck.setForeground(new Color(236, 166, 237));
        buttonCheck.setPreferredSize(new Dimension(400, 50));
        RoundedBorder purpleLineBorder = new RoundedBorder(new Color(143, 19, 145), 12);
        Border emptyBorder = BorderFactory.createEmptyBorder(buttonCheck.getBorder().getBorderInsets(buttonCheck).top, buttonCheck.getBorder().getBorderInsets(buttonCheck).left, buttonCheck.getBorder().getBorderInsets(buttonCheck).bottom, buttonCheck.getBorder().getBorderInsets(buttonCheck).right);
        buttonCheck.setBorder(BorderFactory.createCompoundBorder(purpleLineBorder, emptyBorder));
        buttonCheck.addActionListener(this);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(500, 50));
        textField.setFont(font);
        textField.setBackground(new Color(0x200B35));
        textField.setForeground(Color.WHITE);
        emptyBorder = BorderFactory.createEmptyBorder();
        textField.setBorder(BorderFactory.createCompoundBorder(purpleLineBorder, emptyBorder));

        add(buttonCheck, new GridBagConstraints(1, 5, 3, 1, 0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, insets, 0, 0));
        add(textField, new GridBagConstraints(1, 4, 3, 1, 0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, insets, 0, 0));
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
    public void initializeMultipleChoice(){
        buttonPanel.removeAll();
        for (int i = 0; i < movieMultipleChoice.size(); i++){
            JButton button = new JButton(movieMultipleChoice.get(i).get("title"));
            button.setBackground(new Color(47, 4, 99));
            button.setForeground(new Color(236, 166, 237));
            button.setFocusPainted(false);
            button.setFont(font);
            RoundedBorder purpleLineBorder = new RoundedBorder(new Color(95, 29, 145), 12);
            Border emptyBorder = BorderFactory.createEmptyBorder(button.getBorder().getBorderInsets(button).top, button.getBorder().getBorderInsets(button).left, button.getBorder().getBorderInsets(button).bottom, button.getBorder().getBorderInsets(button).right);
            GridBagConstraints gbc = new GridBagConstraints(i, 0, 1, 1, 0, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE, insets, 0, 0);
            button.setBorder(BorderFactory.createCompoundBorder(purpleLineBorder, emptyBorder));
            button.addActionListener(this);
            buttonPanel.add(button, gbc);
            button.setName("multiple");
        }
        System.out.println(randomMovie.get("imdb_id"));
        add(buttonPanel, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public void initializeMovie(){
        // Movie
        randomMovie = Movie.returnMovieData("tt1262426");
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
        if (tries == 0){
            System.out.println("no");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
            System.out.println(movieRating - marginOfError);
            if ((answer >= movieRating - marginOfError) && (answer <= movieRating + marginOfError)){
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
            p.setPreferredSize(new Dimension(780, 400));  // Set the preferred size for the panel

        }
    }
    public void tryAgain(String currentQuestion){
        tryAgain = new JButton();
        tryAgain.setText("Try Again");
        tryAgain.setPreferredSize(new Dimension(2, 30));
        tryAgain.setBackground(new Color(0x0C7D0C));
        tryAgain.setFont(font);
        tryAgain.setForeground(Color.white);
        tryAgain.setName("restart");
        tryAgain.addActionListener(this);

        if (currentQuestion.equals("multiple")){
            buttonPanel.setVisible(false);
        }
        else {
            buttonCheck.setName("Date");
            buttonCheck.setVisible(false);
            textField.setText(null);
            textField.setVisible(false);
        }
        add(tryAgain, new GridBagConstraints(1, 5, 0, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));



    }
    private static class RoundedBorder implements Border {

        private int radius = 10;
        private Color color;

        private RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(color);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
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