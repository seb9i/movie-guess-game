package com.backend;

import com.frontend.PictureFrame;


public class Main {
    public static void main(String[] args) {
        PictureFrame m = new PictureFrame("Hello");

        // Main Question Loop
//        while (iterator.hasNext()){
//            QuestionAnswer question = iterator.next();
//            boolean result = false;
//
//            while (!(result)){
//                m.setQuestionText(question.returnQuestion());
//                if (tries == 0){ // No tries left
//                    System.out.println("Sorry, you lost!");
//                    break;
//                }
//                if (question.returnQuestion().contains("rating")){ // Calling different function depending on which question is asked
//                    double answer = scan.nextDouble();
//                    result = question.verifyAnswer(answer);
//                }
//                else {
//                    String answer = scan.nextLine();
//                    result = question.verifyAnswer(answer);
//                }
//                if (result){
//                    if (!iterator.hasNext()){
//                        System.out.printf("You got it right, you finished with %d tries!\n", tries); // Final question
//                        m.setImageUrl(randomMovie.get("movie_poster_url"));
//                    }
//                    else {
//                        System.out.println("You got it right, on to the next one!"); // Not final question
//                    }
//
//                }
//                else { // Wrong answer
//                    System.out.println("WRONG!");
//                    tries -= 1;
//                }
//            }
//        }

    }
}