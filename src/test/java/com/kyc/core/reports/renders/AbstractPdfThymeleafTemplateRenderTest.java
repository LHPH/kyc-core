package com.kyc.core.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AbstractPdfThymeleafTemplateRenderTest {

    private static SpringTemplateEngine templateEngine;

    @BeforeAll
    public static void setUp(){

        templateEngine = new SpringTemplateEngine();

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(new StaticApplicationContext());
        templateResolver.setPrefix("classpath:/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Test
    public void generateReport_generatingReport_returnByteArrayResource(){

        AbstractPdfThymeleafTemplateRender render = new AbstractPdfThymeleafTemplateRender("test_thymeleaf",new KycMessages(),templateEngine,null){

            @Override
            protected Context fillContext(String serialNumber, RequestData data) {
                Context context = new Context();
                Map<String,Object> vars = new HashMap<>();
                vars.put("word","WORLD");
                context.setVariables(vars);
                return context;
            }
        };

        ByteArrayResource result = render.generateReport("TEST",RequestData.builder().build());
        Assertions.assertNotNull(result);
    }

    @Test
    public void generateReport_generatingReportButErrorBaseDir_throwsKycException(){

       Assertions.assertThrows(KycException.class,()->{

           AbstractPdfThymeleafTemplateRender render = new AbstractPdfThymeleafTemplateRender("test_thymeleaf",new KycMessages(),templateEngine,"templates"){

               @Override
               protected Context fillContext(String serialNumber, RequestData data) {
                   Context context = new Context();
                   Map<String,Object> vars = new HashMap<>();
                   vars.put("word","WORLD");
                   context.setVariables(vars);
                   return context;
               }
           };

           render.generateReport("TEST",RequestData.builder().build());
       });
    }

    @Test
    public void generateReport_generatingReportButErrorTemplateEngine_throwsKycException(){

        Assertions.assertThrows(KycException.class,()->{

            AbstractPdfThymeleafTemplateRender render = new AbstractPdfThymeleafTemplateRender("test",new KycMessages(),templateEngine,null){

                @Override
                protected Context fillContext(String serialNumber, RequestData data) {
                    Context context = new Context();
                    Map<String,Object> vars = new HashMap<>();
                    vars.put("word","WORLD");
                    context.setVariables(vars);
                    return context;
                }
            };

            render.generateReport("TEST",RequestData.builder().build());
        });
    }
}
