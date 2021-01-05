package com.sivamalabrothers.memorizeanimals;

import java.util.ArrayList;

public class AnimalMenuItem {

    private String adi;
    private int imgId;

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public static ArrayList<AnimalMenuItem> getAnimalMenuItems(int level, int backOrNext, ArrayList<Animals> animals,
                                                                int hangiOyun){

        ArrayList<AnimalMenuItem> AnimalMenuItemArrayList = new ArrayList<AnimalMenuItem>();

        if(level > animals.size())
            level = animals.size();
        else if(level == 0)
            level = 1;

        if(hangiOyun == 0){
            level = level *2;
        }else if(hangiOyun == 1){
            level = level *3;
        }else if(hangiOyun == 2){
            level = level *6;
        }else if(hangiOyun == 3){
            level = level *10;
        }

        if(backOrNext == 0) {

            for (int i = 0; i < level; i++){
                AnimalMenuItem AnimalMenuItem = new AnimalMenuItem();
                AnimalMenuItem.setAdi(animals.get(i).getName());
                AnimalMenuItem.setImgId(animals.get(i).getImage());
                AnimalMenuItemArrayList.add(AnimalMenuItem);
            }
        }else if(backOrNext == 1){

            for (int i = level; i < animals.size(); i++) {
                AnimalMenuItem AnimalMenuItem = new AnimalMenuItem();
                AnimalMenuItem.setAdi(animals.get(i).getName());
                AnimalMenuItem.setImgId(animals.get(i).getImage());
                AnimalMenuItemArrayList.add(AnimalMenuItem);
            }
        }
        return AnimalMenuItemArrayList;
    }
}
