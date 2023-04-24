package com.kyc.core.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class AbstractPdfJasperRenderTest {

    private static final String PARAM = "PARAM_1";

    @Test
    public void generateReport_generatingReport_returnByteArrayResource(){

        AbstractPdfJasperRender render = new AbstractPdfJasperRender("test_jasper.jasper",new KycMessages()){

            @Override
            protected Map<String, Object> fillParameters(String serialNumber, RequestData data) {

                Map<String, Object> params = new HashMap<>();
                params.put(PARAM,"TEST");
                return params;
            }
        };
        render.loadBytesTemplate();
        render.afterPropertiesSet();

        ByteArrayResource result = render.generateReport("TEST",RequestData.builder().build());
        Assertions.assertNotNull(result);
    }

    @Test
    public void generateReport_generatingReport_throwKycException(){

        Assertions.assertThrows(KycException.class,()->{

            AbstractPdfJasperRender render = new AbstractPdfJasperRender("test_jasper.jrxml",new KycMessages()){

                @Override
                protected Map<String, Object> fillParameters(String serialNumber, RequestData data) {

                    Map<String, Object> params = new HashMap<>();
                    params.put(PARAM,"TEST");
                    return params;
                }
            };
            render.loadBytesTemplate();
            render.afterPropertiesSet();

            render.generateReport("TEST",RequestData.builder().build());
        });
    }
}
