package application.example.com.moviesapp.Api;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieApi {

    private static String API_KEY = "4a7d2c349f439f8ac8d00d0bff4b7e32";
    public static String baseURL = "https://api.themoviedb.org/3/movie/";
    public static String popularMoviesURL = baseURL + "popular?api_key=" + API_KEY;
    public static String UpComingMoviesURL = baseURL + "upcoming?api_key=" + API_KEY;
    public static String TopRatedMoviesURL = baseURL + "top_rated?api_key=" + API_KEY;
    public static String LatestMoviesURL = baseURL + "latest?api_key=" + API_KEY;

    public static String LMoviesURL = "https://api.themoviedb.org/3/movie/latest?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32";
    public static String now_playing = "https://api.themoviedb.org/3/movie/now_playing?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32";

    public static String TopRatedMovie = "https://api.themoviedb.org/3/movie/top_rated?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32";
    public static String upcoming = "https://api.themoviedb.org/3/movie/upcoming?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32";
}