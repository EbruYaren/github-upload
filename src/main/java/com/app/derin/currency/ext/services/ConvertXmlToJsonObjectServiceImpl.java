package com.app.derin.currency.ext.services;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConvertXmlToJsonObjectServiceImpl implements ConvertXmlToJsonObjectService {
    @Override
    public JSONObject convert(String s) {
        return XML.toJSONObject(s);
    }
}
