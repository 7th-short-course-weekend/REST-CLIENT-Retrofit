package com.rathana.asynctaskdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rathana.asynctaskdemo.util.DownloadAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDownload(View view){
        DownloadAsyncTask downloadAsyncTask=new DownloadAsyncTask(this);
        downloadAsyncTask.execute();
    }
}
