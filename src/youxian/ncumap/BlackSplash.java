package youxian.ncumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class BlackSplash extends Activity {
private static final int SPLASH_DISPLAY_TIME = 300; // splash screen delay time

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.blacksplash);

    new Handler().postDelayed(new Runnable() {
        public void run() {

            Intent intent = new Intent();
            intent.setClass(BlackSplash.this, MainActivity.class);

            BlackSplash.this.startActivity(intent);
            BlackSplash.this.finish();

            // transition from splash to main menu
            overridePendingTransition(R.anim.activityfadein,
                    R.anim.splashfadeout);


        }
    }, SPLASH_DISPLAY_TIME);
}
}