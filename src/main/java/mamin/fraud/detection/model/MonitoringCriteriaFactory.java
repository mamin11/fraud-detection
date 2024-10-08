package mamin.fraud.detection.model;

import mamin.fraud.detection.constants.MonitoringCriteriaType;
import mamin.fraud.detection.model.criteria.HighAmountCriteria;
import mamin.fraud.detection.model.criteria.SmallIntervalCriteria;
import mamin.fraud.detection.model.criteria.SpecificSectorCriteria;

public class MonitoringCriteriaFactory {
    private final MonitoringCriteriaType type;

    public MonitoringCriteriaFactory(MonitoringCriteriaType type) {
        this.type = type;
    }

    public MonitoringCriteriaItem<?> getMonitoringCriteriaItem() {
        return switch (this.type) {
            case HIGH_TRANSACTION_AMOUNT -> new HighAmountCriteria();
            case SMALL_INTERVAL -> new SmallIntervalCriteria();
            case SPECIFIC_SECTOR -> new SpecificSectorCriteria();
        };
    }
}
