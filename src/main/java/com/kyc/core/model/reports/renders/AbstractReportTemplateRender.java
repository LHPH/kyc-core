package com.kyc.core.model.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Getter
public abstract class AbstractReportTemplateRender implements InitializingBean {

    private byte [] bytesTemplate;

    private final String pathTemplate;
    private final KycMessages kycMessages;

    public AbstractReportTemplateRender(String pathTemplate, KycMessages kycMessages){
        this.pathTemplate = pathTemplate;
        this.kycMessages = kycMessages;
    }

    @PostConstruct
    public void loadBytesTemplate() throws KycException {

        ClassPathResource cl = new ClassPathResource(pathTemplate);
        try(InputStream in = cl.getInputStream()){

            bytesTemplate = IOUtils.toByteArray(in);
        }
        catch(IOException ioex){

            throw KycException.builder()
                    .exception(ioex)
                    .errorData(kycMessages.getMessage(""))
                    .build();
        }
    }

    @Override
    public void afterPropertiesSet() throws IllegalArgumentException {
        Assert.notNull(bytesTemplate,"bytesTemplate must not null");
        Assert.notNull(pathTemplate,"pathTemplate must not null");
        Assert.notNull(kycMessages,"kycMessages must not null");
    }
}
