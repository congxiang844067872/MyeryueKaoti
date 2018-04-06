package com.bwie.myeryuekaoti.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bwie.myeryuekaoti.R;
import com.bwie.myeryuekaoti.zidingyi.GramophoneView;

public class Main2Activity extends AppCompatActivity {
    private GramophoneView gramophoneView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gramophoneView = (GramophoneView) findViewById(R.id.gramophone);
    }

    public void pauseOrstart(View view) {
        gramophoneView.pauseOrstart();
    }
}
