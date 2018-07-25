package com.nearbuy.dynamic.pricing.dynamicpricing.Config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.codec.BookingCodec;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.codec.NotifictionCodec;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.NotificationMongoModel;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.PalBooking;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MongoConfig {

    @Autowired
    AppProperties env;

    private static final Logger logger=LoggerFactory.getLogger(MongoConfig.class);
    private List<? extends CodecProvider> provider;

    @Bean
    public MongoDatabase getDb(){

        String db = env.getProperty("mongo.db");
        String[] hosts = env.getProperty("mongo.hosts").split(":");
        String host = hosts[0];
        int port = Integer.valueOf(hosts[1]);
        String userName = env.getProperty("mongo.username");
        String passwd = env.getProperty("mongo.password");
        MongoClient mongoClient = new MongoClient(host,port);
        CodecRegistry codecRegistry = CodecRegistries.fromProviders(getProvider());
        return mongoClient.getDatabase(db).withCodecRegistry(codecRegistry);
    }

    @Bean(name = "Dynamic_Collection")
    public MongoCollection<Document> geBookingtCollections(){
        MongoCollection<Document> coll = getDb().getCollection("demo");
        coll.createIndex(new Document(BookingDao.ORDER_ID, 1));
        coll.createIndex(new Document(BookingDao.MERCHANT_ID,1).append(BookingDao.TIMESLOT,1));
        return coll;
    }

    @Bean(name = "Notification_Collection")
    public MongoCollection<Document> getNotificationCollection(){
        MongoCollection<Document> coll =getDb().getCollection("Notification");
        coll.createIndex(new Document(NotificationDao.USER_ID,1));
        coll.createIndex(new Document(NotificationDao.INVENTORY_ID,1));
        return coll;
    }

    @Bean
    public CodecProvider getProvider() {
        Map<Class, Codec> map = new HashMap<>();
        map.put(Document.class, new DocumentCodec());
        map.put(BsonDocument.class, new BsonDocumentCodec());
        map.put(String.class, new StringCodec());
        map.put(Integer.class, new IntegerCodec());
        map.put(Long.class, new LongCodec());
        map.put(Boolean.class, new BooleanCodec());
        map.put(BsonBoolean.class, new BsonBooleanCodec());
        map.put(PalBooking.class,new BookingCodec());
        map.put(NotificationMongoModel.class,new NotifictionCodec());
        map.put(ObjectId.class,new ObjectIdCodec());
        return new DocumentCodecProvider(map);
    }
}
