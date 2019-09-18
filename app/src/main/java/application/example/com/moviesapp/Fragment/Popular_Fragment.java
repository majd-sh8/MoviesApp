package application.example.com.moviesapp.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import java.util.List;

import application.example.com.moviesapp.Api.MovieApi;
import application.example.com.moviesapp.R;
import application.example.com.moviesapp.activites.MovieDetailActivity;
import application.example.com.moviesapp.adapters.MovieAdapter;
import application.example.com.moviesapp.adapters.MovieItemClickListener;
import application.example.com.moviesapp.models.Movie;

public class Popular_Fragment extends Fragment implements MovieItemClickListener {

    private RecyclerView rv_movies;
    private ArrayList<Movie> movie;
    private MovieAdapter movieAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popular_fragment, container, false);
        movie = new ArrayList<>();
        rv_movies = v.findViewById(R.id.Rv_moviesP);

        getDataP();
        return v;
    }


    private void getDataP() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MovieApi.popularMoviesURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                                movie = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("results");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    Movie movies = new Movie();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    movies.setIdMovie(dataobj.getInt("id"));
                                    movies.setVote_average((float) dataobj.getDouble("vote_average"));
                                    movies.setTitle(dataobj.getString("title"));
                                    movies.setPopularity(dataobj.getDouble("popularity"));
                                    movies.setPoster_path(dataobj.getString("poster_path"));
                                    movies.setBackdrop_path(dataobj.getString("backdrop_path"));
                                    movies.setOverview(dataobj.getString("overview"));
                                    movies.setRelease_date(dataobj.getString("release_date"));
                                    movies.setFav(isStateSaved());

                                    movie.add(movies);

                                }

                                setRecycler(movie);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("error", String.valueOf(volleyError));

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void setRecycler(List<Movie> movies) {

        movieAdapter = new MovieAdapter(getContext(), movies, this);
        rv_movies.setAdapter(movieAdapter);
        rv_movies.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("movie", (Parcelable) movie);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), movieImageView, "sharedName");
        startActivityForResult(intent, 10, options.toBundle());
    }


}
