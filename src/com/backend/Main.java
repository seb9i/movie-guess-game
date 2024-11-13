package com.backend;

import com.frontend.PictureFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        HashMap<String, String> randomMovie = Movie.returnMovieData(); // The movie that the user has to guess
        ArrayList<HashMap<String, String>> movieMultipleChoice = Movie.returnMovieData(4, String.valueOf(randomMovie.get("imdb_id"))); // List of random movies including the one user has to guess

        // To store list of movies in multiple choice format
        String multipleChoiceString = "";
        for (HashMap<String, String> i: movieMultipleChoice){
            multipleChoiceString += i.get("title") + "\n";
        }

        // Questions that will be asked
        ArrayList<QuestionAnswer> questions = new ArrayList<>();
        questions.add(new QuestionAnswer("What is the name of this movie? (choose one):\n" + multipleChoiceString,randomMovie.get("title")));
        questions.add(new QuestionAnswer("What year was this movie released?", randomMovie.get("release_year")));
        questions.add(new QuestionAnswer("What is the average rating of the movie?", randomMovie.get("vote_average")));


        int tries = 6;
        PictureFrame m = new PictureFrame("Hello", randomMovie.get("movie_image_url"));

        Iterator<QuestionAnswer> iterator = questions.iterator();

        // Main Question Loop
        while (iterator.hasNext()){
            QuestionAnswer question = iterator.next();
            boolean result = false;

            while (!(result)){
                System.out.println(question);
                if (tries == 0){ // No tries left
                    System.out.println("Sorry, you lost!");
                    break;
                }
                if (question.returnQuestion().contains("rating")){ // Calling different function depending on which question is asked
                    double answer = scan.nextDouble();
                    result = question.verifyAnswer(answer);
                }
                else {
                    String answer = scan.nextLine();
                    result = question.verifyAnswer(answer);
                }
                if (result){
                    if (!iterator.hasNext()){
                        System.out.printf("You got it right, you finished with %d tries!\n", tries); // Final question
                        m.setImageUrl(randomMovie.get("movie_poster_url"));
                    }
                    else {
                        System.out.println("You got it right, on to the next one!"); // Not final question
                    }

                }
                else { // Wrong answer
                    System.out.println("WRONG!");
                    tries -= 1;
                }
            }
        }

    }
}