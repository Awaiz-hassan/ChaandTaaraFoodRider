package com.apps.chaandtaarafoodrider.Model;

public class FoodItemModel {

    private String name;
    private String image;
    private String price;
    private String category;
    private String id;

    public FoodItemModel(String name, String image, String price, String category, String id) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.category = category;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
