package com.kleszcz.damian.zad_1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;



public class SplashScreen extends Activity {
    private boolean isBackPress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ActivityStarter starter = new ActivityStarter();
        starter.start();
    }
    @Override
    public void onBackPressed() {
        isBackPress = true;
        super.onBackPressed();
    }


    private class ActivityStarter extends Thread  {
        private static final int WAIT_TIME = 5000;

        @Override
        public void run() {
        try {
            Thread.sleep(WAIT_TIME);
        } catch (Exception e){
            int warn = Log.WARN;
        }
          Intent intent = new Intent(SplashScreen.this, MainActivity.class);
           if(!isBackPress){
               SplashScreen.this.startActivity(intent);
           }
            SplashScreen.this.finish();
        }
    }

}
