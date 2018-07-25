package com.nearbuy.dynamic.pricing.dynamicpricing.Config;

public interface EventsListener<T> {
    void consume(T t) throws Exception;
    Class<T> getclazz();
}
