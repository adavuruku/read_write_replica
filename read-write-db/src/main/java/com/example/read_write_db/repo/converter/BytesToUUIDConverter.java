package com.example.read_write_db.repo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by Sherif.Abdulraheem 3/8/2025 - 5:13 PM
 **/
@ReadingConverter
public class BytesToUUIDConverter implements Converter<byte[], UUID> {
    @Override
    public UUID convert(byte[] source) {
        ByteBuffer buf = ByteBuffer.wrap(source);
        return new UUID(buf.getLong(), buf.getLong());
    }
}
