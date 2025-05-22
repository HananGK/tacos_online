package com.hanan.tacosonline.data;

import com.hanan.tacosonline.model.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
}
