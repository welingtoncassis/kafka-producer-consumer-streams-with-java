package br.com.welingtoncassis;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunctions {
    void consume(ConsumerRecord<String, String> record);
}
