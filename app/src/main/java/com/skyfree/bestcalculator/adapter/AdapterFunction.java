package com.skyfree.bestcalculator.adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skyfree.bestcalculator.R;
import com.skyfree.bestcalculator.model.FunctionSetting;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by KienBeu on 3/6/2018.
 */

public class AdapterFunction extends BaseAdapter{

    private Context mContext;
    private ArrayList<FunctionSetting> mListFunction;

    public AdapterFunction(Context mContext, ArrayList<FunctionSetting> mListFunction) {
        this.mContext = mContext;
        this.mListFunction = mListFunction;
    }

    @Override
    public int getCount() {
        return mListFunction.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mRow = inflater.inflate(R.layout.item_function, null);
        ImageView mImgImage = (ImageView) mRow.findViewById(R.id.img_image);
        TextView mTvFunctionName = (TextView) mRow.findViewById(R.id.tv_function_name);

        FunctionSetting fs = mListFunction.get(i);
        mImgImage.setImageResource(fs.getmImage());
        mTvFunctionName.setText(fs.getmFunctionName());

        return mRow;
    }
}
