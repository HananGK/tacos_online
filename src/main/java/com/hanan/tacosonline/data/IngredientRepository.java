package com.hanan.tacosonline.data;

import com.hanan.tacosonline.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
