package com.kyc.core.batch;

import com.kyc.core.model.MessageData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.JobInstance;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class BatchStepListenerTest {

    BatchStepListener<String,String> listener = new BatchStepListener<>("TEST", new MessageData(),true);

    private StepExecution stepExecution;

    @BeforeEach
    public void setUp(){

        JobExecution jobExecution = new JobExecution(1,new JobInstance(1L,"TEST"),null);
        jobExecution.setExitStatus(ExitStatus.COMPLETED);
        jobExecution.setCreateTime(LocalDateTime.now());
        jobExecution.setEndTime(LocalDateTime.now());

        stepExecution = new StepExecution(1,"STEP",jobExecution);
    }

    @Test
    public void beforeStep_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.beforeStep(stepExecution));
    }

    @Test
    public void afterStep_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.afterStep(stepExecution));
    }

    @Test
    public void beforeRead_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.beforeRead());
    }

    @Test
    public void afterRead_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.afterRead("test"));
    }

    @Test
    public void beforeWrite_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.beforeWrite(new Chunk<>(new ArrayList<>())));
    }

    @Test
    public void afterWrite_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.afterWrite(new Chunk<>(new ArrayList<>())));
    }

    @Test
    public void beforeProcess_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.beforeProcess("TEST"));
    }

    @Test
    public void afterProcess_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.afterProcess("TEST","TEST"));
    }

    @Test
    public void onWriteError_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.onWriteError(new NullPointerException(),new Chunk<>(new ArrayList<>())));
    }

    @Test
    public void onProcessError_processData_notThrowError(){

        Assertions.assertDoesNotThrow(()->listener.onProcessError("TEST",new NullPointerException()));
    }

}
