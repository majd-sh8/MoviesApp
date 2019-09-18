package application.example.com.moviesapp.activites;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.example.com.moviesapp.RoomDB.DatabaseClient;
import application.example.com.moviesapp.R;
import application.example.com.moviesapp.adapters.MovieAdapter;
import application.example.com.moviesapp.adapters.MovieItemClickListener;
import application.example.com.moviesapp.models.Movie;

public class FavorateActivity extends AppCompatActivity implements MovieItemClickListener {


    private RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ArrayList<Movie> movies;

    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorate);
        recyclerView = findViewById(R.id.Rv_movies_Fav);
        refreshLayout = findViewById(R.id.refreshFav);
        refreshLayout.setColorScheme(R.color.colorAccent,
                R.color.colorPrimary);

        movies = new ArrayList<>();
        getAllMovie();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllMovie();

            }
        });

    }


    private void setRecycler(List<Movie> movies) {

        movieAdapter = new MovieAdapter(this, movies, this);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", (Parcelable) movie);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, movieImageView, "sharedName");
        startActivityForResult(intent, 10, options.toBundle());
    }


    private void getAllMovie() {
        class GetTasks extends AsyncTask<Void, Void, List<Movie>> {

            @Override
            protected List<Movie> doInBackground(Void... voids) {
                List<Movie> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .movieDao()
                        .getAllMovieFav();

                return taskList;
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                super.onPostExecute(movies);
                setRecycler(movies);
                refreshLayout.setRefreshing(false);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }





}





