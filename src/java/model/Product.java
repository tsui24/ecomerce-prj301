/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
import java.util.Date;

public class Product {
    private int id;
    private Date createAt;
    private String name;
    private String slug;
    private String description;
    private int categoryId;
    private String storage;
    private double priceDefault;
    private String brand;
    // Constructors
    public Product() {}

    public Product(int id, Date createAt, String name, String slug, String description, int categoryId, String storage, double price_default, String brand) {
        this.id = id;
        this.createAt = createAt;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.categoryId = categoryId;
        this.storage = storage;
        this.priceDefault = price_default;
        this.brand = brand;
    }

    public Product(Date createAt, String name, String slug, String description, int categoryId, String storage, double priceDefault, String brand) {
        this.createAt = createAt;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.categoryId = categoryId;
        this.storage = storage;
        this.priceDefault = priceDefault;
        this.brand = brand;
    }

    public Product(int id, String name, String slug, String description, int categoryId, String storage, double priceDefault, String brand) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.categoryId = categoryId;
        this.storage = storage;
        this.priceDefault = priceDefault;
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    

    public double getPriceDefault() {
        return priceDefault;
    }

    public void setPriceDefault(double priceDefault) {
        this.priceDefault = priceDefault;
    }


    

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}

