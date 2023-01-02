package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoKeys {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoKeys.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am a Kafka Producer");

        // Create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 10; i++) {
            String topic = "demo_java";
            String value = "Hello world " + i;
            String key = "id_" + i;

            // Create a producer record
            ProducerRecord<String, String> producerRecord;
            producerRecord = new ProducerRecord<>(topic, key, value);

            // Send data - asynchronous
            producer.send(producerRecord, (metadata, e) -> {
                // Executes every time a record is successfully sent ot an exception is thrown
                if (e == null) {
                    // the record was successfully sent
                    log.info("Received new metadata/ \n" +
                            "Topic: " + metadata.topic() + "\n" +
                            "Key: " + producerRecord.key() + "\n" +
                            "Partition: " + metadata.partition() + "\n" +
                            "Offset: " + metadata.offset() + "\n" +
                            "Timestamp:" + metadata.timestamp() + "\n");
                } else {
                    log.error("Error while producing", e);
                }
            });
        }

        // flush data - synchronous
        producer.flush();

        // flush and close producer
        producer.close();
    }
}
