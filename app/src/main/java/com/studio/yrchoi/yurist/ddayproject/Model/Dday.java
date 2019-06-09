package com.studio.yrchoi.yurist.ddayproject.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "d_day")
public class Dday {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cret_no")
    public int cret_no;
    @ColumnInfo(name = "d_day_nm")
    public String d_day_nm;
    @ColumnInfo(name = "d_day_date")
    public String d_day_date;
    @ColumnInfo(name = "is_start_from_1")
    public Boolean is_start_from_1;


    @NonNull
    public int getCret_no() {
        return cret_no;
    }

    public void setCret_no(@NonNull int cret_no) {
        this.cret_no = cret_no;
    }

    public String getD_day_nm() {
        return d_day_nm;
    }

    public void setD_day_nm(String d_day_nm) {
        this.d_day_nm = d_day_nm;
    }

    public String getD_day_date() {
        return d_day_date;
    }

    public void setD_day_date(String d_day_date) {
        this.d_day_date = d_day_date;
    }

    public Boolean getIs_start_from_1() {
        return is_start_from_1;
    }

    public void setIs_start_from_1(Boolean is_start_from_1) {
        this.is_start_from_1 = is_start_from_1;
    }



    public Dday(@NonNull int cret_no, String d_day_nm, String d_day_date, Boolean is_start_from_1) {
        this.cret_no = cret_no;
        this.d_day_nm = d_day_nm;
        this.d_day_date = d_day_date;
        this.is_start_from_1 = is_start_from_1;
    }
}
