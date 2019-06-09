package com.studio.yrchoi.yurist.ddayproject.View;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.yrchoi.yurist.ddayproject.MainActivity;
import com.studio.yrchoi.yurist.ddayproject.R;

public class IntroFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_intro, container, false); //레이아웃

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 메인 액티비티 실행
                Fragment fragmentMain = new FragmentMain();
                MainActivity homeActivity = (MainActivity) getActivity();
                try{
                    homeActivity.onFragmentChanged(homeActivity.FRAGMENT_MAIN, fragmentMain);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

        }, 2500);




        return loginView;
    }
}
