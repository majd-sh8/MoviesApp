package application.example.com.moviesapp.activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import application.example.com.moviesapp.Api.MovieApi;
import application.example.com.moviesapp.Fragment.NowPlaying_Fragment;
import application.example.com.moviesapp.Fragment.Popular_Fragment;
import application.example.com.moviesapp.Fragment.Top_Fragment;
import application.example.com.moviesapp.Fragment.UpComing_Fragment;
import application.example.com.moviesapp.R;
import application.example.com.moviesapp.RoomDB.DatabaseClient;
import application.example.com.moviesapp.adapters.SlidePagerAdapter;
import application.example.com.moviesapp.models.Movie;
import application.example.com.moviesapp.models.Slide;

public class MovieActivity extends AppCompatActivity implements ActionBar.TabListener {


    private List<Slide> listSlides;
    private ViewPager sliderpager;
    List<Movie> movie;
    private TabLayout indicator;
    ProgressDialog progressDialog;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        sliderpager = findViewById(R.id.slide_page);
        indicator = findViewById(R.id.indicator);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("Top").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Popular").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("UpComing").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("NowPlaying").setTabListener(this));

        actionBar.setLogo(R.drawable.ic_movie);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("Movies");

        if (checkInternetConnection(this)) {
            getData();

        } else {
            Toast.makeText(this, "Couldn't refresh feed", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean checkInternetConnection(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected())
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MovieApi.TopRatedMoviesURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("page") == 1) {

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

                                    movie.add(movies);

                                }

                                setRecyclerUpComing(movie);
                            }

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void setRecyclerUpComing(List<Movie> movies) {
        listSlides = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listSlides.add(new Slide(movies.get(i).getBackdrop_path(), movies.get(i).getTitle()));
        }

        SlidePagerAdapter adapter = new SlidePagerAdapter(this, listSlides);
        sliderpager.setAdapter(adapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 3000);
        indicator.setupWithViewPager(sliderpager, true);

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_layout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (tab.getText().toString().equals("Top")) {
            loadFragment(new Top_Fragment());
        }
        if (tab.getText().toString().equals("Popular")) {
            loadFragment(new Popular_Fragment());

        }
        if (tab.getText().toString().equals("UpComing")) {
            loadFragment(new UpComing_Fragment());

        }
        if (tab.getText().toString().equals("Now Playing")) {
            loadFragment(new NowPlaying_Fragment());
            Toast.makeText(this, "NowPlaying_Fragment", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    class SliderTimer extends TimerTask {
        @Override
        public void run() {
            MovieActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem() < listSlides.size() - 1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem() + 1);
                    } else {
                        sliderpager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Favorate) {
            Intent intent = new Intent(this, FavorateActivity.class);
            startActivity(intent);
        }
        if (id == R.id.refresh) {

            finish();
            startActivity(getIntent());

            Toast.makeText(this, "Done refresh", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



}



