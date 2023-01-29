package com.kyc.core.batch;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class BatchJobExecutionListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobExecutionListener.class);

    private String jobName;

    @Override
    public void afterJob(JobExecution jobExecution) {

        jobName = ObjectUtils.defaultIfNull(jobName,jobExecution.getJobConfigurationName());
        BatchStatus batchStatus = jobExecution.getStatus();
        Date startDate = jobExecution.getCreateTime();
        Date finishDate = jobExecution.getEndTime();

        LOGGER.info("[{}] Start: {}, Finish: {}, Status: {}",jobName,startDate,finishDate,batchStatus);
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

        jobName = ObjectUtils.defaultIfNull(jobName,jobExecution.getJobConfigurationName());
        LOGGER.info("[{}] Starting Job with id {}",jobName,jobExecution.getJobId());

    }
}
