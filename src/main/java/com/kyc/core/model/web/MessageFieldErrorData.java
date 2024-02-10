package com.kyc.core.model.web;

import com.kyc.core.model.MessageData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageFieldErrorData extends MessageData {

    private List<FieldErrorData> details = new ArrayList<>();

    public void addDetail(FieldErrorData value){

        if(details!=null){
            details.add(value);
        }
    }
}
