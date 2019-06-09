package com.studio.yrchoi.yurist.ddayproject.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class DdayViewModel extends AndroidViewModel {
    private DdayRepository mRepository;
    private LiveData<List<Dday>> mAllDday;

    public DdayViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DdayRepository(application);
        mAllDday = mRepository.getAllDday();
    }

    public LiveData<List<Dday>> getAllDday(){
        return mAllDday;
    }

    public void insert(Dday d_day){
        mRepository.insert(d_day);
    }

    public void update(Dday d_day){
        mRepository.update(d_day);
    }

    public void delete(Dday d_day){
        mRepository.delete(d_day);
    }
}
