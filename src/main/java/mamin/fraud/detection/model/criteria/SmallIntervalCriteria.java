package mamin.fraud.detection.model.criteria;

import lombok.extern.slf4j.Slf4j;
import mamin.fraud.detection.constants.MonitorAction;
import mamin.fraud.detection.constants.MonitoringCriteriaType;
import mamin.fraud.detection.model.MonitoringCriteriaItem;
import mamin.fraud.detection.model.Transaction;
import mamin.fraud.detection.model.criteria_value.SmallIntervalCriteriaValue;

@Slf4j
public class SmallIntervalCriteria extends MonitoringCriteriaItem <SmallIntervalCriteriaValue> {

    @Override
    public boolean violatesRules(Transaction transaction) {
        // should be overwritten in aggregation criteria
        return false;
    }

    @Override
    public SmallIntervalCriteriaValue criteriaValue() {
        return new SmallIntervalCriteriaValue();
    }

    @Override
    public MonitoringCriteriaType getMonitoringCriteriaType() {
        return MonitoringCriteriaType.SMALL_INTERVAL;
    }

    @Override
    protected MonitorAction getMonitoringAction() {
        return MonitorAction.REVIEW;
    }
}
