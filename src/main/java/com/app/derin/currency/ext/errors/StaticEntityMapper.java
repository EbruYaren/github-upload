package com.app.derin.currency.ext.errors;

import java.util.HashMap;
import java.util.Map;

public class StaticEntityMapper {
    public static Map<String, String> entityMap;

    static {
        entityMap = new HashMap<>();
        entityMap.put("curCurrenciesDTO", "cur_currencies");
        entityMap.put("curConfigDTO", "cur_config");
        entityMap.put("curCurrencyDateDTO", "cur_currency_date");
        entityMap.put("curCurrencyMatrixDTO", "cur_currency_matrix");
    }

    private StaticEntityMapper() {
    }
}
