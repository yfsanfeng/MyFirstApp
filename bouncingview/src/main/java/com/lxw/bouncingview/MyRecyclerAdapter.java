package com.lxw.bouncingview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * description... //TODO
 *
 * @author lsw
 * @version 1.0, 2017/4/16
 * @see //TODO
 * @since JDK 1.8
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.Viewholder> {
    private List<String> mList;
    private Context mContext;

    public MyRecyclerAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView tv=new TextView(mContext);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        Viewholder viewholder=new Viewholder(tv) ;
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
     TextView tv= (TextView) holder.itemView;
        tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  static class Viewholder extends RecyclerView.ViewHolder{

        public Viewholder(View itemView) {
            super(itemView);
        }
    }
}
