package com.kyc.core.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MockObject {

    private String stringField;
    private Integer integerField;
    private Long longField;
    private MockObject2 objectField;
}
