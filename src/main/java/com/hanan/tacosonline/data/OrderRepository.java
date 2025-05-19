package com.hanan.tacosonline.data;

import com.hanan.tacosonline.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    Order save(Order order);
}
