package application.example.com.moviesapp.activites;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import application.example.com.moviesapp.R;

public class Splash_Screen extends AppCompatActivity {
    private ImageView imag;
   private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash__screen);

        imag = findViewById(R.id.image_splashScreen);
        txt = findViewById(R.id.text_splashScreen);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mysplash_animation);
        imag.startAnimation(myanim);
        txt.startAnimation(myanim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen.this,MovieActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

    }
}
