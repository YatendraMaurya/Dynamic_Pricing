package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppConstants;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Configuration
public class SubscriberConfig {

    public static final Logger logger=LoggerFactory.getLogger(SubscriberConfig.class);
    private final HashMap<String, AppSubscriber> topicConsumer;
    List<KafkaConsumer> consumers=new ArrayList<>();
    private Thread thread;
    private boolean flag=true;

    public SubscriberConfig(){
        this.topicConsumer = new HashMap<>();
        Runtime r= Runtime.getRuntime();
        Thread hook =new Thread(()->{
            try {
                logger.info("got shutdown hook");
                destroy();
            } catch (InterruptedException e) {
                logger.error("error in shutdown hook", e);
            }
        });
        r.addShutdownHook(hook);
    }

    @Autowired
    private BookingSubscriber bookingSubscriber;

    @Autowired
    private CashbackUpdateSubscriber cashbackUpdateSubscriber;



    @Autowired
    AppProperties env;
    private Properties getProps(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "dynamicpricing687");
        props.put("enable.auto.commit", true);
        props.put("max.poll.records", 5);
        props.put("auto.commit.interval.ms", 1000);
        props.put("session.timeout.ms", 300000);
        props.put("request.timeout.ms", 300001);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @PostConstruct
    void init()
    {
        //topicConsumer.put(AppConstants.PAL_EVENT,new ArrayList<>(Arrays.asList(palEventConsumer)));
        topicConsumer.put(AppConstants.PAL_EVENT, bookingSubscriber);
//        topicConsumer.put(AppConstants.CREATE_EVENT, new Object());
        topicConsumer.put(AppConstants.UPDATE_EVENT, cashbackUpdateSubscriber);
    }

    @PostConstruct
    void initkafka()
    {
        int noofthread=1;
        for(int i=0;i<noofthread;i++)
        {
            int finali=i;
            thread =new Thread(){
                @Override
                public void run() {
                    int threadNo=finali;
                    KafkaConsumer<String,Object> _consumer=new KafkaConsumer<String, Object>(getProps());
                    consumers.add(_consumer);
                    _consumer.subscribe(topicConsumer.keySet());

                    while (flag) {
                        logger.info("*******************");
                        ConsumerRecords<String,Object> records=_consumer.poll(1000);
                        logger.info("************************");
                        for(ConsumerRecord<String,Object> record:records)
                        {
                            AppSubscriber appSubscriber=topicConsumer.get(record.topic());
                            logger.info("offset : {}, key : {}, value : {}, consumer : {}, topic : {}, partition : {}, thread : {}",
                                    record.offset(), record.key(), record.value(), _consumer.getClass().getSimpleName(),
                                    record.topic(), record.partition(), threadNo);
                            appSubscriber.consume(AppUtil.getFromJson(record.value().toString(), appSubscriber.getClazz()));
                            logger.info("************************");
                            logger.info(AppUtil.getFromJson(record.value().toString(),appSubscriber.getClazz()).toString());
                        }
                    }
                    _consumer.unsubscribe();
                }
            };
            thread.start();
        }

    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        logger.info("system going for sleep for next 5 seconds");
        flag = false;
        Thread.sleep(5000);
    }



}
