package mamin.fraud.detection.data_producer;

import mamin.fraud.detection.model.Transaction;
import mamin.fraud.detection.model.TransactionKey;
import mamin.fraud.detection.serde.CustomSerdes;
import mamin.fraud.detection.serde.producer.TransactionKeySerializer;
import mamin.fraud.detection.serde.producer.TransactionSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Producer Configuration - this is used to configure beans related to kafka producer
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${broker.url}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> transProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, TransactionKeySerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TransactionSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<TransactionKey, Transaction> transactionProducerFactory() {
        return new DefaultKafkaProducerFactory<>(transProducerConfigs());
    }

    @Bean
    public KafkaTemplate<TransactionKey, Transaction> transProducerTemplate() {
        return new KafkaTemplate<>(transactionProducerFactory());
    }

}
