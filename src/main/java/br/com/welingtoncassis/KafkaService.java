package br.com.welingtoncassis;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

class KafkaService implements Closeable {
    private final KafkaConsumer<String, String> consumer;
    private final String topic;
    private final ConsumerFunction parse;

    public KafkaService(String groupId, String topic, ConsumerFunction parse) {
        this.topic = topic;
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(groupId));
        // escutar topico
        this.consumer.subscribe(Collections.singletonList(this.topic));
    }

    void run() {
        while (true) {
            // perguntar se tem mensagem por um tempo
            var records = this.consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()){
                System.out.println("Encontrei " + records.count() + " mensagens neste topico");
                for(var record : records) {
                    this.parse.consume(record);
                }
            }
        }
    }

    public static Properties properties(String groupId) {
        var properties = new Properties();
        // onde ele vai escutar
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        // deserializar de bytes para string
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // um é como se fosse uma fila para cada topico podendo ter varias filas num topico
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        return properties;
    }

    @Override
    public void close() {
        this.consumer.close();
    }
}
