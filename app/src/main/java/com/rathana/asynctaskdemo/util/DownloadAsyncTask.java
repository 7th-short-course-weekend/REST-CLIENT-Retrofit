package com.rathana.asynctaskdemo.util;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

public class DownloadAsyncTask extends AsyncTask<Void,Integer,Boolean> {

    ProgressBarDialog progressBarDialog;
    AppCompatActivity activity;

    public DownloadAsyncTask(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressBarDialog= new ProgressBarDialog();
        progressBarDialog.show(activity.getSupportFragmentManager(),"downloading");
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        for(int i=1;i<=100;i++){
            try {
                publishProgress(i);
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean)
            this.cancel(false);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBarDialog.updateProgress(values[0]);
        progressBarDialog.updateResult(values[0]);
    }
}
