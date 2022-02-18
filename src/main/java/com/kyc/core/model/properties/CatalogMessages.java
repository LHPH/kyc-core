package com.kyc.core.model.properties;

import com.kyc.core.model.web.MessageData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class CatalogMessages {

    private Map<String, MessageData> messages = new HashMap<>();

}
