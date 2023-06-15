package com.kyc.core.util;

import com.kyc.core.model.web.ResponseData;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.utils.test.TestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    @Test
    public void checkPDAcroFormForms_checkPDAcroForms_returnList(){

        List<String> result = TestsUtil.checkPDAcroFormForms("test_pdfbox.pdf");
        Assertions.assertNotNull(result);
    }
}
