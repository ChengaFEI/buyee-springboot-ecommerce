package com.chengfei.buyee.admin.setting;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chengfei.buyee.common.entity.Currency;
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
    public List<Currency> findAllByOrderByNameAsc();
}
