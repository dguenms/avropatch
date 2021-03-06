package io.datanerds.avropatch.value.conversion;

import io.datanerds.avropatch.value.type.UuidType;
import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericFixed;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConversion extends Conversion<UUID> implements UuidType {

    @Override
    public Schema getRecommendedSchema() {
        return SCHEMA;
    }

    @Override
    public Class<UUID> getConvertedType() {
        return UUID.class;
    }

    @Override
    public String getLogicalTypeName() {
        return NAME;
    }

    @Override
    public GenericFixed toFixed(UUID value, Schema schema, LogicalType type) {
        return new GenericFixed() {
            @Override
            public byte[] bytes() {
                ByteBuffer buffer = ByteBuffer.allocate(SIZE);
                buffer.putLong(value.getLeastSignificantBits());
                buffer.putLong(value.getMostSignificantBits());
                return buffer.array();
            }

            @Override
            public Schema getSchema() {
                return SCHEMA;
            }
        };
    }

    @Override
    public UUID fromFixed(GenericFixed value, Schema schema, LogicalType type) {
        ByteBuffer buffer = ByteBuffer.wrap(value.bytes());
        long leastSignificantBits = buffer.getLong();
        long mostSignificantBits = buffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}