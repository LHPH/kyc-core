package com.kyc.core.util;

import com.kyc.core.validation.model.RuleValidation;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class GeneralUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralUtil.class);

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

    public static <T,R> R getValue(T parent, Function<T,R> getter){

        if(ObjectUtils.allNotNull(parent,getter)){
            return getter.apply(parent);
        }
        return null;
    }

    public static Integer toInt(Double value, Integer defaultValue){

        if(value != null){
            return value.intValue();
        }
        return defaultValue;
    }

    public static Integer toInt(Double value){
        return toInt(value,0);
    }

    public static String setNullIfEquals(String value, String valueToCompare){

        if(value == null || value.equals(valueToCompare)){
            return null;
        }
        else{
            return value;
        }
    }

    public static <T> T getType(Object source, Class<T> type){
        return type.cast(source);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T convertOrNull(String value, Class<T> type){

        paramNotNull("type",type);
        if(value==null)
            return null;
        try{

            if(type.equals(Integer.class)){
                return (T) Integer.valueOf(value);
            }
            if(type.equals(Long.class)){
                return (T) Long.valueOf(value);
            }
            if(type.equals(Double.class)){
                return  (T) Double.valueOf(value);
            }
            if(type.equals(Float.class)){
                return (T) Float.valueOf(value);
            }
        }
        catch(NumberFormatException ex){
            LOGGER.error(" ",ex);
        }
        return null;
    }

    public static void paramNotNull(String nameParam,Object valueParam){
        if(valueParam==null){
            throw new IllegalArgumentException(nameParam+"must not be null");
        }
    }

    public static Map<String,String> mapStringValue(Map<String,Object> map){

        Map<String,String> newMap = new HashMap<>();
        if(CollectionUtils.isEmpty(map)){
            return newMap;
        }

        for(Map.Entry<String,Object> entry : map.entrySet()){
            newMap.put(entry.getKey(),Objects.toString(entry.getValue(),null));
        }
        return newMap;
    }

    private GeneralUtil(){}
}
