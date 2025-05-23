package com.example.simpleasynctask;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private static final String TEXT_STATE = "currentText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);

        mTextView = findViewById(R.id.textView1);

        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(TEXT_STATE);
            if (savedText != null) {
                mTextView.setText(savedText);
            }
        }
    }

    public void startTask(View view) {
        if (mTextView != null) {
            mTextView.setText(R.string.napping);
            new SimpleAsyncTask(mTextView).execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }
}