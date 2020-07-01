package com.app.derin.currency.ext.services;

import org.apache.kafka.common.protocol.types.Field;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

@Component
public interface ConvertXmlToJsonObjectService {
    JSONObject convert(String s);
}
