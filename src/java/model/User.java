/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.Instant;
import java.util.Date;

/**
 *
 * @author admin
 */
public class User {
    private int id;
    private String createAt;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;
    private String avt;
    private boolean isBanned;
    private String email;

    public User() {
        
    }

    public User(String firstName, String lastName, String username, String password, String role,  boolean isBanned, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;

        this.isBanned = isBanned;
        this.email = email;
    }
    
    public User(String createAt, String firstName, String lastName, String username, String password, String role, String avt, boolean isBanned, String email) {
        this.createAt = createAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.avt = avt;
        this.isBanned = isBanned;
        this.email = email;
    }
    
    public User(int id, String firstName, String lastName, String role, boolean isBanned, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isBanned = isBanned;
        this.email = email;
    }
    
    public User(int id, String createAt, String firstName, String lastName, String username, String password, String role, String avt, boolean isBanned, String email) {
        this.id = id;
        this.createAt = createAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.avt = avt;
        this.isBanned = isBanned;
        this.email = email;
    }
    
    public boolean isIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    
    

    // Getter và Setter cho từng thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }   
}
