package com.studio.yrchoi.yurist.ddayproject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.studio.yrchoi.yurist.ddayproject.View.FragmentMain;
import com.studio.yrchoi.yurist.ddayproject.View.IntroFragment;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    public static final int CHECK_INTERVAL = 1000; // 1초
    public static final int DELAY_TIMER = 5;
    private Fragment startFragment; //시작화면
    private Fragment introFragment; //인트로 화면
    private Fragment mainFragment; //메인 화면
    public RelativeLayout relativeMain;
    public static final int FRAGMENT_MAIN = 1;           //메인 화면
    public static final int FRAGMENT_ADD = 2;           //메인 화면

    public static final String TAG_FRAG_MAIN = "fragment_main";                             //메인화면 태그

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        introFragment = new IntroFragment();
        mainFragment = new FragmentMain();
        relativeMain = (RelativeLayout) findViewById(R.id.main_container);
        startFragment = introFragment;


        setDefaultFragment();

    }
    //기본 프래그먼트 설정
    public void setDefaultFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_container, startFragment);
        transaction.commitAllowingStateLoss(); //트랜색션 적용
    }


    //프래그먼트 변경 (화면 전환)
    public void onFragmentChanged(int index, Fragment target_fragment) { //Modified to use Bundle for communication between Fragment
        if (index == FRAGMENT_MAIN) { //메인화면
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, target_fragment, TAG_FRAG_MAIN).addToBackStack(null).
                    commitAllowingStateLoss();
        }
    }

}
