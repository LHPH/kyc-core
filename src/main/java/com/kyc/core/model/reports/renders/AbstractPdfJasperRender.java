package com.kyc.core.model.reports.renders;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.properties.KycMessages;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

public abstract class AbstractPdfJasperRender<T> extends AbstractReportTemplateRender{

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractPdfJasperRender.class);

    public AbstractPdfJasperRender(String pathMasterReport, KycMessages kycMessages){

        super(pathMasterReport,kycMessages);
    }

    public ByteArrayResource generateReport(String serialNumber, RequestData<T> data)  {

        try {
            //JasperReport report = JasperCompileManager.compileReport("");

            JasperReport report = (JasperReport) JRLoader.loadObject(new ByteArrayInputStream(getBytesTemplate()));

            Map<String,Object> params = fillParameters(serialNumber, data);

            JasperPrint print = JasperFillManager.fillReport(report,params,new JREmptyDataSource());


            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            JasperExportManager.exportReportToPdfStream(print,bytes);

            return new ByteArrayResource(bytes.toByteArray());
        } catch (JRException jre) {
            throw KycException.builder()
                    .exception(jre)
                    .errorData(getKycMessages().getMessage(""))
                    .build();
        }
    }

    protected abstract Map<String,Object> fillParameters(String serialNumber,RequestData<T> data);
}
