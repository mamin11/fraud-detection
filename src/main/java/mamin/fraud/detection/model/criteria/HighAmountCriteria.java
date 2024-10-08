package mamin.fraud.detection.model.criteria;

import lombok.extern.slf4j.Slf4j;
import mamin.fraud.detection.constants.MonitorAction;
import mamin.fraud.detection.constants.MonitoringCriteriaType;
import mamin.fraud.detection.model.MonitoringCriteriaItem;
import mamin.fraud.detection.model.Transaction;
import mamin.fraud.detection.model.criteria_value.HighTransactionAmountCriteriaValue;

import java.math.BigDecimal;


@Slf4j
public class HighAmountCriteria extends MonitoringCriteriaItem<HighTransactionAmountCriteriaValue> {

    @Override
    public boolean violatesRules(Transaction transaction) {
        BigDecimal withdrawalAmount = BigDecimal.valueOf(Float.parseFloat(transaction.getWithdrawalAmount()));
        BigDecimal depositAmount = BigDecimal.valueOf(Float.parseFloat(transaction.getDepositAmount()));
        BigDecimal threshold = (BigDecimal) this.criteriaValue().getValue();
        return withdrawalAmount.compareTo(threshold) >= 0 || depositAmount.compareTo(threshold) > 0;
    }

    @Override
    public HighTransactionAmountCriteriaValue criteriaValue() {
        return new HighTransactionAmountCriteriaValue();
    }

    @Override
    public MonitoringCriteriaType getMonitoringCriteriaType() {
        return MonitoringCriteriaType.HIGH_TRANSACTION_AMOUNT;
    }

    @Override
    protected MonitorAction getMonitoringAction() {
        return MonitorAction.REVIEW;
    }
}
