package model;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.Music;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import presenter.MusicFragemtCallback;

public class MusicModel {

    public static final MediaType URLENCODE = MediaType.parse("application/x-www-form-urlencoded");

    public static void getNetData(final String param, Integer pageNum,String type, final MusicFragemtCallback callback) {
        // 利用postDelayed方法模拟网络请求数据的耗时操作
        // 网络请求

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                callback.onSuccess((List<Music>) message.obj);
                return false;
            }
        });
        Log.i("MusicModel", "网络请求中");
        final Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(URLENCODE, "filter=name&page=1&type="+type+"&input=" + param);
        Request request = new Request.Builder()
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .url("http://music.sonimei.cn/ ")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response responseData) throws IOException {
                try {
                    JSONObject response = new JSONObject(responseData.body().string());
                    Integer code = response.getInt("code");
                    JSONArray data = response.getJSONArray("data");
                    List<Music> musicList = gson.fromJson(data.toString(), new TypeToken<List<Music>>() {}.getType());
                    Log.i("MusicModel","数据请求成功");
                    Message msg =new Message();
                    msg.obj = musicList;//可以是基本类型，可以是对象，可以是List、map等；
                    handler.sendMessage(msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


//
//----------------------------------
            }
        });

    }
}