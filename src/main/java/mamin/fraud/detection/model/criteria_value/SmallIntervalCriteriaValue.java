package mamin.fraud.detection.model.criteria_value;

import mamin.fraud.detection.constants.MonitoringCriteriaType;
import mamin.fraud.detection.model.MonitoringCriteriaValue;

public class SmallIntervalCriteriaValue extends MonitoringCriteriaValue {
    public SmallIntervalCriteriaValue() {
        super(10, MonitoringCriteriaType.SMALL_INTERVAL); // 10 seconds. used to evaluate if SmallIntervalCheck is hit or not
    }

}
