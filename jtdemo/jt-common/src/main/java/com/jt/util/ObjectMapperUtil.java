package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

public class ObjectMapperUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // obj ——> Json
    public static String toJSON(Object obj) {
        if (obj == null) {
            throw new NullPointerException("数据为null");
        }

        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //json ——> obj
    public static <T> T toObject(String json, Class<T> targetClass) {
        if (StringUtils.isEmpty(json) || targetClass == null) {
            throw new NullPointerException("参数不能为null");
        }

        try {
            return MAPPER.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
