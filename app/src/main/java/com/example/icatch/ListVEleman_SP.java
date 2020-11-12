package com.example.icatch;

public class ListVEleman_SP {
    private String name;
    private int image;

    public ListVEleman_SP(String name,int image) {
        this.setName(name);
        this.setImage(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
