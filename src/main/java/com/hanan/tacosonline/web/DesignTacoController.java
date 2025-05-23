package com.hanan.tacosonline.web;

import com.hanan.tacosonline.data.IngredientRepository;
import com.hanan.tacosonline.data.TacoRepository;
import com.hanan.tacosonline.model.Ingredient;
import com.hanan.tacosonline.model.Ingredient.Type;
import com.hanan.tacosonline.model.Order;
import com.hanan.tacosonline.model.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;

    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        fillIngredients(model);

        model.addAttribute("tktn", new Taco());
        return "design";
    }

    @ModelAttribute("tktn")
    public Taco taco(){
        log.info("Iniciando Taco");
        return new Taco();
    }

    @ModelAttribute
    public Order order(){
        return new Order();
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(ingredient -> ingredient.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute(name="tktn") Taco design, Errors errors, Model model, @ModelAttribute Order order) {
        if(errors.hasErrors()) {
            fillIngredients(model);
            return "design";
        }
        Taco saved = tacoRepository.save(design);
        log.info("Processing Design Taco: {}", design);

        order.addDesign(design);
        return "redirect:/orders/current";
    }


    public void fillIngredients(Model model){

//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        );

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Ingredient.Type[] types = Ingredient.Type.values();

        for(Ingredient.Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }
}
