package com.kyc.core.batch.policies.skip;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import java.io.FileNotFoundException;

@AllArgsConstructor
public class FileSkipPolicy implements SkipPolicy {

    private int skipLimit;

    @Override
    public boolean shouldSkip(Throwable ex, long skipCount) throws SkipLimitExceededException {

        if(ex instanceof FileNotFoundException){
            return false;
        }

        return skipCount >= skipLimit;
    }
}
