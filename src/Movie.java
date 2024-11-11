import java.net.http.HttpResponse;
import java.util.*;


public class Movie {
    private HttpResponse<String> movieData;
    private static ArrayList<String> lines = FileHandle.getFileData("src/movielist.txt");


    /**
     * This method is the foundational block of this program, it returns the metadata of any IMDB movie given its id.
     * @param id The IMDB id of the movie that you want to scrape.
     * @return HashMap that includes data about the movie including overview, imdb id, release year, title, and average IMDB rating.
     */
    public static HashMap<Object, Object> returnMovieData(String id){
        String response = ApiCall.get("https://api.themoviedb.org/3/find/" + id + "?external_source=imdb_id").body();
        HashMap<Object, Object> movieData = new HashMap<>();
        movieData.put("overview", response.substring(response.indexOf("\"overview\"") + 12, response.indexOf("poster_path") - 3));
        movieData.put("imdb_id", id);
        movieData.put("release_year", response.substring(response.indexOf("\"release_date\"") + 16, response.indexOf("\"release_date\"") + 20));
        movieData.put("title", response.substring(response.indexOf("\"title\"") + 9, response.indexOf("\"original_title\"")-2));
        movieData.put("vote_average", response.substring(response.indexOf("\"vote_average\"") + 15, response.indexOf("\"vote_count\"") - 1));
        movieData.put("movie_image_url", "https://image.tmdb.org/t/p/w1280" + response.substring(response.indexOf("\"backdrop_path\"") + 17, response.indexOf("\"id\"")-2));
        movieData.put("id", response.substring(response.indexOf("\"id\"") + 5, response.indexOf("\"title\"") - 1));


        return movieData;
    }

    /**
     * This method is meant for the multiple choice implementation, returns an array list of random movie hashmaps, excluding one.
     * @param numberOfMovies Number of random moves you want to generate
     * @param include Parameter to make sure there are no doubles in the final list.
     * @return Array list of randomized movies including the one in the include parameter.
     */

    public static ArrayList<HashMap<Object, Object>> returnMovieData(int numberOfMovies, String include){
        ArrayList<HashMap<Object, Object>> hi = new ArrayList<>();
        Collections.shuffle(lines);

        int i = 0;
        while (i < numberOfMovies - 1){
            if (lines.get(i).equals(include)){
            }
            else {
                hi.add(returnMovieData(lines.get(i)));
                i += 1;
            }
        }
        hi.add(returnMovieData(include));
        Collections.shuffle(hi);
        return hi;
    }

    /**
     * Returns a random movie, no parameters needed.
     * @return Random Movie HashMap.
     */
    public static HashMap<Object, Object> returnMovieData(){

        int randomId = (int)(Math.random() * lines.size());
        String randomIMDB = lines.get(randomId);
        return returnMovieData(randomIMDB);
    }


}
