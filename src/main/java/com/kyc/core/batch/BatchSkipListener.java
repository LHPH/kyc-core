package com.kyc.core.batch;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

@AllArgsConstructor
public class BatchSkipListener<I,O> implements SkipListener<I,O> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchSkipListener.class);

    private String stepName;

    @Override
    public void onSkipInProcess(I item, Throwable t) {
        LOGGER.warn("[{}] The item could {} not be processed, skipped",stepName,item,t);
    }

    @Override
    public void onSkipInRead(Throwable t) {
        LOGGER.warn("An item could not be read, skipped",t);
    }

    @Override
    public void onSkipInWrite(O item, Throwable t) {
        LOGGER.warn("[{}] The item {} could not be written, skipped",stepName,item,t);
    }
}
