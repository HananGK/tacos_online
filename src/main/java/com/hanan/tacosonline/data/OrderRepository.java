package com.hanan.tacosonline.data;

import com.hanan.tacosonline.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>{
}
