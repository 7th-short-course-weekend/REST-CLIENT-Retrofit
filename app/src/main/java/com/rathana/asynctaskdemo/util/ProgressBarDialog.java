package com.rathana.asynctaskdemo.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rathana.asynctaskdemo.R;

public class ProgressBarDialog extends DialogFragment {

    ProgressBar progressBar;
    TextView textResult;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("downloading ...");
        View view =LayoutInflater.from(getActivity()).
                inflate(R.layout.progress_dialog_layout,null);
        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        textResult=view.findViewById(R.id.result);
        builder.setView(view);

        return builder.create();
    }

    public void updateProgress(int val){
        progressBar.setProgress(val);
    }

    public void updateResult(int val){
        textResult.setText(val+"%");
    }
}
