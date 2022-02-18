package com.kyc.core.exception;



import com.kyc.core.model.web.MessageData;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.kyc.core.constants.GeneralConstants.EXC_ERROR_DATA_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_HTTP_STATUS_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_INPUT_DATA_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_ORIGINAL_EXCEPTION_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_OUTPUT_DATA_LABEL;

@Builder
@Getter
public class KycRestException extends RuntimeException{

    private final Object inputData;
    private final Object outputData;
    private final MessageData errorData;
    private final Throwable exception;
    private final HttpStatus status;

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();

        addDescription(sb,EXC_INPUT_DATA_LABEL,getInputData(),false);
        addDescription(sb, EXC_OUTPUT_DATA_LABEL,getOutputData(),true);
        addDescription(sb, EXC_ERROR_DATA_LABEL,getErrorData(),true);
        addDescription(sb, EXC_ORIGINAL_EXCEPTION_LABEL,getException(),true);
        addDescription(sb, EXC_HTTP_STATUS_LABEL,getStatus(),true);

        return sb.toString();
    }

    private void addDescription(StringBuilder sb, String label, Object data, boolean newLine){

        if(data!= null){

            if(newLine){
                sb.append("\n");
            }
            sb.append(label);
            sb.append(data);

        }
    }
}
