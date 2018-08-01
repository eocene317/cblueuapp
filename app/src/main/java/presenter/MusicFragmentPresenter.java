package presenter;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import cn.cblueu.cblueuapp.MusicActivity;
import data.Music;
import fragment.MusicFragment;
import fragment.MusicFragmentView;
import model.MusicModel;

public class MusicFragmentPresenter {
    MusicFragmentView mView;

    public MusicFragmentPresenter(MusicFragmentView mView) {
        this.mView = mView;
    }

    public void getData(String songName, Integer pageNum, String type){
        mView.showLoading();
        Log.i("MusicFragmentPresenter","开始进行数据请求.....");
        MusicModel.getNetData(songName, pageNum,type, new MusicFragemtCallback() {
            @Override
            public void onSuccess(List<Music> musicList) {
                mView.hideLoading();
                mView.updateData(musicList);
            }

            @Override
            public void onFailure(String msg) {
                mView.hideLoading();
                mView.showFailureMessage(msg);
            }

            @Override
            public void onError() {
                mView.hideLoading();
                mView.showFailureMessage("error");
            }

            @Override
            public void onComplete() {
                mView.hideLoading();
                mView.showFailureMessage("complete");
            }
        });
        Log.i("MusicFragmentPresenter","数据请求结束");
    }
}
