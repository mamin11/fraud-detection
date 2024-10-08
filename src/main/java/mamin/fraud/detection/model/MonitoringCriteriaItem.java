package mamin.fraud.detection.model;

import lombok.extern.slf4j.Slf4j;
import mamin.fraud.detection.constants.HitStatus;
import mamin.fraud.detection.constants.MonitorAction;
import mamin.fraud.detection.constants.MonitoringCriteriaType;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public abstract class MonitoringCriteriaItem<T extends MonitoringCriteriaValue> {
    public abstract boolean violatesRules(Transaction transaction);

    public void isHit(Transaction transaction) {
        boolean hitStatus = this.violatesRules(transaction);
        HitStatus status = hitStatus ? HitStatus.HIT : HitStatus.NO_HIT;
        log.info("MonitoringCriteriaItem: {} hitStatus: {} transaction {} ",
                this.getMonitoringCriteriaType(), status, transaction);

        Set<MonitoringCriteriaType> monitoringCriteriaType = new HashSet<>(
                transaction.getMonitoringCriteriaType() == null ? Set.of() : transaction.getMonitoringCriteriaType());
        Set<HitStatus> hitStatuses = new HashSet<>(
                transaction.getHitStatus() == null ? Set.of() : transaction.getHitStatus());
        hitStatuses.add(status);
        monitoringCriteriaType.add(this.getMonitoringCriteriaType());
        transaction.setHitStatus(hitStatuses.stream().toList());
        transaction.setMonitoringCriteriaType(monitoringCriteriaType.stream().toList());
        transaction.setMonitorAction(hitStatus ? this.getMonitoringAction() : MonitorAction.ALLOW);
    }


    public abstract MonitoringCriteriaValue criteriaValue();
    public abstract MonitoringCriteriaType getMonitoringCriteriaType();
    protected abstract MonitorAction getMonitoringAction();
}
