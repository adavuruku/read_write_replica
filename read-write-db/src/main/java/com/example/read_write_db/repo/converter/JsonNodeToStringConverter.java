package com.example.read_write_db.repo.converter;

import com.example.read_write_db.exception.JDBCConverterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Created by Sherif.Abdulraheem 3/8/2025 - 5:13 PM
 **/
@WritingConverter
@RequiredArgsConstructor
public class JsonNodeToStringConverter implements Converter<JsonNode, String> {
    private final ObjectMapper mapper;

    @Override
    public String convert(JsonNode source) {
        try {
            return mapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new JDBCConverterException("Error converting JsonNodeToString", e);
        }
    }
}
