import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class Movie {
    private HttpResponse<String> movieData;
    private String imbdID;
    private String movieName;
    // random photo;
    private int movieReleaseYear;
    private String movieOverview;

    public Movie(){
        System.out.println("hi");
    }

    public static HashMap<Object, Object> getMovieData(String id){
        String response = ApiCall.get("https://api.themoviedb.org/3/find/" + id + "?external_source=imdb_id").body();
        HashMap<Object, Object> movieData = new HashMap<>();
        movieData.put("overview", response.substring(response.indexOf("overview") + 11, response.indexOf("poster_path") - 3));
        movieData.put("imbd_id", id);
        movieData.put("release_year", response.substring(response.indexOf("release_date") + 15, response.indexOf("release_date") + 19));
        movieData.put("title", response.substring(response.indexOf("title") + 8, response.indexOf("original_title")-4));
        return movieData;
    }
    public static HashMap<?, ?> getMovieData(){
        return new HashMap<>();
    }
}
