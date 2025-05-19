package com.hanan.tacosonline.data;

import com.hanan.tacosonline.model.Ingredient;
import com.hanan.tacosonline.model.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository{
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Taco save(Taco design) {
        long tacoId = saveTacoInfo(design);
        design.setId(tacoId);
        for (Ingredient ingredient : design.getIngredients()) {
            saveIngredient(tacoId, ingredient);
        }
        return null;
    }

    private void saveIngredient(long tacoId, Ingredient ingredient) {
        jdbc.update("INSERT INTO Taco_Ingredients (taco_id, ingredient_id) VALUES (?, ?)", tacoId, ingredient.getId());
    }

    private long saveTacoInfo(Taco design) {
        design.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("INSERT INTO Taco (name, createdAt) VALUES (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(design.getName(), new Timestamp(design.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        Number key = keyHolder.getKey();
        return key.longValue();
    }
}
