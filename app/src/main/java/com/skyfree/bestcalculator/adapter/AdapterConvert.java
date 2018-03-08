package com.skyfree.bestcalculator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skyfree.bestcalculator.R;
import com.skyfree.bestcalculator.model.Convert;

import java.util.ArrayList;

/**
 * Created by KienBeu on 3/7/2018.
 */

public class AdapterConvert extends RecyclerView.Adapter<AdapterConvert.ViewHolder> {

    private Context mContext;
    private ArrayList<Convert> mListConvert;

    public AdapterConvert(Context mContext, ArrayList<Convert> mListConvert) {
        this.mContext = mContext;
        this.mListConvert = mListConvert;
    }

    @Override
    public AdapterConvert.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_convert, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterConvert.ViewHolder holder, int position) {
        holder.mImgAvatar.setImageResource(mListConvert.get(position).getmAvatar());
        holder.mTvConvertName.setText(mListConvert.get(position).getmConvertName());
    }

    @Override
    public int getItemCount() {
        return mListConvert.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImgAvatar;
        TextView mTvConvertName;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar_convert);
            mTvConvertName = (TextView) itemView.findViewById(R.id.tv_convert_name);
            mContext = itemView.getContext();
        }
    }
}
