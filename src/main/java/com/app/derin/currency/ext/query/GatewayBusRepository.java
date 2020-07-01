package com.app.derin.currency.ext.query;

import com.app.derin.currency.ext.dto.query.QueryFields;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface GatewayBusRepository {
    List<Map<String, Object>> getResult(String sqlQuery, List<QueryFields> fields);
}
