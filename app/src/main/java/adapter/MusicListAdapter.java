package adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.cblueu.cblueuapp.R;
import data.Music;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
    private List<Music> musicList;

    public MusicListAdapter(List<Music> musicList) {
        this.musicList = musicList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        ImageView albumImg;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_name);
            author = itemView.findViewById(R.id.tv_singer_album);
            albumImg = itemView.findViewById(R.id.iv_album_img);
        }

    }


    @NonNull
    @Override
    public MusicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_search_result,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MusicListAdapter.ViewHolder holder, int position) {
        OkHttpClient client = new OkHttpClient();

        Music music = musicList.get(position);
        Request request = new Request.Builder().url(music.getPic()).build();
        Response response = null;
        final android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                holder.albumImg.setImageBitmap((Bitmap) message.obj);
                return false;
            }
        });
        client.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        InputStream is = response.body().byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        Message msg =new Message();
                        msg.obj = bitmap;//可以是基本类型，可以是对象，可以是List、map等；
                        handler.sendMessage(msg);
                    }
                }
        );

        holder.author.setText(music.getAuthor());
        holder.title.setText(music.getTitle());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

}
