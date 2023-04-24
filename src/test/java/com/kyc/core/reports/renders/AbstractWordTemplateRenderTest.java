package com.kyc.core.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class AbstractWordTemplateRenderTest {

    @Test
    public void generateReport_generatingReport_returnByteArrayResource(){

        AbstractWordTemplateRender render = new AbstractWordTemplateRender("test_apache_poi_ooxml.docx",new KycMessages()){

            @Override
            protected Map<String, String> fillVariables(XWPFDocument doc, String serialNumber, RequestData data) {

                Map<String, String> vars = new HashMap<>();
                vars.put("TEXT ","TXET");
                vars.put("CHECKBOX","CHECKS");
                vars.put("ROW","ROW 1X1");
                return vars;
            }

            @Override
            protected void additionalProcessing(XWPFDocument doc, RequestData data) {}
        };

        render.loadBytesTemplate();
        render.afterPropertiesSet();

        ByteArrayResource result = render.generateReport("TEST",RequestData.builder().build());
        Assertions.assertNotNull(result);
    }

    @Test
    public void generateReport_generatingReportButBadFile_throwKycException(){

        Assertions.assertThrows(KycException.class,()->{

            AbstractWordTemplateRender render = new AbstractWordTemplateRender("test_apache_poi_ooxml.docx",new KycMessages()){

                @Override
                protected Map<String, String> fillVariables(XWPFDocument doc, String serialNumber, RequestData data) {

                    Map<String, String> vars = new HashMap<>();
                    vars.put("TEXT ","TXET");
                    vars.put("CHECKBOX","CHECKS");
                    vars.put("ROW","ROW 1X1");
                    return vars;
                }

                @Override
                protected void additionalProcessing(XWPFDocument doc, RequestData data) {
                    throw new POIXMLException();
                }
            };

            render.loadBytesTemplate();
            render.afterPropertiesSet();

            render.generateReport("TEST",RequestData.builder().build());
        });

    }
}
