package mamin.fraud.detection.model.criteria;

import lombok.extern.slf4j.Slf4j;
import mamin.fraud.detection.constants.MonitorAction;
import mamin.fraud.detection.constants.MonitoringCriteriaType;
import mamin.fraud.detection.model.MonitoringCriteriaItem;
import mamin.fraud.detection.model.Transaction;
import mamin.fraud.detection.model.criteria_value.SpecificSectorCriteriaValue;

@Slf4j
public class SpecificSectorCriteria extends MonitoringCriteriaItem<SpecificSectorCriteriaValue> {
    @Override
    public boolean violatesRules(Transaction transaction) {
        return transaction.getTransactionSector().equals(this.criteriaValue().getValue());
    }

    @Override
    public SpecificSectorCriteriaValue criteriaValue() {
        return new SpecificSectorCriteriaValue();
    }

    @Override
    public MonitoringCriteriaType getMonitoringCriteriaType() {
        return MonitoringCriteriaType.SPECIFIC_SECTOR;
    }

    @Override
    protected MonitorAction getMonitoringAction() {
        return MonitorAction.BLOCK;
    }
}
