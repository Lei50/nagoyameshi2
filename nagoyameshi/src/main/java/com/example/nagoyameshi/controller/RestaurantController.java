package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
	private final RestaurantRepository restaurantRepository;  
	private final ReviewRepository reviewRepository;   
    private final ReviewService reviewService;  
    private final FavoriteRepository favoriteRepository; 
    private final FavoriteService favoriteService;  
    private final CategoryRepository categoryRepository;  
    
    public RestaurantController(RestaurantRepository restaurantRepository, ReviewRepository reviewRepository, ReviewService reviewService, 
    		FavoriteRepository favoriteRepository, FavoriteService favoriteService, CategoryRepository categoryRepository) {
        this.restaurantRepository = restaurantRepository; 
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
        this.favoriteRepository = favoriteRepository;
        this.favoriteService = favoriteService;
        this.categoryRepository = categoryRepository;
       
    }       
  
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
    					@RequestParam(required = false) String category,
    					@RequestParam(required = false) Integer priceHigh,
    					@RequestParam(required = false) Integer priceLow,
    					@RequestParam(name = "order", required = false) String order,
    					@RequestParam(defaultValue = "0") int page,
    					@RequestParam(defaultValue = "10") int size,
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                        Model model) 
    {
        Page<Restaurant> restaurantPage;
        List<Category> categories = categoryRepository.findAll();                   
        
        if (keyword != null && !keyword.isEmpty()) {   
        	if (order != null && order.equals("priceLowAsc")) {
                restaurantPage = restaurantRepository.findByNameLikeOrderByPriceLowAsc("%" + keyword + "%", pageable);
        	} else if (order != null && order.equals("priceHighDesc")) {
        		restaurantPage = restaurantRepository.findByNameLikeOrderByPriceHighDesc("%" + keyword + "%", pageable);
        	} else {	
        		restaurantPage = restaurantRepository.findByNameLikeOrderByCreatedAtDesc("%" + keyword + "%", pageable);
            }  
         } else if (category != null  && !category.isEmpty()) {
        	 if (order != null && order.equals("priceLowAsc")) {
                restaurantPage = restaurantRepository.findByCategory_NameLikeOrderByPriceLowAsc("%" + category + "%", pageable);
        	 } else if (order != null && order.equals("priceHighDesc")) {
        		 restaurantPage = restaurantRepository.findByCategory_NameLikeOrderByPriceHighDesc("%" + category + "%", pageable);
        	 } else {	 
        		 restaurantPage = restaurantRepository.findByCategory_NameLikeOrderByCreatedAtDesc("%" + category + "%", pageable);
             }  
         } else if (priceHigh!= null) {
        	 if (order != null && order.equals("priceLowAsc")) {
            	restaurantPage = restaurantRepository.findByPriceHighLessThanEqualOrderByPriceLowAsc(priceHigh, pageable);	
        	 } else if (order != null && order.equals("priceHighDesc")) {
        		 restaurantPage = restaurantRepository.findByPriceHighLessThanEqualOrderByPriceHighDesc(priceHigh, pageable);
        	 } else {	 
        		restaurantPage = restaurantRepository.findByPriceHighLessThanEqualOrderByCreatedAtDesc(priceHigh, pageable);
             }  
         } else {
        	 if (order != null && order.equals("priceLowAsc")) {
        		 restaurantPage = restaurantRepository.findAllByOrderByPriceLowAsc(pageable);
             } else if (order != null && order.equals("priceHighDesc")) {
            	 restaurantPage = restaurantRepository.findAllByOrderByPriceHighDesc(pageable);  
             } else {	 
            	 restaurantPage = restaurantRepository.findAllByOrderByCreatedAtDesc(pageable);   
             }    
         }
                
        model.addAttribute("restaurantPage", restaurantPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categories);
        model.addAttribute("priceHigh", priceHigh);  
        model.addAttribute("category", category);
        model.addAttribute("order", order);
        
        return "restaurants/index";        
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        Favorite favorite = null;
        boolean hasUserAlreadyReviewed = false;  
        boolean isFavorite = false;
        
        if (userDetailsImpl != null) {
            User user = userDetailsImpl.getUser();
            hasUserAlreadyReviewed = reviewService.hasUserAlreadyReviewed(restaurant, user);
            isFavorite = favoriteService.isFavorite(restaurant, user);
            if (isFavorite) {
                favorite = favoriteRepository.findByRestaurantAndUser(restaurant, user);
            }  
        }
        
        List<Review> newReviews = reviewRepository.findTop6ByRestaurantOrderByCreatedAtDesc(restaurant);        
        long totalReviewCount = reviewRepository.countByRestaurant(restaurant);  
        
        model.addAttribute("restaurant", restaurant);   
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        model.addAttribute("favorite", favorite);
        model.addAttribute("hasUserAlreadyReviewed", hasUserAlreadyReviewed);
        model.addAttribute("newReviews", newReviews);        
        model.addAttribute("totalReviewCount", totalReviewCount); 
        model.addAttribute("isFavorite", isFavorite); 
        
        return "restaurants/show";
    }     
}
