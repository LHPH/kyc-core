package com.kyc.core.exception.handlers;

import com.kyc.core.exception.KycBatchException;
import com.kyc.core.model.web.MessageData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.repeat.context.RepeatContextSupport;

@ExtendWith(MockitoExtension.class)
public class KycBatchExceptionHandlerTest {

    private KycBatchExceptionHandler handler = new KycBatchExceptionHandler(new MessageData());

    @Test
    public void handleException_handleFlatFileParseException_throwKycBatchException(){

        Assertions.assertThrows(KycBatchException.class,()->{
            FlatFileParseException ex = new FlatFileParseException("Bad file","text");
            handler.handleException(new RepeatContextSupport(null),ex);
        });
    }

    @Test
    public void handleException_handleUnknownException_throwKycBatchException(){

        Assertions.assertThrows(KycBatchException.class,()->{
            NullPointerException ex = new NullPointerException("null test");
            handler.handleException(new RepeatContextSupport(null),ex);
        });
    }
}
