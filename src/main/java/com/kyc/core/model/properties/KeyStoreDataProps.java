package com.kyc.core.model.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@NoArgsConstructor
public class KeyStoreDataProps {

    private boolean enabled;
    private Resource keyStore;
    private String keyAlias;
    private String keyPassword;
    private String keyStoreType;
    private String keyStorePassword;
}
