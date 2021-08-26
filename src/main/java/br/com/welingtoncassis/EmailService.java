package br.com.welingtoncassis;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EmailService {
    public static void main(String[] args) {
        try (var service = new KafkaService(
                "ECOMMERCE_SEND_EMAIL",
                EmailService::parse
        )) {
            service.run();
        }
    }

    public static void parse(ConsumerRecord<String, String> record) {
        System.out.println("--------");
        System.out.println("Enviando email ...");
        System.out.println("chave: " + record.key() + " valor: " + record.value());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Email enviado");
    }

}
