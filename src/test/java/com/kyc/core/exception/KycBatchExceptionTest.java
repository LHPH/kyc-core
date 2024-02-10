package com.kyc.core.exception;

import com.kyc.core.model.MessageData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;

@ExtendWith(MockitoExtension.class)
public class KycBatchExceptionTest {

    @Test
    public void builderBatchException_buildException_builtException(){


        KycBatchException ex = KycBatchException.builderBatchException()
                .batchStepName("step")
                .errorData(new MessageData())
                .exception(new NullPointerException())
                .exitStatus(ExitStatus.FAILED)
                .inputData("TEST")
                .outputData("test")
                .build();

        Assertions.assertNotNull(ex);
        Assertions.assertEquals(ExitStatus.FAILED,ex.getExitStatus());
        Assertions.assertEquals("step",ex.getBatchStepName());
    }

    @Test
    public void builderBatchException_buildExceptionWithNoData_builtException(){


        KycBatchException ex = KycBatchException.builderBatchException()
                .build();

        Assertions.assertNotNull(ex);
    }

    @Test
    public void toString_printMessage_returnStringErrorMessage(){


        KycBatchException ex = KycBatchException.builderBatchException()
                .batchStepName("step")
                .errorData(new MessageData())
                .exception(new NullPointerException())
                .exitStatus(ExitStatus.FAILED)
                .inputData("TEST")
                .outputData("test")
                .build();

        Assertions.assertNotNull(ex.toString());
    }
}
