package cn.cblueu.cblueuapp;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class SpalshActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_spalsh);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
