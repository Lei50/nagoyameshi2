package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.service.RestaurantService;

@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantRepository restaurantRepository; 
    private final RestaurantService restaurantService; 
    private final CategoryRepository categoryRepository;
    
    public AdminRestaurantController(RestaurantRepository restaurantRepository, RestaurantService restaurantService, CategoryRepository categoryRepository) {
        this.restaurantRepository = restaurantRepository;   
        this.restaurantService = restaurantService;
        this.categoryRepository = categoryRepository;
    }   
    
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword, 
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, 
                        Model model) {
        Page<Restaurant> restaurantPage;   
        
        if (keyword != null && !keyword.isEmpty()) {
            restaurantPage = restaurantRepository.findByNameLikeOrCategory_NameLike("%" + keyword + "%", "%" + keyword + "%", pageable);         
        } else {
            restaurantPage = restaurantRepository.findAll(pageable);
        }  
        
        model.addAttribute("restaurantPage", restaurantPage);
        model.addAttribute("keyword", keyword);
        
        return "admin/restaurants/index";
    } 
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        
        model.addAttribute("restaurant", restaurant);
        
        return "admin/restaurants/show";
    }    
    
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("restaurantRegisterForm", new RestaurantRegisterForm());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/restaurants/register";
    }   
    
    @PostMapping("/create")
    public String create(@ModelAttribute @Validated RestaurantRegisterForm restaurantRegisterForm, 
                         BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {        
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/restaurants/register";
        } 
        
        restaurantService.create(restaurantRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");    
        
        return "redirect:/admin/restaurants";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
    	
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        String imageName = restaurant.getImageName();
        RestaurantEditForm restaurantEditForm = new RestaurantEditForm(
            restaurant.getId(), 
            restaurant.getName(), 
            restaurant.getCategory(), 
            null, 
            restaurant.getDescription(), 
            restaurant.getPriceLow(), 
            restaurant.getPriceHigh(), 
            restaurant.getAddress(), 
            restaurant.getOpenAt(), 
            restaurant.getCloseAt(), 
            restaurant.getClosedOn(), 
            restaurant.getCapacity()
        );
        
        model.addAttribute("imageName", imageName);
        model.addAttribute("restaurantEditForm", restaurantEditForm);
        model.addAttribute("categories", categoryRepository.findAll());
        
        return "admin/restaurants/edit";
    }    
    
    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Validated RestaurantEditForm restaurantEditForm, 
                         BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {        
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/restaurants/edit";
        }   
        
        restaurantService.update(restaurantEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");
        
        return "redirect:/admin/restaurants";
    }  
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
        restaurantRepository.deleteById(id);
                
        redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
        
        return "redirect:/admin/restaurants";
    }   
    
}