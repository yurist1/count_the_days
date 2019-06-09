package com.studio.yrchoi.yurist.ddayproject.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studio.yrchoi.yurist.ddayproject.MainActivity;
import com.studio.yrchoi.yurist.ddayproject.Model.Dday;
import com.studio.yrchoi.yurist.ddayproject.Model.DdayViewModel;
import com.studio.yrchoi.yurist.ddayproject.R;

import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentMain extends Fragment {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int DELETE_DIALOG = 2;
    private DdayViewModel mDdayViewModel;
    private static Fragment fragment;
    public static final int RESULT_UPDATE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false); //레이아웃
        fragment = this;
        FloatingActionButton addBtn = view.findViewById(R.id.fab);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveToAddFragment();
            }
        });


        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final DdayListAdapter recyAdapter = new DdayListAdapter(getContext());
        recyclerView.setAdapter(recyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));


        //Model Provider
        mDdayViewModel = ViewModelProviders.of(this).get(DdayViewModel.class);

        //observe : model의 LiveData를 관찰한다.
        mDdayViewModel.getAllDday().observe(this, new Observer<List<Dday>>() {
            @Override
            public void onChanged(@Nullable final List<Dday> words) {
                // Update the cached copy of the words in the adapter.
                recyAdapter.setDday(words);
            }
        });

        //클릭 이벤트
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //update
                moveToUpdateFragment(position, mDdayViewModel.getAllDday().getValue().get(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {
                //delete
                showDeleteDialog(position);
            }
        }));


        return view;
    }
    private void moveToAddFragment() {
        Fragment fragmentAdd = new addListFragment().newInstance();
        MainActivity homeActivity = (MainActivity) getActivity();
        homeActivity.onFragmentChanged(homeActivity.FRAGMENT_MAIN, fragmentAdd);
        fragmentAdd.setTargetFragment(this, 1);
    }

    private void moveToUpdateFragment(int position, Dday dday) {
        Fragment fragmentAdd = new addListFragment().newInstance(position,dday);
        MainActivity homeActivity = (MainActivity) getActivity();
        homeActivity.onFragmentChanged(homeActivity.FRAGMENT_MAIN, fragmentAdd);
        fragmentAdd.setTargetFragment(this, 1);
    }

    private void showDeleteDialog(int position) {
        OneButtonDialogFragment oneButtonDialog = new OneButtonDialogFragment().newInstance("삭제하기",
                "선택한 날짜를 삭제하시겠습니까?", true, position);
        oneButtonDialog.setTargetFragment(this,DELETE_DIALOG);
        oneButtonDialog.show(getFragmentManager(),"delete_dialog");
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Calendar now = Calendar.getInstance();
            int cretNo = now.get(Calendar.MILLISECOND);

            Dday d_day = new Dday(cretNo, data.getStringExtra("title"), data.getStringExtra("date"), data.getBooleanExtra("start_with_1", true));
            mDdayViewModel.insert(d_day);

        } else if(resultCode ==RESULT_UPDATE){
            Dday d_day = new Dday(data.getIntExtra("cretNo",-1), data.getStringExtra("title"), data.getStringExtra("date"), data.getBooleanExtra("start_with_1", true));
            mDdayViewModel.update(d_day);
        } else if(requestCode == DELETE_DIALOG){
            int position = data.getIntExtra("position", -1);
            Dday delete_factor = mDdayViewModel.getAllDday().getValue().get(position);
            Dday d_day = new Dday(delete_factor.getCret_no(), delete_factor.getD_day_nm(), delete_factor.getD_day_date(), delete_factor.getIs_start_from_1());
            mDdayViewModel.delete(d_day);
        }else {
            Toast.makeText(
                    getContext(),
                    "빈칸을 채워주세요.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
