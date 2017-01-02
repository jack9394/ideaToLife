package me.ideatolife.testproject.activities;


import android.content.Intent;
import android.os.Bundle;
import me.ideatolife.testproject.R;
import me.ideatolife.testproject.base.BaseActivity;

/**
 * Created by jack on 1/01/2017.
 */
public class SplashScreenActivity extends BaseActivity {
    private final int SPLASH_TIME=1500;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        handler.postDelayed(runnable,SPLASH_TIME);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void init() {
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

}
