/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class ProductVariant {
    private int id;
    private int productId;
    private String sku;
    private double price;
    private int quantity;
    private int soldQuantity;
    private String storage;

    // Constructors
    public ProductVariant() {}

    public ProductVariant(int id, int productId, String sku, double price, int quantity, int soldQuantity, String storage) {
        this.id = id;
        this.productId = productId;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.soldQuantity = soldQuantity;
        this.storage = storage;
    }

    public ProductVariant(int productId, String sku, double price, int quantity, int soldQuantity, String storage) {
        this.productId = productId;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.soldQuantity = soldQuantity;
        this.storage = storage;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}

