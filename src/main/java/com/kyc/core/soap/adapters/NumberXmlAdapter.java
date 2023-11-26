package com.kyc.core.soap.adapters;

import com.kyc.core.util.GeneralUtil;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Objects;

@RequiredArgsConstructor
public class NumberXmlAdapter<T extends Number> extends XmlAdapter<String, T> {

    private final Class<T> type;

    @Override
    public T unmarshal(String v) {

       return GeneralUtil.convertOrNull(v,type);
    }

    @Override
    public String marshal(T v) {

        return Objects.toString(v,null);
    }
}
