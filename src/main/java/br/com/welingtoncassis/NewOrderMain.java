package br.com.welingtoncassis;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var dispatcher = new KafkaDispatcher())  {
            var key = UUID.randomUUID().toString();
            var value = key + "332123,67523,7894589745";
            dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

            var email = "Welcome! we are processing your order!";
            dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
        }
    }
}
