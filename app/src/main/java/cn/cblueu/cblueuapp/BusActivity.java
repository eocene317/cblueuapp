package cn.cblueu.cblueuapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class BusActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getPage();
    }


    private void getPage(){
        if(ContextCompat.checkSelfPermission(BusActivity.this,Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(BusActivity.this,new String[]{Manifest.permission.INTERNET},1);
            ActivityCompat.requestPermissions(BusActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            Toast.makeText(BusActivity.this,"获取权限",Toast.LENGTH_SHORT).show();
            webView.loadUrl("https://web.chelaile.net.cn/ch5/index.html?cityId=066&cityName=长沙&src=webapp_weixin_changshalongxiang&utm_medium=entrance&cityVersion=0&utm_source=webapp_weixin_changshalongxiang&homePage=linearound&supportSubway=1&switchCity=0&showTopLogo=0&hideFooter=1&showFav=0#!/linearound");
        }
        else {
            Toast.makeText(BusActivity.this,"已有权限",Toast.LENGTH_SHORT).show();
            webView.loadUrl("https://web.chelaile.net.cn/ch5/index.html?cityId=066&cityName=长沙&src=webapp_weixin_changshalongxiang&utm_medium=entrance&cityVersion=0&utm_source=webapp_weixin_changshalongxiang&homePage=linearound&supportSubway=1&switchCity=0&showTopLogo=0&hideFooter=1&showFav=0#!/linearound");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        setContentView(R.layout.activity_bus);
        webView = (WebView) findViewById(R.id.WebView1);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
    }

    public void ApplyNet(View view) {


    }
}
