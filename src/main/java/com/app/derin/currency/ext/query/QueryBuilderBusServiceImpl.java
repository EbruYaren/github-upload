package com.app.derin.currency.ext.query;


import com.app.derin.currency.ext.dto.query.QueryFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QueryBuilderBusServiceImpl implements QueryBuilderBusService {

    private final Logger log = LoggerFactory.getLogger(QueryBuilderBusServiceImpl.class);

    private final GatewayBusRepository gatewayBusRepository;

    public QueryBuilderBusServiceImpl(GatewayBusRepository gatewayBusRepository) {
        this.gatewayBusRepository = gatewayBusRepository;
    }

    @Override
    public List<Map<String, Object>> getResult(String strQuery, List<QueryFields> fields) {

        log.debug("{}", strQuery);
        return gatewayBusRepository.getResult(strQuery, fields);
    }

}
