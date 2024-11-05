/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class BillDetail {
    private int id;
    private int billId;
    private int productVariantId;
    private int quantity;

    public BillDetail() {
    }

    public BillDetail(int id, int billId, int productVariantId, int quantity) {
        this.id = id;
        this.billId = billId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
    }

    public BillDetail(int billId, int productVariantId, int quantity) {
        this.billId = billId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
