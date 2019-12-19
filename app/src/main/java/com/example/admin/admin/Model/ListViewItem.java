package com.example.admin.admin.Model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class ListViewItem implements Serializable {
    private Drawable iconDrawable ;
    private String lnoStr;
    private String nameStr;
    private String addr1Str;
    private String addr2Str;
    private String addr3Str;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getLnoStr() {
        return lnoStr;
    }

    public void setLnoStr(String lnoStr) {
        this.lnoStr = lnoStr;
    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    public String getAddr1Str() {
        return addr1Str;
    }

    public void setAddr1Str(String addr1Str) {
        this.addr1Str = addr1Str;
    }

    public String getAddr2Str() {
        return addr2Str;
    }

    public void setAddr2Str(String addr2Str) {
        this.addr2Str = addr2Str;
    }

    public String getAddr3Str() {
        return addr3Str;
    }

    public void setAddr3Str(String addr3Str) {
        this.addr3Str = addr3Str;
    }
}
