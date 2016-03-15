package com.wind.layoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wind on 16/3/15.
 */
public class TanTanActivity extends AppCompatActivity implements DragRecycleView.OnDragListener{
    StackLayoutManager layoutManager;
    DataAdapter dataAdapter;
    DragRecycleView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuo);

        recyclerView= (DragRecycleView) findViewById(R.id.recyclerView);
        layoutManager=new StackLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        List<String> datas=new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        datas.add("5");
        datas.add("6");
        datas.add("7");
        datas.add("8");
        datas.add("9");
        datas.add("10");
        datas.add("11");
        datas.add("12");
        datas.add("13");
        datas.add("14");
        datas.add("15");
        datas.add("16");
        datas.add("17");
        datas.add("18");
        datas.add("19");
        datas.add("20");

        dataAdapter=new DataAdapter(datas);
        recyclerView.setAdapter(dataAdapter);
        //int count=recyclerView.getChildCount();
        layoutManager.setOnLayoutListener(new StackLayoutManager.OnLayoutListener() {
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
       // layoutManager.removeTopViewEnd();

    }
}
