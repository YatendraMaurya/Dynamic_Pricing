package com.nearbuy.dynamic.pricing.dynamicpricing.dao.codec;


import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;

public abstract class AppCodec<T> implements Codec<T> {
    public Codec<Document> documentCodec = new DocumentCodec();

    @Override
    public T decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document doc=documentCodec.decode(bsonReader,decoderContext);
        return _decode(doc);
    }

    protected abstract T _decode(Document doc);


    @Override
    public void encode(BsonWriter bsonWriter, T t, EncoderContext encoderContext) {
        Document doc=_encode(t);
        documentCodec.encode(bsonWriter,doc,encoderContext);

    }

    protected abstract Document _encode(T t);

    @Override
    public Class<T> getEncoderClass() {
        return null;
    }
}
