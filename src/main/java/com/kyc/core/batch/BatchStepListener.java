package com.kyc.core.batch;

import com.kyc.core.exception.KycBatchException;
import com.kyc.core.model.MessageData;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.file.FlatFileParseException;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class BatchStepListener<I,O> extends StepListenerSupport<I,O> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchStepListener.class);

    private String stepName;
    private MessageData messageData;
    private boolean silentException;

    public BatchStepListener(String stepName, MessageData messageData) {
        this(stepName,messageData,false);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        stepName = ObjectUtils.getIfNull(stepName,stepExecution.getStepName());
        LocalDateTime startDate = stepExecution.getStartTime();
        LocalDateTime finishDate = stepExecution.getEndTime();
        ExitStatus exitStatus = stepExecution.getExitStatus();
        long readCount = stepExecution.getReadCount();
        long rollbackCount = stepExecution.getRollbackCount();
        long skipCount = stepExecution.getSkipCount();
        long writeCount = stepExecution.getWriteCount();

        LOGGER.info("[{}] Status: [{}], Starting: {}, Finish: {}, Read Count: {}," +
                        " Write Count: {}, Skip Count: {}, Rollback Count: {}",
                stepName,exitStatus,startDate,finishDate,readCount,writeCount,skipCount,rollbackCount);
        return stepExecution.getExitStatus();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        stepName = ObjectUtils.getIfNull(stepName,stepExecution.getStepName());
        LOGGER.info("[{}] Starting step", stepName);
    }

    @Override
    public void afterRead(I item) {

        LOGGER.info("[{}] It was read item: {}", stepName,item);
    }

    @Override
    public void beforeRead() {

        LOGGER.info("[{}] Beginning to read records",stepName);
    }

    @Override
    public void afterWrite(Chunk<? extends O> chunk) {

        LOGGER.info("[{}] It was written {} records", stepName,chunk.getItems().size());
    }

    @Override
    public void beforeWrite(Chunk<? extends O> chunk) {

        LOGGER.info("[{}] Beginning to write {} records",stepName,chunk.getItems().size());
        super.beforeWrite(chunk);
    }

    @Override
    public void onWriteError(Exception ex, Chunk<? extends O> chunk) {

        LOGGER.error("[{}] An error has occurred writing the elements", stepName,ex);

        if(!silentException){
            handleException(ex);
        }
    }

    @Override
    public void afterProcess(I item, O result) {

        LOGGER.info("[{}] The item {} was processed",stepName,item);
        super.afterProcess(item,result);
    }

    @Override
    public void beforeProcess(I item) {

        LOGGER.info("[{}] Beginning to process record {}",stepName,item);
        super.beforeProcess(item);
    }

    @Override
    public void onProcessError(I item, Exception ex) {
        LOGGER.error("[{}] An error has occurred processing the element {}", stepName,item,ex);

        if(!silentException){
            handleException(ex);
        }
    }

    @Override
    public void onReadError(Exception ex) {
        LOGGER.error("[{}] An error has occurred in reading {}", stepName,ex.getMessage());

        if(!silentException){
            handleException(ex);
        }
    }

    protected void handleException(Exception ex){

        Object inputData = null;
        Exception exc;
        if (ex instanceof FlatFileParseException exception) {
            inputData = exception.getInput();
            exc = exception;
        }
        else if(ex instanceof KycBatchException){
            throw (KycBatchException)ex;
        }
        else{
            exc = ex;
        }
        throw KycBatchException.builderBatchException()
                .inputData(inputData)
                .exception(exc)
                .exitStatus(ExitStatus.FAILED)
                .errorData(messageData)
                .build();
    }
}
