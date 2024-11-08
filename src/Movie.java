import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Movie {
    private HttpResponse<String> movieData;


    public Movie(){
        System.out.println("hi");
    }

    public static HashMap<Object, Object> returnMovieData(String id){
        String response = ApiCall.get("https://api.themoviedb.org/3/find/" + id + "?external_source=imdb_id").body();
        HashMap<Object, Object> movieData = new HashMap<>();
        movieData.put("overview", response.substring(response.indexOf("\"overview\"") + 12, response.indexOf("poster_path") - 3));
        movieData.put("imdb_id", id);
        movieData.put("release_year", response.substring(response.indexOf("\"release_date\"") + 16, response.indexOf("\"release_date\"") + 20));
        movieData.put("title", response.substring(response.indexOf("\"title\"") + 9, response.indexOf("\"original_title\"")-2));
        movieData.put("vote_average", response.substring(response.indexOf("\"vote_average\"") + 15, response.indexOf("\"vote_count\"") - 1));
        return movieData;
    }
    // Parameters:
    public static HashMap<Object, Object> returnMovieData(){
        ArrayList<String> lines = FileHandle.getFileData("src/movielist.txt");
        int randomId = (int)(Math.random() * lines.size());
        String randomIMDB = lines.get(randomId);
        return returnMovieData(randomIMDB);
    }
}
