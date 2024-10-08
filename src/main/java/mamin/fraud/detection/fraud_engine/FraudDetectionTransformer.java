package mamin.fraud.detection.fraud_engine;

import lombok.extern.slf4j.Slf4j;
import mamin.fraud.detection.constants.HitStatus;
import mamin.fraud.detection.constants.MonitorAction;
import mamin.fraud.detection.constants.MonitoringCriteriaType;
import mamin.fraud.detection.model.MonitoringCriteriaFactory;
import mamin.fraud.detection.model.MonitoringCriteriaItem;
import mamin.fraud.detection.model.Transaction;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.List;

@Slf4j
public class FraudDetectionTransformer implements ValueTransformerSupplier<Transaction, Transaction> {

    @Override
    public ValueTransformer<Transaction, Transaction> get() {
        return new FraudTransactionProcessor();
    }

    private static class FraudTransactionProcessor implements ValueTransformer<Transaction, Transaction> {
        @Override
        public void init(ProcessorContext processorContext) {
            // nothing to do
        }

        @Override
        public Transaction transform(Transaction value) {
            log.info("Incoming record for fraud detection - value: {}", value);

            // list of criteria to check
            List<MonitoringCriteriaType> criteriaTypeList = List.of(
                    MonitoringCriteriaType.HIGH_TRANSACTION_AMOUNT,
                    MonitoringCriteriaType.SPECIFIC_SECTOR);

            for (MonitoringCriteriaType criteriaType : criteriaTypeList) {
                MonitoringCriteriaFactory monitoringCriteriaFactory = new MonitoringCriteriaFactory(criteriaType);
                MonitoringCriteriaItem<?> monitoringCriteriaItem = monitoringCriteriaFactory.getMonitoringCriteriaItem();
                monitoringCriteriaItem.isHit(value);
            }

            log.info("Outgoing record after fraud check - value: {}", value);

            return value;
        }

        @Override
        public void close() {
            // nothing to do
        }
    }
}
