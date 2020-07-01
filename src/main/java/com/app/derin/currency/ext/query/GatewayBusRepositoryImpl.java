package com.app.derin.currency.ext.query;

import com.app.derin.currency.ext.dto.query.QueryFields;
import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GatewayBusRepositoryImpl implements GatewayBusRepository {
    private final Logger log = LoggerFactory.getLogger(GatewayBusRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Map<String, Object>> getResult(String sqlQuery, List<QueryFields> fields) {
        Query query = entityManager.createNativeQuery(sqlQuery);
        List<Object[]> queryResultList = query.getResultList();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] obj: queryResultList)
        {
            Map<String,Object> values = new HashMap<>();
            for(int i = 0; i < fields.size(); i++)
            {
                String fieldName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,fields.get(i).getName());
                values.put(fieldName, obj[i]);
                //r.put(values);
            }
            result.add(values);
        }
        return result;
    }
}
