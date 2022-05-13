package com.kyc.core.util;

import com.kyc.core.model.web.ResponseData;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class TestsUtilTest {

    @Test
    public void getResponseTest_generateResponseWithDefaultHttpStatus_returnResponseEntity(){

        ResponseEntity<ResponseData<String>> response = TestsUtil.getResponseTest("test");

        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("test",response.getBody().getData());

    }

    @Test
    public void getResponseTest_generateResponseWithProvidedHttpStatus_returnResponseEntity(){

        ResponseEntity<ResponseData<String>> response = TestsUtil.getResponseTest("test",HttpStatus.BAD_REQUEST);

        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("test",response.getBody().getData());
    }
}
