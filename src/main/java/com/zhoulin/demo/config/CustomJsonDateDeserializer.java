package com.zhoulin.demo.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期反序列化
 */
public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date = jp.getText();
            try {
                return format.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }
