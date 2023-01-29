package com.kyc.core.validation.model;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InputData<T> {

    private String fieldName;
    private T data;

    public InputData(T data){
        this.data = data;
    }
}
