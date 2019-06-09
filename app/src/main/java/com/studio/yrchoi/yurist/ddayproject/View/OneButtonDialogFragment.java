package com.studio.yrchoi.yurist.ddayproject.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studio.yrchoi.yurist.ddayproject.R;


public class OneButtonDialogFragment extends DialogFragment {

    TextView txtTitle, txtMessage;
    TextView txtBtnOK;
    RelativeLayout relativeBtnOk;
    static Context mContext;
    String dialog_title, dialog_msg;
    TextView txtDialogMessage;
    private boolean isCancelable = false;
    private int mPosition;

    public static OneButtonDialogFragment newInstance( String dialog_title, String dialog_msg, boolean cancelable, int position) {
        OneButtonDialogFragment oneButtonDialog = new OneButtonDialogFragment();
        Bundle args = new Bundle();
        args.putString("dialog_title", dialog_title);
        args.putString("dialog_msg", dialog_msg);
        args.putBoolean("dialog_cancel", cancelable);
        args.putInt("position", position);
        oneButtonDialog.setArguments(args);
        return oneButtonDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_dialog, container);
        // Connect Layout XML to view variables
        mContext = getContext();
        dialog_title = getArguments().getString("dialog_title");
        dialog_msg = getArguments().getString("dialog_msg");
        isCancelable = getArguments().getBoolean("dialog_cancel");
        txtTitle = view.findViewById(R.id.txt_dialog_ok_title);
        txtMessage = view.findViewById(R.id.txt_dialog_ok_message);
        txtBtnOK = view.findViewById(R.id.txt_btn_dialog_ok);
        relativeBtnOk = view.findViewById(R.id.relative_btn_ok);
        mPosition = getArguments().getInt("position");

        relativeBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("position", mPosition);
                getTargetFragment().onActivityResult(FragmentMain.DELETE_DIALOG, 0, data);
                dismiss();
            }
        });


        txtTitle.setText(Html.fromHtml("<font color=\"#000000\">" + dialog_title + "</font>"));

        if (null != dialog_msg) {
            txtMessage.setText(Html.fromHtml(dialog_msg));
        } else {
            txtMessage.setText("");
        }


        // remove dialog title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // remove dialog background
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //txtDialogMessage = view.findViewById(R.id.txt_dialog_ok_message);
        //txtDialogMessage.setText(mDialogMessage);
        setCancelable(isCancelable);

        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
            //Log.d("KSC_Tag", "OneButtonDialogFragment: Exception = "+e.getMessage());
        }
    }

    public void setMessage(String msg) {
        dialog_msg = msg;
    }


}
