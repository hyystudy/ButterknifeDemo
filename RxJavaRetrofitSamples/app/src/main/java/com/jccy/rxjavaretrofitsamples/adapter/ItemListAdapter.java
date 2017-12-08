package com.jccy.rxjavaretrofitsamples.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jccy.rxjavaretrofitsamples.R;
import com.jccy.rxjavaretrofitsamples.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ZbViewHolder> {

    private Context mContext;
    List<Item> mData = new ArrayList<>();

    public ItemListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ZbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zb_list_item_layout, parent, false);
        return new ZbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ZbViewHolder holder, int position) {
        Item item = mData.get(position);
        holder.des.setText(item.des);
        Glide.with(mContext).load(item.image_url).into(holder.image);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setImages(List<Item> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    static class ZbViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.des)
        TextView des;
        public ZbViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
