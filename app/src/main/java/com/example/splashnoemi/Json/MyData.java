package com.example.splashnoemi.Json;

import java.io.Serializable;

public class MyData implements Serializable {
    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private String contra;
    private int image;

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    private String red;
}
