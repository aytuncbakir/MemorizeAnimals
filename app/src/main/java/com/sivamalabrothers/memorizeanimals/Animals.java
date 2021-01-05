package com.sivamalabrothers.memorizeanimals;


public class Animals{

    String name;
    int image;

    public Animals(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Animals(String name) {
        this.name = name;

    }

    public Animals(){

    }

    public void setName(String name) {

        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {

        return name;
    }

    public int getImage() {
        return image;
    }
}
