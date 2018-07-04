package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

public interface AppSubscriber<T> {

    void consume(T t);
    Class<T> getClazz();
}
