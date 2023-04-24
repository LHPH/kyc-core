package com.kyc.core.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class AbstractPdfBoxTemplateRenderTest {

    private static final String TEXT_FIELD = "F_TEXTFIELD";
    private static final String RADIO_BUTTON = "F_RADIO_BUTTON";
    private static final String CHECK_BOX_1 = "F_CHECK_BOX_1";
    private static final String CHECK_BOX_2 = "F_CHECK_BOX_2";

    @Test
    public void generateReport_generatingReport_returnByteArrayResource(){

        AbstractPdfBoxTemplateRender render = new AbstractPdfBoxTemplateRender("test_pdfbox.pdf",new KycMessages()){

            protected void fillFields(PDAcroForm pdAcroForm, String serialNumber, RequestData data) throws IOException {

                setTextField(pdAcroForm,TEXT_FIELD,"TEST");
                setRadioButton(pdAcroForm,RADIO_BUTTON,"Choice1");
                setCheckBox(pdAcroForm,CHECK_BOX_1,true);
                setCheckBox(pdAcroForm,CHECK_BOX_2,false);
            }
        };
        render.loadBytesTemplate();
        render.afterPropertiesSet();

        ByteArrayResource result = render.generateReport("TEST",RequestData.builder().build());
        Assertions.assertNotNull(result);
    }

    @Test
    public void generateReport_generatingReport2_returnByteArrayResource(){

        AbstractPdfBoxTemplateRender render = new AbstractPdfBoxTemplateRender("test_pdfbox.pdf",new KycMessages()){

            protected void fillFields(PDAcroForm pdAcroForm, String serialNumber, RequestData data) throws IOException {

                setTextField(pdAcroForm,TEXT_FIELD,"TEST",false);
                setRadioButton(pdAcroForm,RADIO_BUTTON,"Choice2");
                setCheckBox(pdAcroForm,CHECK_BOX_1,true);
                setCheckBox(pdAcroForm,CHECK_BOX_2,false);
            }
        };
        render.loadBytesTemplate();
        render.afterPropertiesSet();

        ByteArrayResource result = render.generateReport("TEST",RequestData.builder().build());
        Assertions.assertNotNull(result);
    }

    @Test
    public void generateReport_generatingReportButErrorProcessing_throwKycException(){

        Assertions.assertThrows(KycException.class,()->{

            AbstractPdfBoxTemplateRender render = new AbstractPdfBoxTemplateRender("test_jasper.jasper",new KycMessages()){

                protected void fillFields(PDAcroForm pdAcroForm, String serialNumber, RequestData data) throws IOException {

                    setTextField(pdAcroForm,TEXT_FIELD,"TEST",false);
                }
            };
            render.loadBytesTemplate();
            render.afterPropertiesSet();

            render.generateReport("TEST",RequestData.builder().build());
        });
    }



}
