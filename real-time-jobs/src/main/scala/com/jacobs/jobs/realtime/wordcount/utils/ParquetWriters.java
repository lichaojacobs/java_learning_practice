package com.jacobs.jobs.realtime.wordcount.utils;

import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.reflect.ReflectData;
import org.apache.flink.formats.parquet.ParquetBuilder;
import org.apache.flink.formats.parquet.ParquetWriterFactory;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.io.OutputFile;

/**
 * @author lichao
 * @date 2018/11/25
 */
public class ParquetWriters {

    private static final CompressionCodecName DEFAULT_CODEC_NAME = CompressionCodecName.SNAPPY;

    public static <T> ParquetWriterFactory<T> forReflectRecord(Class<T> type) {
        return forReflectRecord(type, DEFAULT_CODEC_NAME);
    }


    public static <T> ParquetWriterFactory<T> forReflectRecord(Class<T> type, CompressionCodecName codecName) {
        final String schemaString = ReflectData.get().getSchema(type).toString();
        final ParquetBuilder<T> builder = (out) -> createAvroParquetWriter(schemaString, ReflectData.get(), codecName, out);
        return new ParquetWriterFactory<>(builder);
    }

    private static <T> ParquetWriter<T> createAvroParquetWriter(String schemaString, GenericData dataModel, CompressionCodecName codecName,
        OutputFile out) throws IOException {

        final Schema schema = new Schema.Parser().parse(schemaString);
        return AvroParquetWriter.<T>builder(out).withSchema(schema).withDataModel(dataModel).withCompressionCodec(codecName).build();
    }
}

