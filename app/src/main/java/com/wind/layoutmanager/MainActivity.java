package com.wind.layoutmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.tv_tantan).setOnClickListener(this);
        findViewById(R.id.tv_zuo).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_tantan:
                startActivity(new Intent(this,TanTanActivity.class));
                break;
            case R.id.tv_zuo:
                startActivity(new Intent(this,ZuoActivity.class));
                break;
        }
    }
}
