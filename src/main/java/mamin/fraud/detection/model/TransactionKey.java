package mamin.fraud.detection.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionKey {
    private String accountNumber;
    private String bankId;
}
