package b1392982.uk.ac.tees.predictionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN =4000;
    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.logoSplash);
        title = findViewById(R.id.titleSplash);

        image.setAnimation(topAnim);
        title.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the FirstActivity. */
            Intent intent = new Intent(SplashActivity.this,FirstActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN);
    }


}
