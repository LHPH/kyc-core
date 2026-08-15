package com.kyc.core.batch;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class BatchJobExecutionListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobExecutionListener.class);

    private String jobName;

    @Override
    public void afterJob(JobExecution jobExecution) {

        jobName = ObjectUtils.getIfNull(jobName,jobExecution.getJobInstance().getJobName());
        BatchStatus batchStatus = jobExecution.getStatus();
        LocalDateTime startDate = jobExecution.getCreateTime();
        LocalDateTime finishDate = jobExecution.getEndTime();

        LOGGER.info("[{}] Status: [{}], Start: {}, Finish: {}",jobName,batchStatus,startDate,finishDate);
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

        jobName = ObjectUtils.getIfNull(jobName,jobExecution.getJobInstance().getJobName());
        LOGGER.info("[{}] Starting Job with id {}",jobName,jobExecution.getJobInstanceId());

    }
}
