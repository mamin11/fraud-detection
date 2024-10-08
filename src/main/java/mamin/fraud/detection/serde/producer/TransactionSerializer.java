package mamin.fraud.detection.serde.producer;

import com.google.gson.Gson;
import mamin.fraud.detection.model.Transaction;
import mamin.fraud.detection.model.TransactionKey;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class TransactionSerializer implements Serializer<Transaction> {
    @Override
    public byte[] serialize(String s, Transaction request) {
        return new Gson().toJson(request).getBytes(StandardCharsets.UTF_8);
    }
}
