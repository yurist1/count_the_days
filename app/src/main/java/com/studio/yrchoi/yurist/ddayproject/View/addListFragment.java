package com.studio.yrchoi.yurist.ddayproject.View;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.yrchoi.yurist.ddayproject.Model.Dday;
import com.studio.yrchoi.yurist.ddayproject.R;

import java.util.Calendar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class addListFragment extends Fragment {
    private Calendar calendar;
    private static TextView etDate;
    private static TextView result;
    private CheckBox checkStart;
//    private CheckBox checkToopBar;
    private EditText et_title;
    private static Boolean isStart = true;
    private Context mContext;
    private int mPosition;
    private static Dday mDday;
    private static Boolean isUpdate = false;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final int RESULT_UPDATE = 1;


    public addListFragment newInstance(int position, Dday dday) {
        //수정
        mPosition = position;
        mDday = dday;
        isUpdate = true;
        return new addListFragment();
    }
    public addListFragment newInstance() {
        //생성
        isUpdate = false;
        return new addListFragment();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_list_fragment, container, false); //레이아웃

        mContext = getContext();
        ImageView backBtn = view.findViewById(R.id.imgBackBtn);
        ImageView applyBtn = view.findViewById(R.id.imgApply);
        etDate = view.findViewById(R.id.et_day);
        result = view.findViewById(R.id.dday_result);
        checkStart = view.findViewById(R.id.check_count_1);
//        checkToopBar = view.findViewById(R.id.check_inf_on_top);
        et_title = view.findViewById(R.id.et_title);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(isUpdate == false) {
                    //insert
                    if (TextUtils.isEmpty(et_title.getText())) {
                        assert getTargetFragment() != null;
                        getTargetFragment().onActivityResult(1, RESULT_CANCELED, replyIntent);
                    } else {
                        String title = et_title.getText().toString();
                        replyIntent.putExtra("title", title);
                        replyIntent.putExtra("date", etDate.getText().toString());
                        replyIntent.putExtra("start_with_1", checkStart.isChecked());
//                        replyIntent.putExtra("settingTopbar", checkToopBar.isChecked());
                        assert getTargetFragment() != null;
                        getTargetFragment().onActivityResult(1, RESULT_OK, replyIntent);
                    }
                } else {
                    //update
                    String title = et_title.getText().toString();
                    replyIntent.putExtra("title", title);
                    replyIntent.putExtra("date", etDate.getText().toString());
                    replyIntent.putExtra("cretNo", mDday.getCret_no());
                    replyIntent.putExtra("start_with_1", checkStart.isChecked());
                    assert getTargetFragment() != null;
                    getTargetFragment().onActivityResult(1, RESULT_UPDATE, replyIntent);
                }


                //뒤로가기
                getFragmentManager().popBackStack();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogfragment = new DatePickerDialogTheme1();
                dialogfragment.show(getFragmentManager(), "Theme 1");
            }
        });

        checkStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculateDays(etDate.getText().toString(), isChecked);
                isStart = !isStart;
            }
        });

        //update일 경우
        if(isUpdate == true){
            et_title.setText(mDday.getD_day_nm());
            etDate.setText(mDday.getD_day_date());
            checkStart.setChecked(mDday.is_start_from_1);
            calculateDays(mDday.getD_day_date(), mDday.is_start_from_1);
        }
        return view;
    }


    private static void calculateDays(String date, Boolean isStartChecked) {
        if(date.length()>0){
            Calendar calendar = Calendar.getInstance();
            int tYear = calendar.get(Calendar.YEAR);
            int tMonth = calendar.get(Calendar.MONTH) + 1;
            int tDay = calendar.get(Calendar.DAY_OF_MONTH);

            String[] ddate = date.replace(".", "-").split("-");
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

            if (diffDay > 0) {
                if(tCalendar.getTime().getYear() == dCalendar.getTime().getYear() &&
                        tCalendar.getTime().getMonth() == dCalendar.getTime().getMonth() &&
                        tCalendar.getTime().getDay() == dCalendar.getTime().getDay()){
                    diffDay = 1;
                } else {
                    diffDay = (int) r + 1;
                }
                if (isStartChecked == true) {
                    result.setText("D+" + diffDay);
                } else {
                    result.setText("D+" + (diffDay - 1));
                }
            } else {
                result.setText("D" + (diffDay - 1));
            }
        }


    }


    public static class DatePickerDialogTheme1 extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            etDate.setText(year + "-" + (month+1) + "-" + day);
            calculateDays(etDate.getText().toString(), isStart);
        }
    }

}