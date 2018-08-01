package data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Music {
    private String type;
    private String link; // 原链接
    private String songId;
    private String title;
    private String author;
    private String url; // 下载链接
    private String lrc;
    private String pic;

    public Music(String type, String link, String songId, String title, String author, String url, String lrc, String pic) {
        this.type = type;
        this.link = link;
        this.songId = songId;
        this.title = title;
        this.author = author;
        this.url = url;
        this.lrc = lrc;
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getPic() {return pic ;}

    public void setPic(String pic) {
        this.pic = pic;
    }
}

