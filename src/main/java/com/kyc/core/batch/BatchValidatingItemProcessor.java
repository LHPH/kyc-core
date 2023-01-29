package com.kyc.core.batch;

import com.kyc.core.exception.KycBatchException;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

@Setter
public class BatchValidatingItemProcessor<T> implements ItemProcessor<T, T>, InitializingBean {

    private Validator<? super T> validator;
    private boolean filter = false;

    @Override
    public T process(T item) throws KycBatchException {
        try {
            this.validator.validate(item);
            return item;
        } catch (KycBatchException var3) {
            if (this.filter) {
                return null;
            } else {
                throw var3;
            }
        }
    }

    public void afterPropertiesSet() {
        Assert.notNull(this.validator, "Validator must not be null.");
    }
}
