package org.apache.flink.streaming.StephanEwen;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.streaming.util.serialization.DeserializationSchema;

/**
 * From https://gist.github.com/StephanEwen/d515e10dd1c609f70bed
 * @param <T>
 */
public class AvroDeserializationSchema<T> implements DeserializationSchema<T> {

    private final Class<T> avroType;

    private transient DatumReader<T> reader;
    private transient BinaryDecoder decoder;

    public AvroDeserializationSchema(Class<T> avroType) {
        this.avroType = avroType;
    }

    @Override
    public T deserialize(byte[] message) {
        ensureInitialized();

        try {
            decoder = DecoderFactory.get().binaryDecoder(message, decoder);
            return reader.read(null, decoder);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEndOfStream(T nextElement) {
        return false;
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeExtractor.getForClass(avroType);
    }

    private void ensureInitialized() {
        if (reader == null) {
            if (org.apache.avro.specific.SpecificRecordBase.class.isAssignableFrom(avroType)) {
                reader = new SpecificDatumReader<T>(avroType);
            } else {
                reader = new ReflectDatumReader<T>(avroType);
            }
        }
    }
}