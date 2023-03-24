package com.kyc.core.model.reports.processors;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;


public abstract class AbstractGeneratePdfBoxReport<T> implements InitializingBean {

    private byte [] bytesTemplate;

    private final String pathTemplate;
    private final KycMessages kycMessages;

    public AbstractGeneratePdfBoxReport(String pathTemplate, KycMessages kycMessages){
        this.pathTemplate = pathTemplate;
        this.kycMessages = kycMessages;
    }

    @PostConstruct
    public void init() throws KycException {

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

    public ByteArrayResource generateReport(RequestData<T> data) throws KycException{

        try(PDDocument pDDocument = PDDocument.load(bytesTemplate)){

            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();

            fillFields(pDAcroForm,data);

            pDAcroForm.flatten();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            pDDocument.save(bytes);
            return new ByteArrayResource(bytes.toByteArray());
        }
        catch(IOException ioex){
            throw KycException.builder()
                    .exception(ioex)
                    .errorData(kycMessages.getMessage(""))
                    .build();
        }
    }

    protected abstract void fillFields(PDAcroForm pdAcroForm, RequestData<T> data) throws IOException;

    protected void setTextField(PDAcroForm pdAcroForm, String textFieldName, Object value, boolean uppercase) throws IOException{

        PDField pdField = pdAcroForm.getField(textFieldName);
        PDTextField textField = (PDTextField) pdField;

        if(uppercase){
            textField.setValue(Objects.toString(value).toUpperCase(new Locale("es")));
        }
        else{
            textField.setValue(Objects.toString(value));
        }
    }

    protected void setTextField(PDAcroForm pdAcroForm, String textFieldName, Object value) throws IOException{

        setTextField(pdAcroForm,textFieldName,value,true);
    }

    protected void setCheckBox(PDAcroForm pdAcroForm, String checkBoxName, boolean check) throws IOException {

        PDField pdField = pdAcroForm.getField(checkBoxName);
        PDCheckBox pdCheckBox = (PDCheckBox) pdField;
        if(check){
            pdCheckBox.check();
        }
        else{
            pdCheckBox.unCheck();
        }
    }

    protected void setRadioButton(PDAcroForm pdAcroForm, String radioButtonName, String option) throws IOException {

        PDField pdField = pdAcroForm.getField(radioButtonName);
        PDRadioButton pdRadioButton = (PDRadioButton) pdField;

        pdRadioButton.setValue(option);
    }

    @Override
    public void afterPropertiesSet() throws IllegalArgumentException {
        Assert.notNull(bytesTemplate,"bytesTemplate must not null");
        Assert.notNull(pathTemplate,"pathTemplate must not null");
    }
}