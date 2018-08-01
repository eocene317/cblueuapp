package fragment;

import java.util.List;

import data.Music;

public interface MusicFragmentView {

    void showLoading();// 显示进度条
    void hideLoading();// 隐藏进度条
    void updateData(List<Music> musicList); // 更新数据
    void showFailureMessage(String msg); //数据请求失败，显示出错信息
    void showErrorMessage(); //数据请求异常，显示出错信息
}
