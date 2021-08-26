package br.com.welingtoncassis;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaService {
    private final KafkaConsumer<String, String> consumer;
    private final String topic;
    private final ConsumerFunctions parse;

    public KafkaService(String topic, ConsumerFunctions parse) {
        this.topic = topic;
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties());
        // escutar topico
        consumer.subscribe(Collections.singletonList(topic));
    }

    void run() {
        while (true) {
            // perguntar se tem mensagem por um tempo
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()){
                System.out.println("Encontrei " + records.count() + " mensagens neste topico");
                for(var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    public static Properties properties() {
        var properties = new Properties();
        // onde ele vai escutar
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        // deserializar de bytes para string
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // um é como se fosse uma fila para cada topico podendo ter varias filas num topico
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());
        return properties;
    }
}
