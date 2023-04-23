package com.kyc.core.model.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public abstract class AbstractPdfBoxTemplateRender<T> extends AbstractStreamReportTemplateRender {

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractPdfBoxTemplateRender.class);

    public AbstractPdfBoxTemplateRender(String pathTemplate, KycMessages kycMessages){
        super(pathTemplate,kycMessages);
    }

    public ByteArrayResource generateReport(String serialNumber, RequestData<T> data) throws KycException{

        try(PDDocument pDDocument = PDDocument.load(getBytesTemplate())){

            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();

            fillFields(pDAcroForm,serialNumber,data);

            pDAcroForm.flatten();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            pDDocument.save(bytes);
            return new ByteArrayResource(bytes.toByteArray());
        }
        catch(IOException ioex){
            throw KycException.builder()
                    .exception(ioex)
                    .errorData(getKycMessages().getMessage(""))
                    .build();
        }
    }

    protected abstract void fillFields(PDAcroForm pdAcroForm, String serialNumber, RequestData<T> data) throws IOException;

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
}