package com.calls;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.net.URI;
public class ApiCall {
    /**
     * Function for HTTP get requests.
     * @param url Url that you want to use.
     * @return Response body as a string.
     */
    public static HttpResponse<String> get (String url){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNzYxNWRlYzdlNjE4MjM4OTE3NDY4MzIzYzk2ZThiMCIsIm5iZiI6MTczMTAzNDM5NC43NTkzNzUzLCJzdWIiOiI2NzJjZGI1YzVhMjA0NTkyMDc0MTUwZjYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.u4TjsKVugM9-tnnwDsBJrn4LXzoS6EmPIbrk6lJ3xU8")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
