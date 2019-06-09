package com.studio.yrchoi.yurist.ddayproject.Model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DdayRepository {
    private DdayDAO mDdayDao;
    private LiveData<List<Dday>> mAllDday;

    public DdayRepository(Application application){
        DdayDataBase db = DdayDataBase.getInstance(application);
        //RoomDatabase에 있는 Dao를 가지고 온다.
        mDdayDao = db.ddayDAO();
        //Dao의 쿼리를 이용하여 저장되어 있는 모든 dday를 가지고 온다.
        mAllDday = mDdayDao.getAllDday();

    }

    public LiveData<List<Dday>> getAllDday(){
        return mAllDday;
    }

    //Dday를 삭제하는 함수
    public void delete(final Dday d_day){
        Runnable addRunnable= new Runnable() {
            @Override
            public void run() {
                mDdayDao.deleteDday(d_day);
            }
        };
        Executor diskIO= Executors.newSingleThreadExecutor();

        diskIO.execute(addRunnable);
//        new insertAsyncTask(mDdayDao).execute(d_day);
    }

    //Dday를 수정하는 함수
    public void update(final Dday d_day){
        Runnable addRunnable= new Runnable() {
            @Override
            public void run() {
                mDdayDao.updateDday(d_day);
            }
        };
        Executor diskIO= Executors.newSingleThreadExecutor();

        diskIO.execute(addRunnable);
//        new insertAsyncTask(mDdayDao).execute(d_day);
    }

    //Dday를 추가하는 함수
    public void insert(final Dday d_day){
        Runnable addRunnable= new Runnable() {
            @Override
            public void run() {
                mDdayDao.insertDday(d_day);
            }
        };
        Executor diskIO= Executors.newSingleThreadExecutor();

        diskIO.execute(addRunnable);
//        new insertAsyncTask(mDdayDao).execute(d_day);
    }

    private static class insertAsyncTask extends AsyncTask<Dday,Void,Void> {
        private DdayDAO mAsyncTaskDao;

        insertAsyncTask(DdayDAO dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Dday... ddays) {
            mAsyncTaskDao.insertDday(ddays[0]);
            return null;
        }
    }
}