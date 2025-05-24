package com.example.nagoyameshi.entity;

import java.sql.Timestamp;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "restaurants")
@Data
public class Restaurant {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
	@ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; 
    
    @Column(name = "name")
    private String name;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "description")
    private String description;

    @Column(name = "price_low")
    private Integer priceLow;
    
    @Column(name = "price_high")
    private Integer priceHigh;

    @Column(name = "address")
    private String address;

    @Column(name = "open_at")
    private LocalTime openAt;
    
    @Column(name = "close_at")
    private LocalTime closeAt;

    @Column(name = "closed_on")
    private String closedOn;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;
            
    // Getters and setters for openAt and closeAt
    public LocalTime getOpenAt() {
        return openAt;
    }

    public void setOpenAt(LocalTime openAt) {
        this.openAt = openAt;
    }

    public LocalTime getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(LocalTime closeAt) {
        this.closeAt = closeAt;
    }
    
 // カテゴリ名を取得するメソッドを追加
    public String getCategoryName() {
        return category != null ? category.getName() : null;
    }
    
}
