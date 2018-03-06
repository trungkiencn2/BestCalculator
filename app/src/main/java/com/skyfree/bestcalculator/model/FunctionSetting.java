package com.skyfree.bestcalculator.model;

/**
 * Created by KienBeu on 3/6/2018.
 */

public class FunctionSetting {
    private Integer mImage;
    private String mFunctionName;

    public Integer getmImage() {
        return mImage;
    }

    public void setmImage(Integer mImage) {
        this.mImage = mImage;
    }

    public String getmFunctionName() {
        return mFunctionName;
    }

    public void setmFunctionName(String mFunctionName) {
        this.mFunctionName = mFunctionName;
    }

    public FunctionSetting() {
    }

    public FunctionSetting(Integer mImage, String mFunctionName) {
        this.mImage = mImage;
        this.mFunctionName = mFunctionName;
    }
}
