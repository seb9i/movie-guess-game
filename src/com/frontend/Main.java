package com.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        HashMap<String, String> randomMovie = Movie.returnMovieData();
        ArrayList<HashMap<String, String>> movieMultipleChoice = Movie.returnMovieData(4, String.valueOf(randomMovie.get("imdb_id")));
        String multipleChoiceString = "";
        for (HashMap<String, String> i: movieMultipleChoice){
            multipleChoiceString += i.get("title") + "\n";
        }
        ArrayList<QuestionAnswer> questions = new ArrayList<>();
        questions.add(new QuestionAnswer("What is the name of this movie? (choose one):\n" + multipleChoiceString,randomMovie.get("title")));
        questions.add(new QuestionAnswer("What year was this movie released?", randomMovie.get("release_year")));
        questions.add(new QuestionAnswer("What is the average rating of the movie?", randomMovie.get("vote_average")));
        int tries = 6;
        boolean result = false;
        System.out.println(randomMovie.get("movie_image_url"));
        for (QuestionAnswer x: questions){
            result = false;
            while (!(result)){
                System.out.println(x);
                if (tries == 0){
                    System.out.println("Sorry, you lost!");
                    break;
                }
                if (x.returnQuestion().contains("rating")){
                    double answer = scan.nextDouble();
                    result = x.verifyAnswer(answer);
                }
                else {
                    String answer = scan.nextLine();
                    result = x.verifyAnswer(answer);
                }
                if (result){
                    System.out.println("You got it right, onto the next one!");
                }
                else {
                    System.out.println("WRONG!");
                    tries -= 1;
                }
            }
            }

        System.out.printf("You finished with %d tries.", tries);
//        int tries = 10;
//        System.out.println("The objective of this game is to guess the details of the movie in 6 tries.");
//        System.out.println(randomMovie.get("movie_image_url"));
//        System.out.println("Tries: " + tries);
//
//        while (tries != 0){
//            System.out.println("What is the name of this movie?");
//            for (int i = 0; i < movieMultipleChoice.size(); i++){
//                String currentLetter = String.valueOf((char) (i + 65));
//                System.out.println(currentLetter + ". " + movieMultipleChoice.get(i).get("title"));
//            }
//            String guess = scan.nextLine();
//            if (guess.equals(randomMovie.get("title"))){
//                System.out.println("You got it right!");
//                break;
//            }
//            else{
//                System.out.println("no.");
//                tries -= 1;
//
//            }
//            System.out.println(tries);
//
//            if (tries == 0){
//                System.out.println("You lost!");
//            }
//        }
    }
}