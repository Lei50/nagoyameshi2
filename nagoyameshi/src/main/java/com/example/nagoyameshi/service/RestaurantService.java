package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository; 	
    
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;         
    }   
    public Optional<Restaurant> findRestaurantById(Integer id){
    	return restaurantRepository.findById(id);
    }
    
    public LocalTime convertToLocalTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(timeString, formatter);
    }
    
    @Transactional
    public void create(RestaurantRegisterForm restaurantRegisterForm) {
    	Restaurant restaurant = new Restaurant();       	
        MultipartFile imageFile = restaurantRegisterForm.getImageFile();
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            restaurant.setImageName(hashedImageName);
        }
        
        restaurant.setName(restaurantRegisterForm.getName());      
        restaurant.setCategory(restaurantRegisterForm.getCategory()); 
        restaurant.setDescription(restaurantRegisterForm.getDescription());
        restaurant.setPriceLow(restaurantRegisterForm.getPriceLow());
        restaurant.setPriceHigh(restaurantRegisterForm.getPriceHigh());
        restaurant.setAddress(restaurantRegisterForm.getAddress()); 
        restaurant.setOpenAt(restaurantRegisterForm.getOpenAt());
        restaurant.setCloseAt(restaurantRegisterForm.getCloseAt());
        restaurant.setClosedOn(restaurantRegisterForm.getClosedOn());
        restaurant.setCapacity(restaurantRegisterForm.getCapacity()); 
        
        restaurantRepository.save(restaurant);
    }  
    
    @Transactional
    public void update(RestaurantEditForm restaurantEditForm) {
    	Restaurant restaurant = restaurantRepository.getReferenceById(restaurantEditForm.getId());
        MultipartFile imageFile = restaurantEditForm.getImageFile();
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            restaurant.setImageName(hashedImageName);
        }
        
        restaurant.setName(restaurantEditForm.getName());    
        restaurant.setCategory(restaurantEditForm.getCategory()); 
        restaurant.setDescription(restaurantEditForm.getDescription());
        restaurant.setPriceLow(restaurantEditForm.getPriceLow());
        restaurant.setPriceHigh(restaurantEditForm.getPriceHigh());
        restaurant.setAddress(restaurantEditForm.getAddress());
        restaurant.setOpenAt(restaurantEditForm.getOpenAt());
        restaurant.setCloseAt(restaurantEditForm.getCloseAt());
        restaurant.setClosedOn(restaurantEditForm.getClosedOn());
        restaurant.setCapacity(restaurantEditForm.getCapacity());  
                    
        restaurantRepository.save(restaurant);
    }    
    
    // UUIDを使って生成したファイル名を返す
    public String generateNewFileName(String fileName) {
        String[] fileNames = fileName.split("\\.");                
        for (int i = 0; i < fileNames.length - 1; i++) {
            fileNames[i] = UUID.randomUUID().toString();            
        }
        String hashedFileName = String.join(".", fileNames);
        return hashedFileName;
    }     
    
    // 画像ファイルを指定したファイルにコピーする
    public void copyImageFile(MultipartFile imageFile, Path filePath) {           
        try {
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }          
    } 
    
    public boolean isWithinOperatingHours(Integer restaurantId, LocalTime reservationTime) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));
        LocalTime openAt = restaurant.getOpenAt();
        LocalTime closeAt = restaurant.getCloseAt();
        return !reservationTime.isBefore(openAt) && !reservationTime.isAfter(closeAt);
    }    
   
    public Page<Restaurant> findByCategory(Category category, Pageable pageable) {
        return restaurantRepository.findByCategory_NameLike("%" + category + "%", pageable);
    }

    public Page<Restaurant> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }
	
}

