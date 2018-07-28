package cn.cblueu.cblueuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartBusActivity(View view) {
        Intent intent = new Intent(MainActivity.this,BusActivity.class);
        startActivity(intent);
    }
}
