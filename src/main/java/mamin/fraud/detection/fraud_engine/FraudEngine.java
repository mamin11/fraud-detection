package mamin.fraud.detection.fraud_engine;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mamin.fraud.detection.constants.HitStatus;
import mamin.fraud.detection.data_producer.KafkaProducer;
import mamin.fraud.detection.model.Transaction;
import mamin.fraud.detection.model.TransactionKey;
import mamin.fraud.detection.serde.CustomSerdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FraudEngine {
    private final KafkaProducer kafkaProducer;
    @Bean
    public KStream<TransactionKey, Transaction> fraudEngineBuilder() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (Exception e) {
            log.error("Error loading properties file: {}", e.getMessage());
        }

        String inputTopic = props.getProperty("input.topic");
        String outputTopic = props.getProperty("output.topic");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "fraud-detection-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, CustomSerdes.TransactionKey().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CustomSerdes.Transaction().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<TransactionKey, Transaction> firstStream = builder
                .stream(inputTopic, Consumed.with(CustomSerdes.TransactionKey(), CustomSerdes.Transaction()));
        firstStream
                .peek((key, value) -> log.info("Incoming record - key: {} value: {} ", key, value))
                .transformValues(fraudDetectionTransformer())
                .filter((key, value) -> value.getHitStatus() != null && value.getHitStatus().contains(HitStatus.HIT))
                .peek((key, value) -> log.info("Outgoing record - key: {} value: {} ", key, value))
                .to(outputTopic, Produced.with(CustomSerdes.TransactionKey(), CustomSerdes.Transaction()));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), props);
        kafkaStreams.start();

        return firstStream;
    }

    @Bean
    public FraudDetectionTransformer fraudDetectionTransformer() {
        return new FraudDetectionTransformer();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void readDataFromCSV() {
        log.info("Reading data from CSV file after context is loaded");
        Map<String, String> mapping = new HashMap<>();
        mapping.put("transaction_id", "transactionId");
        mapping.put("account_no", "accountNumber");
        mapping.put("bank_id", "bankId");
        mapping.put("transaction_timestamp", "transactionTimestamp");
        mapping.put("deposit_amt", "depositAmount");
        mapping.put("withdrawal_amt", "withdrawalAmount");
        mapping.put("balance_amt", "balanceAmount");
        mapping.put("chq_no", "chqNo");
        mapping.put("transaction_details", "transactionDetails");
        mapping.put("value_date", "valueDate");
        mapping.put("transaction_sector", "transactionSector");

        HeaderColumnNameTranslateMappingStrategy<Transaction> strategy =
                new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(Transaction.class);
        strategy.setColumnMapping(mapping);

        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader("data/sample.csv"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CsvToBean csvToBean = new CsvToBean();
        Set<Transaction> list = new HashSet<>(csvToBean.parse(strategy, csvReader));

        // print details of Bean object
        for (Transaction e : list) {
            kafkaProducer.sendMessageToFraud(new TransactionKey(e.getAccountNumber(),e.getBankId()), e);
        }
    }

}
