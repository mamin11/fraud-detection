package mamin.fraud.detection.serde.producer;

import com.google.gson.Gson;
import mamin.fraud.detection.model.TransactionKey;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class TransactionKeySerializer  implements Serializer<TransactionKey> {
    @Override
    public byte[] serialize(String s, TransactionKey request) {
        return new Gson().toJson(request).getBytes(StandardCharsets.UTF_8);
    }
}
