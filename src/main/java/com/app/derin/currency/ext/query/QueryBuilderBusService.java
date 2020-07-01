package com.app.derin.currency.ext.query;

import com.app.derin.currency.ext.dto.query.QueryFields;

import java.util.List;
import java.util.Map;

public interface QueryBuilderBusService {
    List<Map<String, Object>> getResult(String strQuery, List<QueryFields> fields);
}
