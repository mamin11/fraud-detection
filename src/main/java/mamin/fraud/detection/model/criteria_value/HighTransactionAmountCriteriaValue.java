package mamin.fraud.detection.model.criteria_value;

import mamin.fraud.detection.constants.MonitoringCriteriaType;
import mamin.fraud.detection.model.MonitoringCriteriaValue;

import java.math.BigDecimal;

public class HighTransactionAmountCriteriaValue extends MonitoringCriteriaValue {
    public HighTransactionAmountCriteriaValue() {
        super(BigDecimal.valueOf(10000), MonitoringCriteriaType.HIGH_TRANSACTION_AMOUNT); // used to evaluate if HighAmountCheck is hit or not
    }

}
