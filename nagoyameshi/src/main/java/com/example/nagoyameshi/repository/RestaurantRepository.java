package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	public Restaurant findRestaurantById(Integer id);
	Page<Restaurant> findByNameLike(String keyword, Pageable pageable);
	Page<Restaurant> findByNameLikeOrderByCreatedAtDesc(String keyword, Pageable pageable);
	Page<Restaurant> findByNameLikeOrderByPriceLowAsc(String keyword, Pageable pageable);
	Page<Restaurant> findByNameLikeOrderByPriceHighDesc(String keyword, Pageable pageable);
	
	Page<Restaurant> findByNameLikeOrCategory_NameLike(String keyword, String categoryName,Pageable pageable);
	
	Page<Restaurant> findByCategory_NameLike(String categoryName, Pageable pageable);
	Page<Restaurant> findByCategory_NameLikeOrderByCreatedAtDesc(String categoryName, Pageable pageable);
	Page<Restaurant> findByCategory_NameLikeOrderByPriceLowAsc(String categoryName, Pageable pageable);
	Page<Restaurant> findByCategory_NameLikeOrderByPriceHighDesc(String categoryName, Pageable pageable);
	
	Page<Restaurant> findByPriceHighLessThanEqualOrderByCreatedAtDesc(Integer priceHigh, Pageable pageable);
	Page<Restaurant> findByPriceHighLessThanEqualOrderByPriceLowAsc(Integer priceHigh, Pageable pageable);
	Page<Restaurant> findByPriceHighLessThanEqualOrderByPriceHighDesc(Integer priceHigh, Pageable pageable);
	   
	   
	Page<Restaurant> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Restaurant> findAllByOrderByPriceLowAsc(Pageable pageable); 
    Page<Restaurant> findAllByOrderByPriceHighDesc(Pageable pageable); 
    				
    List<Restaurant> findTop5ByOrderByCreatedAtDesc();
	
	List<Restaurant> findByNameContaining(String keyword);	
      
}
