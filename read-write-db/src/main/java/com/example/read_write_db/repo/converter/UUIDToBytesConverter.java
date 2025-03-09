package com.example.read_write_db.repo.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/**
 * Created by Sherif.Abdulraheem 3/8/2025 - 5:14 PM
 **/
@WritingConverter
@Slf4j
public class UUIDToBytesConverter implements Converter<UUID, byte[]> {
    @Override
    public byte[] convert(UUID source) {
        log.info(source.toString());
        byte[] uuidBytes = new byte[16];
        ByteBuffer.wrap(uuidBytes)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(source.getMostSignificantBits())
                .putLong(source.getLeastSignificantBits());
        return uuidBytes;
    }
}
