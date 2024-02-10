package com.kyc.core.util;

import com.kyc.core.model.MessageData;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class ReactiveUtilTest {

    @Test
    public void sendReactiveError_createMonoError_returnMonoError(){

        Mono<Void> monoError = ReactiveUtil.sendReactiveError(new NullPointerException(), HttpStatus.SERVICE_UNAVAILABLE,new MessageData(),null);
        Assertions.assertNotNull(monoError);
    }


}
