package application.example.com.moviesapp.activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.santalu.diagonalimageview.DiagonalImageView;
import com.squareup.picasso.Picasso;

import application.example.com.moviesapp.R;
import application.example.com.moviesapp.RoomDB.DatabaseClient;
import application.example.com.moviesapp.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {


    private ImageView MovieThumbnailImg, favorate;
    private TextView tv_description, tv_popularity, date;
    private RatingBar ratingBar;
    private int id;
    DiagonalImageView MovieCoverImg;
    private AppCompatTextView tv_title;
    Movie movie;
    ImageView img_favorate;
    String Title, Poster_path, Backdrop_path, Overview, rdate, orgTitle;
    float vote_average;
    double popularity;
    Boolean fav;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_movie_detail);

        img_favorate = findViewById(R.id.favorate);
        MovieThumbnailImg = findViewById(R.id.detail_movie_img);
        MovieCoverImg = findViewById(R.id.detail_movie_cover);
        tv_title = findViewById(R.id.detail_movie_title);
        tv_popularity = findViewById(R.id.popularity);
        tv_description = findViewById(R.id.detail_movie_desc);
        ratingBar = findViewById(R.id.ratingBar);
        favorate = findViewById(R.id.favorate);


        movie = getIntent().getParcelableExtra("movie");
        id = movie.getIdMovie();
        Title = movie.getTitle();
        Poster_path = movie.getPoster_path();
        Backdrop_path = movie.getBackdrop_path();
        Overview = movie.getOverview();
        rdate = movie.getRelease_date();
        vote_average = movie.getVote_average();
        popularity = movie.getPopularity();
        Boolean favv = movie.getFav();
        Log.d("dd", Backdrop_path);

        if (favv == null) {
            fav = false;
        } else {
            fav = favv;
        }
        Picasso.get()
                .load(Poster_path).placeholder(R.drawable.ic_image_black_24dp)
                .into(MovieThumbnailImg);

        Picasso.get()
                .load(Backdrop_path).placeholder(R.drawable.ic_image_black_24dp)
                .into(MovieCoverImg);

        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_img));


        tv_title.setText(Title);
        getSupportActionBar().setTitle(Title);
        tv_description.setText(Overview);
        favorate.setSelected(fav);

        tv_popularity.setText("" + popularity + "M");
        int r = Math.round(vote_average / 2);
        ratingBar.setRating(r);

        YoYo.with(Techniques.SlideInRight)
                .duration(700)
                .repeat(1)

                .playOn(MovieCoverImg);

        MovieThumbnailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImgPoster_Activity.class);
                intent.putExtra("imgPoster", Poster_path);
                startActivity(intent);
            }
        });
        CheckMovie(Title);



    }

    public void fav(View view) {

        view.setSelected(!view.isSelected());
        movie.setFav(view.isSelected());

        Boolean favv = view.isSelected();

        Movie movieM = new Movie(Poster_path, Overview, rdate, id, Title, Backdrop_path, popularity, vote_average, favv);
        if (view.isSelected()) {
            saveMvoie(movieM);
        } else {
            deleteMvoie(Title);

        }
    }


    private void deleteMvoie(final String titleD) {
        class DeleteMovie extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplication()).getAppDatabase()
                        .movieDao()
                        .deleteM(titleD);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(MovieDetailActivity.this, "Done delete", Toast.LENGTH_SHORT).show();
                super.onPostExecute(aVoid);
            }
        }

        DeleteMovie st = new DeleteMovie();
        st.execute();
    }


    private void saveMvoie(final application.example.com.moviesapp.models.Movie m) {
        class SaveMovie extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {


                DatabaseClient.getInstance(getApplication()).getAppDatabase()
                        .movieDao()
                        .insert(m);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(MovieDetailActivity.this, "Done Save", Toast.LENGTH_SHORT).show();
                super.onPostExecute(aVoid);
            }
        }

        SaveMovie st = new SaveMovie();
        st.execute();
    }


    private void CheckMovie(final String title) {
        class CheckMovie extends AsyncTask<Void, Void, Movie> {

            @Override
            protected Movie doInBackground(Void... voids) {
                Movie taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .movieDao()
                        .checkMovie(title);
                return taskList;
            }

            @Override
            protected void onPostExecute(Movie movies) {
                super.onPostExecute(movies);
                if (movies == null) {
                    favorate.setSelected(false);
                } else {
                    favorate.setSelected(true);
                }
            }
        }

        CheckMovie gt = new CheckMovie();
        gt.execute();
    }



}

