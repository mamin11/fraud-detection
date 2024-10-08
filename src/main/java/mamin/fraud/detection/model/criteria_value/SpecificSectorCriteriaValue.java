package mamin.fraud.detection.model.criteria_value;

import mamin.fraud.detection.constants.MonitoringCriteriaType;
import mamin.fraud.detection.model.MonitoringCriteriaValue;

public class SpecificSectorCriteriaValue extends MonitoringCriteriaValue {
    public SpecificSectorCriteriaValue() {
        // can replace with any sector we want to monitor
        super("Other", MonitoringCriteriaType.SPECIFIC_SECTOR); // used to evaluate if HighAmountCheck is hit or not
    }

}
