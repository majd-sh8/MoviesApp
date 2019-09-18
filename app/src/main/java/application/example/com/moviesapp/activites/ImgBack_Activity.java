package application.example.com.moviesapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import application.example.com.moviesapp.R;
import application.example.com.moviesapp.activites.ImgPoster_Activity;

public class ImgBack_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_imgback);

        ImageView img = findViewById(R.id.imgBack);
        String imgBack = getIntent().getStringExtra("imgBack");
        Picasso.get()
                .load(imgBack).placeholder(R.drawable.ic_image_black_24dp)
                .into(img);


    }
}
