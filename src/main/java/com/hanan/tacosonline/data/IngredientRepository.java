package com.hanan.tacosonline.data;

import com.hanan.tacosonline.model.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
}
