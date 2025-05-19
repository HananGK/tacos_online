package com.hanan.tacosonline.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanan.tacosonline.model.Order;
import com.hanan.tacosonline.model.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Repository
public class JdbcOrderRepository implements OrderRepository{

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
        orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
        objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        for (Taco taco : order.getTacos()) {
            saveTacoToOrder(orderId, taco);
        }
        return order;
    }

    private void saveTacoToOrder(long orderId, Taco taco) {
        Map<String, Object> map = new HashMap<>();
        map.put("taco", taco.getId());
        map.put("tacoOrder", orderId);
        orderTacoInserter.execute(map);
    }

    private long saveOrderDetails(Order order) {
        Map<String, Object> map = objectMapper.convertValue(order, Map.class);
        map.put("placedAt", order.getPlacedAt());
        Number number = orderInserter.executeAndReturnKey(map).longValue();
        return number.longValue();
    }
}
