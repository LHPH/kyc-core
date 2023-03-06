package com.kyc.core.model.reports;

import com.kyc.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.util.Date;

@Getter
@Setter
public class ReportData extends BaseModel {

    private String id;
    private String name;
    private String mimeType;
    private String url;
    private long size;
    private Date date;
    private Resource resource;

}
