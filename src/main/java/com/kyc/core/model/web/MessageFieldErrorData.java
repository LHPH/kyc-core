package com.kyc.core.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageFieldErrorData extends MessageData{

    private List<FieldErrorData> details = new ArrayList<>();

    public void addDetail(FieldErrorData value){

        if(details!=null){
            details.add(value);
        }
    }
}
