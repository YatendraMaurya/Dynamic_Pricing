package com.nearbuy.dynamic.pricing.dynamicpricing.Config;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Map;

public class DocumentCodecProvider implements CodecProvider {
    private Map<Class, Codec> map;

    public DocumentCodecProvider(Map<Class, Codec> map) {
        this.map = map;
    }
    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        return map.get(aClass);
    }
}
