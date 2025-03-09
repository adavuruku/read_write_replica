package com.example.read_write_db.repo.converter;

/**
 * Created by Sherif.Abdulraheem 09/03/2025 - 00:55
 **/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

@Converter(autoApply = true)
public class JsonNodeConverter implements AttributeConverter<JsonNode, PGobject> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(JsonNode jsonNode) {
        if (jsonNode == null) return null;
        try {
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");  // ✅ PostgreSQL expects explicit type
            pgObject.setValue(objectMapper.writeValueAsString(jsonNode));
            return pgObject;
        } catch (Exception e) {
            throw new RuntimeException("Error converting JsonNode to PGobject", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(PGobject pgObject) {
        if (pgObject == null || pgObject.getValue() == null) return null;
        try {
            return objectMapper.readTree(pgObject.getValue());
        } catch (Exception e) {
            throw new RuntimeException("Error converting PGobject to JsonNode", e);
        }
    }
}
