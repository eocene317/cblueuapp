package adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
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
    private Context context;
    public MusicListAdapter(Context context, List<Music> musicList) {
        this.musicList = musicList;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        ImageView albumImg;
        ImageView downloadImg;
        public ViewHolder(View itemView) {
            super(itemView);
            downloadImg = itemView.findViewById(R.id.iv_download);
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

        final Music music = musicList.get(position);

        if(music.getPic().contains("http") || music.getPic().contains("https")){
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
        }

        holder.downloadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music.getUrl()!=null){
                Log.i("Adapter","download clicked!");
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(music.getUrl()));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle(music.getTitle()+ ".mp3");
                request.setDescription(music.getTitle());
                request.setDestinationInExternalFilesDir(context.getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, music.getTitle());
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                long downloadId = manager.enqueue(request);
                Toast.makeText(context.getApplicationContext(),"开始下载"+music.getTitle(),Toast.LENGTH_SHORT).show();}
                else {
                    Toast.makeText(context.getApplicationContext(),"资源失效,无法下载",Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(music.getUrl()==null){
            String content = music.getAuthor()+"   <font color='red'>当前资源不可用</font>";
            holder.author.setText(Html.fromHtml(content));
        }
        else {
            String content = music.getAuthor();
            holder.author.setText(Html.fromHtml(content));
        }
        holder.title.setText(music.getTitle());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

}
