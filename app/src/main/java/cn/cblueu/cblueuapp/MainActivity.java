package cn.cblueu.cblueuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //恢复
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }
    public void StartBusActivity(View view) {
        Intent intent = new Intent(MainActivity.this,BusActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.musicActivity)
    public void startMusic(){
        startActivity(new Intent(MainActivity.this,MusicActivity.class));
    }
}
