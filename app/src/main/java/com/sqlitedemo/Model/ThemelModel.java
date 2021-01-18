package com.sqlitedemo.Model;


import android.graphics.Bitmap;

public class ThemelModel {

    private String themeid;
    private String ThemeName;
    private String image;
    private byte[] pic;

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getThemeid() {
        return themeid;
    }

    public void setThemeid(String themeid) {
        this.themeid = themeid;
    }

    public String getThemeName() {
        return ThemeName;
    }

    public void setThemeName(String themeName) {
        ThemeName = themeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
