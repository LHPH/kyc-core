package com.kyc.core.batch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

import java.time.LocalDateTime;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class BatchJobExecutionListenerTest {

    private BatchJobExecutionListener listener  = new BatchJobExecutionListener("TEST");

    private JobExecution jobExecution;

    @BeforeEach
    public void setUp(){

        jobExecution = new JobExecution(new JobInstance(1L,"TEST"),null);
        jobExecution.setExitStatus(ExitStatus.COMPLETED);
        jobExecution.setCreateTime(LocalDateTime.now());
        jobExecution.setEndTime(LocalDateTime.now());
    }

    @Test
    public void beforeJob_processData_notThrow(){

        Assertions.assertDoesNotThrow(()->{listener.beforeJob(jobExecution);});
    }

    @Test
    public void afterJob_processData_notThrow(){

        Assertions.assertDoesNotThrow(()->{listener.afterJob(jobExecution);});
    }
}
