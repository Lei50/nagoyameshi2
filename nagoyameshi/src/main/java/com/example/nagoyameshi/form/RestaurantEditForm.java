package com.example.nagoyameshi.form;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantEditForm {
    @NotNull
    private Integer id;    
    
    @NotBlank(message = "店舗名を入力してください。")
    private String name;
    
    @NotNull(message = "カテゴリーを入力してください。")
    private Category category;  
    
    private MultipartFile imageFile;     
    
    @NotBlank(message = "説明を入力してください。")
    private String description;   
    
    @NotNull(message = "予算（下限金額）を入力してください。")    
    private Integer priceLow;  
    
    @NotNull(message = "予算（上限金額）を入力してください。")    
    private Integer priceHigh;  
    
    @NotBlank(message = "住所を入力してください。")
    private String address;
    
    @NotNull(message = "開店時間を入力してください。")
    private LocalTime openAt;        
    
    @NotNull(message = "閉店時間を入力してください。")
    private LocalTime closeAt;
    
    @NotBlank(message = "定休日を入力してください。")
    private String closedOn;
    
    @NotNull(message = "席数を入力してください。")    
    private Integer capacity;
}