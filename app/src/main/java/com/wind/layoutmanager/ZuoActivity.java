package com.wind.layoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ZuoActivity extends AppCompatActivity implements DragRecycleView.OnDragListener{
    CardLayoutManager layoutManager;
    DataAdapter dataAdapter;
     DragRecycleView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuo);

        recyclerView= (DragRecycleView) findViewById(R.id.recyclerView);
        layoutManager=new CardLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        List<String> datas=new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        datas.add("5");
        datas.add("6");
        datas.add("7");
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        datas.add("5");
        datas.add("6");
        datas.add("7");
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        datas.add("5");
        datas.add("6");
        datas.add("7");
        dataAdapter=new DataAdapter(datas);
        recyclerView.setAdapter(dataAdapter);
        //int count=recyclerView.getChildCount();
        layoutManager.setOnLayoutListener(new CardLayoutManager.OnLayoutListener() {
            @Override
            public void layoutFinish() {
                View top=recyclerView.getChildAt(recyclerView.getChildCount()-1);
                recyclerView.setTopView(top);
            }
        });
        //setContentView(R.layout.std_card_inner);
        recyclerView.setOnDragListener(this);

    }

    @Override
    public void onDrag(float translationX, float translationY) {
        layoutManager.updateLayout(translationX,translationY);
    }


    @Override
    public void dragRemove() {
        //移除topview
        layoutManager.reset();
        dataAdapter.remove(0);
        layoutManager.removeTopViewEnd();

    }
}
