package com.kyc.core.model.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.TextSegment;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ByteArrayResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class AbstractWordTemplateRender<T> extends AbstractStreamReportTemplateRender {

    public AbstractWordTemplateRender(String pathTemplate, KycMessages kycMessages){
        super(pathTemplate,kycMessages);
    }

    public ByteArrayResource generateReport(String serialNumber,RequestData<T> data) throws KycException{

        try(XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(getBytesTemplate()))){

            Map<String,String> variables = fillVariables(doc,serialNumber,data);
            List<IBodyElement> bodyElements = doc.getBodyElements();

            for(Map.Entry<String,String> entry : variables.entrySet()){

                String placeHolder = entry.getKey();
                String replaceText = entry.getValue();
                replaceAllBodyElements(bodyElements,placeHolder,replaceText);
            }
            additionalProcessing(doc,data);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            doc.write(bytes);
            return new ByteArrayResource(bytes.toByteArray());
        }
        catch(IOException | POIXMLException ioex){
            throw KycException.builder()
                    .exception(ioex)
                    .errorData(getKycMessages().getMessage(""))
                    .build();
        }
    }

    protected abstract Map<String, String> fillVariables(XWPFDocument doc,String serialNumber, RequestData<T> data);

    protected abstract void additionalProcessing(XWPFDocument doc, RequestData<T> data);

    /**
     * This are the references for the next three methods
     * https://stackoverflow.com/questions/24203087/replacing-a-text-in-apache-poi-xwpf-not-working
       https://stackoverflow.com/questions/19393505/how-to-replace-placeholders-in-header-of-docx-in-java-using-poi-3-8/30131160#30131160
    */
    protected void replaceAllBodyElements(List<IBodyElement> bodyElements, String placeHolder, String replaceText){
        for (IBodyElement bodyElement : bodyElements) {
            if (bodyElement.getElementType().compareTo(BodyElementType.PARAGRAPH) == 0)
                replaceInParagraphs((XWPFParagraph) bodyElement, placeHolder, replaceText);
            if (bodyElement.getElementType().compareTo(BodyElementType.TABLE) == 0)
                replaceTable((XWPFTable) bodyElement, placeHolder, replaceText);
        }
    }

    protected long replaceInParagraphs(XWPFParagraph paragraph, String placeHolder, String replaceText) {

        long count = 0;
        List<XWPFRun> runs = paragraph.getRuns();

        TextSegment found = paragraph.searchText(placeHolder, new PositionInParagraph());
        if (found != null) {
            count++;
            if (found.getBeginRun() == found.getEndRun()) {
                // whole search string is in one Run
                XWPFRun run = runs.get(found.getBeginRun());
                String runText = run.getText(run.getTextPosition());
                String replaced = runText.replace(placeHolder, replaceText);
                run.setText(replaced, 0);
            } else {
                // The search string spans over more than one Run
                // Put the Strings together
                StringBuilder b = new StringBuilder();
                for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
                    XWPFRun run = runs.get(runPos);
                    b.append(run.getText(run.getTextPosition()));
                }
                String connectedRuns = b.toString();
                String replaced = connectedRuns.replace(placeHolder, replaceText);

                // The first Run receives the replaced String of all connected Runs
                XWPFRun partOne = runs.get(found.getBeginRun());
                partOne.setText(replaced, 0);
                // Removing the text in the other Runs.
                for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
                    XWPFRun partNext = runs.get(runPos);
                    partNext.setText("", 0);
                }
            }
        }
        return count;
    }

    private void replaceTable(XWPFTable table, String placeHolder, String replaceText) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (IBodyElement bodyElement : cell.getBodyElements()) {
                    if (bodyElement.getElementType().compareTo(BodyElementType.PARAGRAPH) == 0) {
                        replaceInParagraphs((XWPFParagraph) bodyElement, placeHolder, replaceText);
                    }
                    if (bodyElement.getElementType().compareTo(BodyElementType.TABLE) == 0) {
                        replaceTable((XWPFTable) bodyElement, placeHolder, replaceText);
                    }
                }
            }
        }
    }
}
