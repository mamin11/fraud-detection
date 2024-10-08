package mamin.fraud.detection.model;

import mamin.fraud.detection.constants.MonitoringCriteriaType;

public abstract class MonitoringCriteriaValue {
    private final Object value;
    private final MonitoringCriteriaType type;

    public MonitoringCriteriaValue(Object value, MonitoringCriteriaType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return this.value;
    }

    public MonitoringCriteriaType getType() {
        return this.type;
    }
}
