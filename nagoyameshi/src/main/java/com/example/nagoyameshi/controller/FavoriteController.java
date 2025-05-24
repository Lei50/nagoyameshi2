package com.example.nagoyameshi.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.RestaurantService;

@Controller
public class FavoriteController {
	private final FavoriteRepository favoriteRepository;
    private final RestaurantRepository restaurantRepository; 
    private final FavoriteService favoriteService; 
    private final RestaurantService restaurantService;
    
    public FavoriteController(FavoriteRepository favoriteRepository, RestaurantRepository restaurantRepository, FavoriteService favoriteService, RestaurantService restaurantService) {        
        this.favoriteRepository = favoriteRepository;
        this.restaurantRepository = restaurantRepository;
        this.favoriteService = favoriteService;
        this.restaurantService = restaurantService;
    }   
    
    @GetMapping("/favorites")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, Model model) {  
        User user = userDetailsImpl.getUser();   
        Page<Favorite> favoritePage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);       
                            
        model.addAttribute("favoritePage", favoritePage);                
        
        return "favorites/index";
    }    
    
    @PostMapping("/restaurants/{restaurantId}/favorites/create")
    public String create(@PathVariable(name = "restaurantId") Integer restaurantId,
                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,                                                  
                         RedirectAttributes redirectAttributes,
                         Model model)
    {    
        Optional<Restaurant> optionalRestaurant = restaurantService.findRestaurantById(restaurantId);                     
        User user = userDetailsImpl.getUser(); 
        
        
        if (optionalRestaurant.isEmpty()){
        	redirectAttributes.addFlashAttribute("errorMessage","店舗が存在しません");
        	System.out.println("店舗が存在してない");
        	return "redirect:/restaurants";        	
        }         
        
        Restaurant restaurant = optionalRestaurant.get();
        Boolean favorite = favoriteService.isFavorite(restaurant, user);
        System.out.println(restaurant);
        System.out.println(favorite);
        if(favorite) {
        	redirectAttributes.addFlashAttribute("errorMessage","登録済みです。");
        	System.out.println("登録済み");
        	return "redirect:/restaurants";    
        }
        
        favoriteService.create(restaurant, user);
        redirectAttributes.addFlashAttribute("successMessage", "お気に入りに追加しました。");    
        
        System.out.println("追加完了");
        return "redirect:/restaurants/{restaurantId}";
        
    }
    
    @PostMapping("/restaurants/{restaurantId}/favorites/{favoriteId}/delete")
    public String delete(@PathVariable(name = "favoriteId") Integer favoriteId, RedirectAttributes redirectAttributes) {        
        favoriteRepository.deleteById(favoriteId);
                
        redirectAttributes.addFlashAttribute("successMessage", "お気に入りを解除しました。");
        
        return "redirect:/restaurants/{restaurantId}";
    }    
}
