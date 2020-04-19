package com.studio.yrchoi.yurist.ddayproject.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.yrchoi.yurist.ddayproject.Model.Dday;
import com.studio.yrchoi.yurist.ddayproject.R;

import java.util.Calendar;
import java.util.List;

public class DdayListAdapter extends RecyclerView.Adapter<DdayListAdapter.DdayViewHolder> {

    class DdayViewHolder extends RecyclerView.ViewHolder {

        private final TextView dayNm;
        private final TextView dayDate;
        private final TextView Dday;

        private DdayViewHolder(View itemView) {
            super(itemView);
            dayNm = itemView.findViewById(R.id.dayNm);
            dayDate = itemView.findViewById(R.id.day);
            Dday = itemView.findViewById(R.id.d_day);
        }
    }

    private LayoutInflater mInflater;
    private List<Dday> mDday;

    DdayListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DdayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.listview_item, viewGroup, false);
        return new DdayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DdayViewHolder holder, int position) {
        if (mDday != null) {
            Dday current = mDday.get(position);
            holder.dayNm.setText(current.getD_day_nm());
            holder.dayDate.setText(current.getD_day_date());
                holder.Dday.setText(getDday(current.getD_day_date(), current.getIs_start_from_1()));


        } else {
            holder.dayNm.setText("No Word");
        }
    }

    private String getDday(String date, Boolean isStartChecked) {
        String result = "";
        Calendar calendar = Calendar.getInstance();
        int tYear = calendar.get(Calendar.YEAR);
        int tMonth = calendar.get(Calendar.MONTH) + 1;
        int tDay = calendar.get(Calendar.DAY_OF_MONTH);

        String[] ddate = date.split("-");
        int dYear = Integer.parseInt(ddate[0]);
        int dMonth = Integer.parseInt(ddate[1].trim());
        int dDay = Integer.parseInt(ddate[2].trim());


        Calendar dCalendar = Calendar.getInstance();
        dCalendar.set(dYear, dMonth - 1, dDay);

        Calendar tCalendar = Calendar.getInstance();
        tCalendar.set(tYear, tMonth - 1, tDay);

        long t = tCalendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        long d = dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
        int r = (int) ((t - d) / (24 * 60 * 60 * 1000));                   //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈

        int diffDay = (int) r + 1;


//        result.setText(""+dCalendar.compareTo(tCalendar));

        if (diffDay > 0) {
            if(tCalendar.getTime().getYear() == dCalendar.getTime().getYear() &&
                    tCalendar.getTime().getMonth() == dCalendar.getTime().getMonth() &&
                    tCalendar.getTime().getDay() == dCalendar.getTime().getDay()){
                diffDay = 1;
            } else {
                diffDay = (int) r + 1;
            }
            if (isStartChecked == true) {
                result = "D+" + diffDay;
            } else {
                result = "D+" + (diffDay - 1);
            }
        } else {
            result = "D" + (diffDay - 1);
        }

        return result;
    }

    void setDday(List<Dday> dday){
        mDday = dday;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDday != null) {
            return mDday.size();
        } else
            return 0;
    }
}
