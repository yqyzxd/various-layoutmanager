package com.wind.layoutmanager;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wind on 16/2/28.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<String>  datas;

    public DataAdapter( List<String>  datas){
        this.datas=datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position%5==0){
            holder.tv.setBackgroundColor(Color.CYAN);
            holder.iv.setImageResource(R.drawable.picture1);
        }else if (position%5==1){
            holder.tv.setBackgroundColor(Color.WHITE);
            holder.iv.setImageResource(R.drawable.picture2);
        }else if (position%5==2){
            holder.tv.setBackgroundColor(Color.BLACK);
            holder.iv.setImageResource(R.drawable.picture3);
        }else if (position%5==3){
            holder.tv.setBackgroundColor(Color.YELLOW);
        }
        holder.tv.setText(datas.get(position));
    }

    public void remove(int position) {
        datas.remove(position);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public  TextView tv;
        public ImageView iv;
        public LinearLayout ll_content;
        public ViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View itemView) {

            ll_content=(LinearLayout)itemView.findViewById(R.id.ll_content);
            tv= (TextView) itemView.findViewById(R.id.tv);
            iv=(ImageView) itemView.findViewById(R.id.iv);

        }
    }
}
