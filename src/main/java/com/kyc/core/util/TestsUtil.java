package com.kyc.core.util;

import com.kyc.core.model.web.ResponseData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TestsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestsUtil.class);

    public static <T> ResponseEntity<ResponseData<T>> getResponseTest(T body, HttpStatus httpStatus){

        ResponseData<T> responseData = ResponseData.of(body,httpStatus);
        return responseData.toResponseEntity();
    }

    public static <T> ResponseEntity<ResponseData<T>> getResponseTest(T body){
        return getResponseTest(body,HttpStatus.OK);
    }

    public static List<String> checkPDAcroFormForms(String pathFile){

        ClassPathResource cl = new ClassPathResource(pathFile);
        List<String> list = new ArrayList<>();

        try(InputStream in  = cl.getInputStream()){

            PDDocument pDDocument = PDDocument.load(in);

            PDAcroForm acroForm = pDDocument.getDocumentCatalog().getAcroForm();

            for(PDField field : acroForm.getFields()){

                list.add(field.getFullyQualifiedName());
                LOGGER.info("{}",field.getFullyQualifiedName());
            }
            pDDocument.close();
        }
        catch(IOException ex){
            LOGGER.error(" ",ex);
            return null;
        }
        return list;
    }

    private TestsUtil(){}
}
