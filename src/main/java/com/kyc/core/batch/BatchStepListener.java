package com.kyc.core.batch;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class BatchStepListener<I,O> extends StepListenerSupport<I,O> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchStepListener.class);

    private String stepName;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        stepName = ObjectUtils.defaultIfNull(stepName,stepExecution.getStepName());
        Date startDate = stepExecution.getStartTime();
        Date finishDate = stepExecution.getEndTime();
        ExitStatus exitStatus = stepExecution.getExitStatus();
        int readCount = stepExecution.getReadCount();
        int rollbackCount = stepExecution.getRollbackCount();
        int skipCount = stepExecution.getSkipCount();
        int writeCount = stepExecution.getWriteCount();

        LOGGER.info("[{}] Status: [{}], Starting: {}, Finish: {}, Read Count: {}," +
                        " Write Count: {}, Skip Count: {}, Rollback Count: {}",
                stepName,exitStatus,startDate,finishDate,readCount,writeCount,skipCount,rollbackCount);
        return stepExecution.getExitStatus();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        stepName = ObjectUtils.defaultIfNull(stepName,stepExecution.getStepName());
        LOGGER.info("[{}] Starting step", stepName);
    }

    @Override
    public void afterRead(I item) {

        LOGGER.info("[{}] It was read item: {}", stepName,item);
    }

    @Override
    public void beforeRead() {

        LOGGER.info("[{}] Begining to read records",stepName);
    }

    @Override
    public void onReadError(Exception ex) {
        super.onReadError(ex);
    }

    @Override
    public void afterWrite(List<? extends O> items) {

        LOGGER.info("[{}] It was writen {} records", stepName,items.size());
    }

    @Override
    public void beforeWrite(List<? extends O> items) {

        LOGGER.info("[{}] Begining to write {} records",stepName,items.size());
        super.beforeWrite(items);
    }

    @Override
    public void onWriteError(Exception exception, List<? extends O> items) {

        LOGGER.error("[{}] An error has ocurrend writing the elements", stepName,exception);
    }

    @Override
    public void afterProcess(I item, O result) {

    }

    @Override
    public void beforeProcess(I item) {

        LOGGER.info("[{}] Begining to process record {}",stepName,item);
        super.beforeProcess(item);
    }

    @Override
    public void onProcessError(I item, Exception e) {
        LOGGER.error("[{}] An error has ocurrend processing the element {}", stepName,item,e);
    }
}
