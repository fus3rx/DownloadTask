package com.imaginers.onirban.downloadtask;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyProgress myProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void buttonPressed(View view) {
        myProgress = new MyProgress(this);
        myProgress.execute();
    }

    private class MyProgress extends AsyncTask<Void,Integer,String>{

        Context mContext;
        ProgressDialog mProgressDialog;


        public MyProgress(Context context) {
            this.mContext = context;
        }

        //before running do in background
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle("Downloading");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setMax(10);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    dialog.cancel();
                    cancel(true);
                }
            });

            mProgressDialog.show();
        }


        // main background task!!!
        @Override
        protected String doInBackground(final Void... voids) {

                try {

                    for (int i = 0; i <= 10; i++) {
                        Thread.sleep(2000);
                        Log.d("Thread:","Thread executing is " + i);
                        publishProgress(i);
                    }

                    return "Successful";

                } catch (InterruptedException e) {

                Log.d("Thread:","Exception is" + e.getMessage());

                }

                return "failure!";
        }


        //after completing do in background
        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            Toast.makeText(mContext, "Download task Complete!", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);
            int mValue = values[0];
            mProgressDialog.setProgress(mValue);
        }

    }
}
