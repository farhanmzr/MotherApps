package com.aksantara.mother.Model;

public class InformasiModel {

    private String image, category;

    public InformasiModel() {
    }

    public InformasiModel(String image, String category) {
        this.image = image;
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
