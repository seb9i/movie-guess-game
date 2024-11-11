import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        HashMap<Object, Object> randomMovie = Movie.returnMovieData();
        ArrayList<HashMap<Object, Object>> movieMultipleChoice = Movie.returnMovieData(4, String.valueOf(randomMovie.get("imdb_id")));

        int tries = 10;
        System.out.println("The objective of this game is to guess the details of the movie in 6 tries.");
        System.out.println(randomMovie.get("movie_image_url"));
        System.out.println("Tries: " + tries);
        while (tries != 0){
            System.out.println("What is the name of this movie?");
            for (int i = 0; i < movieMultipleChoice.size(); i++){
                String currentLetter = String.valueOf((char) (i + 65));
                System.out.println(currentLetter + ". " + movieMultipleChoice.get(i).get("title"));
            }
            String guess = scan.nextLine();
            if (guess.equals(randomMovie.get("title"))){
                System.out.println("You got it right!");
                break;
            }
            else{
                System.out.println("no.");
                tries -= 1;

            }
            System.out.println(tries);

            if (tries == 0){
                System.out.println("You lost!");
            }
        }
        // Margin of error
        // if ((scan.nextDouble() >= i - marginOfError) && (scan.nextDouble() <= i + marginOfError)){
           // System.out.println("valid input");
        // }
    }
}