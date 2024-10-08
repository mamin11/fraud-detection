package mamin.fraud.detection.model;

import lombok.Data;
import mamin.fraud.detection.constants.HitStatus;
import mamin.fraud.detection.constants.MonitorAction;
import mamin.fraud.detection.constants.MonitoringCriteriaType;

import java.util.List;

@Data
public class Transaction {
    private String transactionId;
    private String accountNumber;
    private String bankId;
    private String transactionTimestamp;
    private String depositAmount;
    private String withdrawalAmount;
    private String balanceAmount;
    private String chqNo;
    private String transactionDetails;
    private String valueDate;
    private String transactionSector;
    private List<HitStatus> hitStatus;
    private MonitorAction monitorAction;
    private List<MonitoringCriteriaType> monitoringCriteriaType;
}
