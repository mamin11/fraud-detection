package mamin.fraud.detection.serde;

import mamin.fraud.detection.model.Transaction;
import mamin.fraud.detection.model.TransactionKey;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class CustomSerdes {
    private CustomSerdes() {}
    public static Serde<TransactionKey> TransactionKey() {
        JsonSerializer<TransactionKey> serializer = new JsonSerializer<>();
        JsonDeserializer<TransactionKey> deserializer = new JsonDeserializer<>(TransactionKey.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<Transaction> Transaction() {
        JsonSerializer<Transaction> serializer = new JsonSerializer<>();
        JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
