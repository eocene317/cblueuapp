package fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.MusicListAdapter;
import cn.cblueu.cblueuapp.MessageEvent;
import cn.cblueu.cblueuapp.R;
import data.Music;
import presenter.MusicFragmentPresenter;


public class MusicFragment extends Fragment implements MusicFragmentView {

    protected boolean isCreated = false;
    RecyclerView recyclerView;
    private String type;
    MusicListAdapter adapter;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    MusicFragmentPresenter presenter;
    private List<Music> musicList = new ArrayList<>();;
    public static MusicFragment newInstance(String type) {
        MusicFragment newFragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type );

        newFragment.setArguments(bundle);
        Log.i(type,"fragment initial");
        return newFragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        Bundle args = getArguments();
        if(args!=null){
            type = args.getString("type");
        }
        Log.i(type,"fragment created");
        EventBus.getDefault().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);
        //提示用户作用
        if(musicList.size() == 0 && type == "qq"){
            Toast.makeText(getActivity(),"请输入你想要查询的音乐名~",Toast.LENGTH_SHORT).show();
        }
        recyclerView = rootView.findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MusicListAdapter(musicList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);
        presenter = new MusicFragmentPresenter(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(type,"activity created");

    }


    @Override
    public void onAttach(Context context) {
        Log.i(type,"attached");

        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(type,"Detached");
        EventBus.getDefault().unregister(this);
    }


    public void updateMusicList() {
        Bitmap pic = Bitmap.createBitmap(24,24,Bitmap.Config.ARGB_8888);
        pic.eraseColor(Color.parseColor("#FF0000"));
        //musicList.add(new Music("qq","http://","123",type,"wz","http","dsgdjas", pic));
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateData(List<Music> musicList) {
        adapter = new MusicListAdapter(musicList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if(event.type.equals("song")){
//            Toast.makeText(getActivity(), type+"收到了"+event.message, Toast.LENGTH_SHORT).show();
            presenter.getData(event.message,1,type);
        }

    }

}
