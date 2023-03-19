package b1392982.uk.ac.tees.predictionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;
    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView title;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        fAuth = FirebaseAuth.getInstance();

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.logoSplash);
        title = findViewById(R.id.titleSplash);

        image.setAnimation(topAnim);
        title.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {
            // Check if the user is already logged in
            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
            boolean isLogged = sharedPreferences.getBoolean("isLogged", false);
            if (isLogged) {
                // Launch the main activity
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                /* Create an Intent that will start the FirstActivity. */
                Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
                startActivity(intent);
                finish();
            }
            ;
        }, SPLASH_SCREEN);

    }
}
