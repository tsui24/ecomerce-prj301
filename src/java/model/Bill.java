/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Bill {
    private int id;
    private Date createdDate;
    private int userId;
    private String sdt;
    private String location;
    private String status;
    public Bill() {
    }

    public Bill(int id, Date createdDate, int userId, String sdt, String location) {
        this.id = id;
        this.createdDate = createdDate;
        this.userId = userId;
        this.sdt = sdt;
        this.location = location;
    }
    
    public Bill(Date createdDate, int userId, String sdt, String location, String status) {
        this.createdDate = createdDate;
        this.userId = userId;
        this.sdt = sdt;
        this.location = location;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
}
