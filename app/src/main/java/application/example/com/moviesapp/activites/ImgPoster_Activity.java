package application.example.com.moviesapp.activites;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import application.example.com.moviesapp.R;

public class ImgPoster_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_imag_poster);


        ImageView img = findViewById(R.id.imgPoster);
        String imgPoster = getIntent().getStringExtra("imgPoster");
        Picasso.get()
                .load(imgPoster).placeholder(R.drawable.ic_image_black_24dp)
                .into(img);
    }
}
