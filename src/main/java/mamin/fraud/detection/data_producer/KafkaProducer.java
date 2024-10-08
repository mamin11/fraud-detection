package mamin.fraud.detection.data_producer;

import lombok.extern.slf4j.Slf4j;
import mamin.fraud.detection.model.Transaction;
import mamin.fraud.detection.model.TransactionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * This service class is sending messages to kafka topic
 */
@Slf4j
@Service
public class KafkaProducer {

    @Value("${input.topic}")
    private String inputTopic;
    @Autowired private KafkaTemplate<TransactionKey, Transaction> transactionTemplate;


    public void sendMessageToFraud(TransactionKey key, Transaction value) {
        log.info(" Sending transaction {} to Kafka topic {} ", value, inputTopic);
        this.transactionTemplate.send(inputTopic, key, value);
    }
}

