package com.example.read_write_db.repo.converter;


import com.example.read_write_db.exception.JDBCConverterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Created by Sherif.Abdulraheem 3/8/2025 - 5:13 PM
 **/
@ReadingConverter
@RequiredArgsConstructor
public class StringToJsonNodeConverter implements Converter<String, JsonNode> {
    private final ObjectMapper mapper;

    @Override
    public JsonNode convert(String source) {
        try {
            return mapper.readValue(source, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new JDBCConverterException("Error converting StringToJsonNode", e);
        }
    }
}
