package com.skyfree.bestcalculator.model;

/**
 * Created by KienBeu on 3/7/2018.
 */

public class Convert {
    private Integer mAvatar;
    private String mConvertName;

    public Integer getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(Integer mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmConvertName() {
        return mConvertName;
    }

    public void setmConvertName(String mConvertName) {
        this.mConvertName = mConvertName;
    }

    public Convert() {
    }

    public Convert(Integer mAvatar, String mConvertName) {
        this.mAvatar = mAvatar;
        this.mConvertName = mConvertName;
    }
}
