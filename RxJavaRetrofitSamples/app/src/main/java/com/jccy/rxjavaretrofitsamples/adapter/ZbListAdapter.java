package com.jccy.rxjavaretrofitsamples.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jccy.rxjavaretrofitsamples.R;
import com.jccy.rxjavaretrofitsamples.model.ZhuangBiImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class ZbListAdapter extends RecyclerView.Adapter<ZbListAdapter.ZbViewHolder> {

    private Context mContext;
    List<ZhuangBiImage> mData = new ArrayList<>();

    public ZbListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ZbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZbViewHolder(View.inflate(mContext, R.layout.zb_list_item_layout, null));
    }

    @Override
    public void onBindViewHolder(ZbViewHolder holder, int position) {
        ZhuangBiImage zhuangBiImage = mData.get(position);
        holder.des.setText(zhuangBiImage.description);
        Glide.with(mContext).load(zhuangBiImage.image_url).into(holder.image);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setImages(List<ZhuangBiImage> zhuangBiImages) {
        mData.clear();
        mData.addAll(zhuangBiImages);
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
