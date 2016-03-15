package com.wind.layoutmanager;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wind on 16/2/28.
 */
public class CardLayoutManager extends RecyclerView.LayoutManager {
    private int visibleCount;

    private float mScaleFactor=0.4f;
    private int mTranslationYThreshold;
    public CardLayoutManager(Context context, int visibleCount){
        this.visibleCount=visibleCount;
        mTranslationYThreshold= DisplayUtil.dip2px(context,300);


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
        if (getItemCount()<getVisibleCount()){
            count=getItemCount()-1;
        }else {
            count=getVisibleCount()-1;
        }

        for(int i=count;i>=0;i--){
           final View scrap=recycler.getViewForPosition(i);
            addView(scrap);
            measureChildWithMargins(scrap,0,0);

            int width=getDecoratedMeasuredWidth(scrap);
            int height=getDecoratedMeasuredHeight(scrap);

            float scale;
            int delta;
            if (!update){
                //初始布局
                if (mSecondViewScale!=0&&i==0){
                    //从mSecondViewScale渐变到1
                    scrap.setScaleX(mSecondViewScale);
                    scrap.setScaleY(mSecondViewScale);
                    scrap.setTranslationX(0);
                    scrap.setTranslationY(0);


                    scaleArray.put(0,1f);
                }else {
                    scale=1-mScaleFactor*i;
                    scrap.setScaleX(scale);
                    scrap.setScaleY(scale);
                    scaleArray.put(i,scale);
                    scrap.setTranslationX(0);
                    scrap.setTranslationY(0);
                }

            }else {
                if (i!=0){

                    float max=mTranslationYThreshold/(scaleArray.get(i-1)-scaleArray.get(i));
                    scale=Math.abs(topViewTranslationY)/max+scaleArray.get(i);
                    scrap.setScaleX(scale);
                    scrap.setScaleY(scale);

                }else {

                    scrap.setScaleX(1);
                    scrap.setScaleY(1);
                    scrap.setTranslationY(topViewTranslationY);
                    scrap.setTranslationX(topViewTranslationX);

                }

            }
            layoutDecorated(scrap,0,0,width,height);

        }
        listener.layoutFinish();
    }





    private float mSecondViewScale=0;
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
        mSecondViewScale=getChildAt(0).getScaleX();
        Log.e("Scale",mSecondViewScale+"");
        topViewTranslationX=0;
        topViewTranslationY=0;
        update=false;
    }

    public void removeTopViewEnd() {
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(mSecondViewScale,1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSecondViewScale= (float) animation.getAnimatedValue();
                Log.e("ValueAnimator",mSecondViewScale+"");
                requestLayout();
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();

    }


    public interface OnLayoutListener{
        void layoutFinish();
    }

}
