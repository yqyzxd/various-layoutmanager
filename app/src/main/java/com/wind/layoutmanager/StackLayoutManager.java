package com.wind.layoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wind on 16/2/28.
 */
public class StackLayoutManager extends RecyclerView.LayoutManager {
    private int visibleCount;

    private float mScaleFactor=0.05f;
    private int mTranslationYThreshold;
    public StackLayoutManager(Context context,int visibleCount){
        this.visibleCount=visibleCount;
        mTranslationYThreshold= DisplayUtil.dip2px(context,100);


    }
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    boolean update;
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

            detachAndScrapAttachedViews(recycler);
            layoutChildren(recycler);



    }



    /**
     * 存储每个view初始状态下的translationY
     */
    public SparseArray<Float> scaleArray=new SparseArray();
    public SparseIntArray translationArray=new SparseIntArray();
    private RecyclerView.Recycler mRecycler;
    private void layoutChildren(RecyclerView.Recycler recycler) {
        mRecycler=recycler;
        if (!update){
            translationArray.clear();
            scaleArray.clear();
        }
        int count=0;
        if (getItemCount()<getVisibleCount()+1){
            count=getItemCount()-1;
        }else {
            count=getVisibleCount();
        }

        for(int i=count;i>=0;i--){
            View scrap=recycler.getViewForPosition(i);
            addView(scrap);
            measureChildWithMargins(scrap,0,0);

            int width=getDecoratedMeasuredWidth(scrap);
            int height=getDecoratedMeasuredHeight(scrap);

            float scale;
            int delta;
            if (!update){
                scale=1-mScaleFactor*i;
                scrap.setScaleX(scale);
                scrap.setScaleY(scale);
                scaleArray.put(i,scale);
                if (i!=getVisibleCount()){

                    delta= (int) (height*(mScaleFactor*i))/2+20*i;

                }else {
                    //最后一个为隐藏的,只有当拖动topview时才会显现
                    delta=(int) (height*(mScaleFactor*i))/2;
                }
                scrap.setTranslationY(delta);
                scrap.setTranslationX(0);
                translationArray.put(i,delta);
            }else {

                //float max=mTranslationYThreshold/(1-scaleArray.get(i));
                if (i!=0){
                    float max;
                    if((scaleArray.get(i-1)-scaleArray.get(i))==0){
                        scale=scaleArray.get(i);
                    }else {
                        max=mTranslationYThreshold/(scaleArray.get(i-1)-scaleArray.get(i));
                        scale=Math.abs(topViewTranslationY)/max+scaleArray.get(i);
                    }

                    scrap.setScaleX(scale);
                    scrap.setScaleY(scale);
                    //需要设置 setTranslationY
                    if ((translationArray.get(i-1)-translationArray.get(i))==0){
                        delta=(translationArray.get(i));
                    }else {
                        max=mTranslationYThreshold/(translationArray.get(i-1)-translationArray.get(i));
                        delta= (int) (Math.abs(topViewTranslationY)/max+translationArray.get(i));

                    }


                    scrap.setTranslationY(delta);
                }else {
                    if (i!=getVisibleCount()) {
                        scrap.setScaleX(1);
                        scrap.setScaleY(1);
                        scrap.setTranslationY(topViewTranslationY);
                        scrap.setTranslationX(topViewTranslationX);
                    }else {
                        //最后一个隐藏的view
                        float max=0;
                        if ((scaleArray.get(i-1)-scaleArray.get(i))==0){
                            scale =scaleArray.get(i);
                        }else {
                            max=mTranslationYThreshold/(scaleArray.get(i-1)-scaleArray.get(i));
                            scale=Math.abs(topViewTranslationY)/max+scaleArray.get(i);
                        }

                        scrap.setScaleX(scale);
                        scrap.setScaleY(scale);

                        //需要设置 setTranslationY
                        if ((translationArray.get(i-1)-translationArray.get(i))==0){
                            delta=translationArray.get(i);
                        }else {
                            max=mTranslationYThreshold/(translationArray.get(i-1)-translationArray.get(i));
                            delta= (int) (Math.abs(topViewTranslationY)/max+translationArray.get(i));
                        }


                        scrap.setTranslationY(delta);
                    }
                }

            }
            layoutDecorated(scrap,0,0,width,height);

        }
        listener.layoutFinish();
    }





    float topViewTranslationY,topViewTranslationX;
    public void updateLayout(float topViewTranslationX, float topViewTranslationY) {
        if(Math.abs(topViewTranslationY)<=mTranslationYThreshold){
            update=true;
            this.topViewTranslationX=topViewTranslationX;
            this.topViewTranslationY=topViewTranslationY;
            requestLayout();
            }
        }

    private OnLayoutListener listener;
    public void setOnLayoutListener(OnLayoutListener listener){
        this.listener=listener;
    }

    public int getVisibleCount() {
        return visibleCount;
    }

    public void reset() {

        topViewTranslationX=0;
        topViewTranslationY=0;
        update=false;
    }


    public interface OnLayoutListener{
        void layoutFinish();
    }

}
