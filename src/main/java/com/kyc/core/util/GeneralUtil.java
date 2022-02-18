package com.kyc.core.util;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public final class GeneralUtil {

    public static boolean isNull(Object obj) {
        return obj==null;
    }

    public static <T> void setIfNotNull(final Consumer<T> func, final T value){

        if(value!=null) {
            func.accept(value);
        }

    }

    public static <T,R> boolean isNotNullObjectAndAttribute(T object, Function<T,R> getter){

        if(object != null){
            return getter.apply(object) != null;
        }
        return false;
    }

    public static <T> boolean isListNotNullAndNotEmpty(List<T> list){
        return list != null && !list.isEmpty();
    }

    public static <K,V> boolean isMapNotNullAndNotEmpty(Map<K,V> map){
        return map != null && !map.isEmpty();
    }


    private GeneralUtil(){}
}
