package com.kyc.core.exception;

import com.kyc.core.model.MessageData;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import static com.kyc.core.constants.GeneralConstants.EXC_ERROR_DATA_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_INPUT_DATA_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_ORIGINAL_EXCEPTION_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_OUTPUT_DATA_LABEL;

@Getter
public class KycException extends RuntimeException{

    private final Object inputData;
    private final Object outputData;
    private final MessageData errorData;
    private final Throwable exception;

    @Builder
    protected KycException(Object inputData, Object outputData, MessageData errorData, Throwable exception) {
        this.inputData = inputData;
        this.outputData = outputData;
        this.errorData = errorData;
        this.exception = exception;
    }

    @Override
    public String toString(){
        return printError();
    }

    @Override
    public String getMessage() {
        return printError();
    }

    public String printError(){

        StringBuilder sb = new StringBuilder();

        addDescription(sb,EXC_INPUT_DATA_LABEL,getInputData(),false);
        addDescription(sb, EXC_OUTPUT_DATA_LABEL,getOutputData(),true);
        addDescription(sb, EXC_ERROR_DATA_LABEL,getErrorData(),true);
        addDescription(sb, EXC_ORIGINAL_EXCEPTION_LABEL, getException(),true);

        return sb.toString();
    }

     protected void addDescription(StringBuilder sb, String label, Object data, boolean newLine){

        if(data!= null){

            if(newLine){
                sb.append("\n");
            }
            sb.append(label);
            if(data instanceof Exception){
                sb.append(ExceptionUtils.getStackTrace((Exception)data));
            }
            else{
                sb.append(data);
            }
        }
    }
}
