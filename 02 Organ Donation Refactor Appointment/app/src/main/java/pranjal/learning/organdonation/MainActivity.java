package pranjal.learning.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int Splash_screen = 3000;

    Animation topAnimation, bottomAnimation;
    ImageView image;
    TextView tagLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hides action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // animations for top and bottom
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        // access to UI elements
        image = findViewById(R.id.imageView);
        tagLine = findViewById(R.id.tagLine);

        // attaching animations
        image.setAnimation(topAnimation);
        tagLine.setAnimation(bottomAnimation);

        // go to next intent after five seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // new intent
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, Splash_screen);
    }

    public static void callPhone(String s) {

    }
}