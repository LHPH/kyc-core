package com.kyc.core.exception.factory;

import com.kyc.core.factory.SimpleJdbcCallFactory;
import com.kyc.core.model.config.SimpleJdbcCallParams;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

@RunWith(MockitoJUnitRunner.class)
public class SimpleJdbcCallFactoryTest {

    @Test
    public void getSimpleJdbcCall_createSP_returnSimpleJdbcCall(){

        JdbcTemplate template = Mockito.mock(JdbcTemplate.class);
        SimpleJdbcCallFactory factory = new SimpleJdbcCallFactory(template);
        SimpleJdbcCall simpleJdbcCall = factory.getSimpleJdbcCall(SimpleJdbcCallParams.builder().build(),false);

        Assert.assertNotNull(simpleJdbcCall);
        Assert.assertFalse(simpleJdbcCall.isFunction());
    }

    @Test
    public void getSimpleJdbcCall_createFunction_returnSimpleJdbcCall(){

        JdbcTemplate template = Mockito.mock(JdbcTemplate.class);
        SimpleJdbcCallFactory factory = new SimpleJdbcCallFactory(template);
        SimpleJdbcCall simpleJdbcCall = factory.getSimpleJdbcCall(SimpleJdbcCallParams.builder().build(),true);

        Assert.assertNotNull(simpleJdbcCall);
        Assert.assertTrue(simpleJdbcCall.isFunction());
    }
}
