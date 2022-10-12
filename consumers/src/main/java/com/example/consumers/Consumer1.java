package com.example.consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.lang.NonNull;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Consumer1 {
    private final Properties properties = new Properties();

    Consumer1(@NonNull String bootstrapServers, @NonNull String groupId, @NonNull String autoOffsetResetConfig) {
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);
        System.out.println("Consumer:   " + groupId + "   " + bootstrapServers);
    }

    public void start(List<String> topics) {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(topics);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Key: " + record.key() + ", Value:" + record.value());
                    System.out.println("Partition:" + record.partition() + ", Offset:" + record.offset());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
