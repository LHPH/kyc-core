package com.kyc.core.batch;

import com.kyc.core.exception.KycBatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.validator.Validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BatchValidatingItemProcessorTest {

    private BatchValidatingItemProcessor<Object> validatingItemProcessor = new BatchValidatingItemProcessor<>();

    @Mock
    private Validator<Object> validator;

    @BeforeAll
    public static void init(){

        MockitoAnnotations.openMocks(BatchValidatingItemProcessorTest.class);
    }

    @Test
    public void process_processing_returnItem() {

        validatingItemProcessor.setValidator(validator);
        validatingItemProcessor.setFilter(true);
        validatingItemProcessor.afterPropertiesSet();

        doNothing().when(validator).validate(any());

        Object result = validatingItemProcessor.process(new Object());
        Assertions.assertNotNull(result);
    }

    @Test
    public void process_processingBadItemAndIgnore_returnNull(){

        validatingItemProcessor.setValidator(validator);
        validatingItemProcessor.setFilter(true);
        validatingItemProcessor.afterPropertiesSet();

        doThrow(KycBatchException.builderBatchException().build()).when(validator).validate(any());

        Object result = validatingItemProcessor.process(new Object());
        Assertions.assertNull(result);
    }

    @Test
    public void process_processingBadItemAndNotIgnore_throwException(){

        Assertions.assertThrows(KycBatchException.class,()->{

            validatingItemProcessor.setValidator(validator);
            validatingItemProcessor.setFilter(false);
            validatingItemProcessor.afterPropertiesSet();

            doThrow(KycBatchException.builderBatchException().build()).when(validator).validate(any());

            validatingItemProcessor.process(new Object());
        });
    }
}
