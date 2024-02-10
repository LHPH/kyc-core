package com.kyc.core.exception;

import com.kyc.core.model.MessageData;
import lombok.Builder;
import lombok.Getter;
import org.springframework.batch.core.ExitStatus;

@Getter
public class KycBatchException extends KycException{

    private String batchStepName;
    private ExitStatus exitStatus;

    @Builder(builderMethodName = "builderBatchException")
    public KycBatchException(Object inputData, Object outputData, MessageData errorData, Throwable exception,
                             String batchStepName, ExitStatus exitStatus) {
        super(inputData, outputData, errorData, exception);
        this.batchStepName = batchStepName;
        this.exitStatus = exitStatus;
    }

     @Override
    public String printError(){

        StringBuilder sb = new StringBuilder(super.printError());

        addDescription(sb, "Batch Step Name: ",getBatchStepName(),true);
        addDescription(sb, "Exit Status: ",getExitStatus(),true);

        return sb.toString();
    }
}
