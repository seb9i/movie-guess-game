package com.frontend;
/*
Class for the main question system in the terminal.
Will most likely be scrapped once I learn how to implement a GUI.
 */
public class QuestionAnswer {
    private final String question;
    private final String answer;
    private double marginOfError = 0.10;

    public QuestionAnswer(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    /* Check user given answer with real answer */
    public boolean verifyAnswer(String userAnswer){
        return (this.answer.equals(userAnswer));
    }
    public boolean verifyAnswer(double userAnswer){  // Accounts for margin of error with IMDB rating
        marginOfError = Double.parseDouble(this.answer) * marginOfError;
        return (userAnswer >= Double.parseDouble(this.answer) - marginOfError) && (userAnswer <= Double.parseDouble(this.answer) + marginOfError);
    }


    // View the question
    public String returnQuestion (){
        return question;
    }
    public String toString(){
        return question;
    }

}
