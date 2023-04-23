package com.kyc.core.model.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateEngineException;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class AbstractPdfThymeleafTemplateRender<T> {

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractPdfThymeleafTemplateRender.class);

    private final String nameTemplate;
    private final KycMessages kycMessages;
    private TemplateEngine templateEngine;
    private final String baseDirTemplate;

    public AbstractPdfThymeleafTemplateRender(String nameTemplate,
                                              KycMessages kycMessages,
                                              TemplateEngine templateEngine,
                                              String baseDirTemplate) {
        this.nameTemplate = nameTemplate;
        this.kycMessages = kycMessages;
        this.templateEngine = templateEngine;
        this.baseDirTemplate = baseDirTemplate;
    }

    /**
     * https://github.com/danfickle/openhtmltopdf/issues/107
     * https://stackoverflow.com/questions/72013516/html-to-pdf-conversion-using-openhtmltopdf-with-bytearrayoutputstream
     * https://github.com/vsch/flexmark-java/blob/master/flexmark-pdf-converter/src/main/java/com/vladsch/flexmark/pdf/converter/PdfConverterExtension.java#L64
     * @param data
     * @return
     * @throws KycException
     */
    public ByteArrayResource generateReport(String serialNumber, RequestData<T> data) throws KycException {


        try{

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            String baseUri = null;
            if(StringUtils.isNotEmpty(baseDirTemplate)){
                baseUri = new ClassPathResource(baseDirTemplate).getURL().toExternalForm()+"/";
            }

            Context ctx = fillContext(serialNumber,data);
            String html = templateEngine.process(nameTemplate,ctx);

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html,baseUri);
            builder.toStream(bytes);
            builder.run();

            return new ByteArrayResource(bytes.toByteArray());
        }
        catch(IOException | TemplateEngineException ioex){
            throw KycException.builder()
                    .exception(ioex)
                    .errorData(kycMessages.getMessage(""))
                    .build();
        }
    }

    protected abstract Context fillContext(String serialNumber,RequestData<T> data);
}
