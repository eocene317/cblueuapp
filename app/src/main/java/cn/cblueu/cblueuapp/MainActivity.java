package cn.cblueu.cblueuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;

public class MainActivity extends Activity {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //这是一个git测试
        //云端修改
        //模拟冲突
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
}
