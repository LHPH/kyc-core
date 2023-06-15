package com.kyc.core.util;

import com.kyc.core.model.MockObject;
import com.kyc.core.model.MockObject2;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class GeneralUtilTest {

    @Test
    public void isNull_checkNullObject_returnTrue(){

        Object test = null;
        Assert.assertTrue(GeneralUtil.isNull(test));
    }

    @Test
    public void isNull_checkNotNullObject_returnFalse(){

        Object test = new Object();
        Assert.assertFalse(GeneralUtil.isNull(test));
    }

    @Test
    public void setIfNotNull_checkNotNullValue_settingValue(){

        MockObject mock = new MockObject();
        mock.setStringField("other");
        GeneralUtil.setIfNotNull(mock::setStringField,"test");

        Assert.assertEquals("test",mock.getStringField());
    }

    @Test
    public void setIfNotNull_checkNullValue_NotSettingValue(){

        MockObject mock = new MockObject();
        mock.setStringField("other");
        GeneralUtil.setIfNotNull(mock::setStringField,null);

        Assert.assertNotNull(mock.getStringField());
        Assert.assertEquals("other",mock.getStringField());
    }

    @Test
    public void isNotNullObjectAndAttribute_checkingNotNullObjectAndNotNullAttribute_returnTrue(){

        MockObject mock = new MockObject();
        mock.setObjectField(new MockObject2());
        Assert.assertTrue(GeneralUtil.isNotNullObjectAndAttribute(mock,MockObject::getObjectField));
    }

    @Test
    public void isNotNullObjectAndAttribute_checkingNullObjectAndNotNullAttribute_returnFalse(){

        MockObject mock = null;
        Assert.assertFalse(GeneralUtil.isNotNullObjectAndAttribute(mock,MockObject::getObjectField));
    }

    @Test
    public void isNotNullObjectAndAttribute_checkingNotNullObjectButNullAttribute_returnFalse(){

        MockObject mock = new MockObject();
        mock.setObjectField(null);
        Assert.assertFalse(GeneralUtil.isNotNullObjectAndAttribute(mock,MockObject::getObjectField));
    }

    @Test
    public void isListNotNullAndNotEmpty_checkListNotNullAndNotEmpty_returnTrue(){

        List<String> list = new ArrayList<>();
        list.add("test");
        Assert.assertTrue(GeneralUtil.isListNotNullAndNotEmpty(list));
    }

    @Test
    public void isListNotNullAndNotEmpty_checkListNull_returnFalse(){

        List<String> list = null;
        Assert.assertFalse(GeneralUtil.isListNotNullAndNotEmpty(list));
    }

    @Test
    public void isListNotNullAndNotEmpty_checkListNotNullButEmpty_returnFalse(){

        List<String> list = new ArrayList<>();
        Assert.assertFalse(GeneralUtil.isListNotNullAndNotEmpty(list));
    }

    @Test
    public void isMapNotNullAndNotEmpty_checkMapNotNullAndNotEmpty_returnTrue(){

        Map<String,Object> map = new HashMap<>();
        map.put("key","value");
        Assert.assertTrue(GeneralUtil.isMapNotNullAndNotEmpty(map));
    }

    @Test
    public void isMapNotNullAndNotEmpty_checkListNull_returnFalse(){

        Map<String,Object> map = null;
        Assert.assertFalse(GeneralUtil.isMapNotNullAndNotEmpty(map));
    }

    @Test
    public void isMapNotNullAndNotEmpty_checkListNotNullButEmpty_returnFalse(){

        Map<String,Object> map = new HashMap<>();
        Assert.assertFalse(GeneralUtil.isMapNotNullAndNotEmpty(map));
    }

    @Test
    public void getValue_getValueFromWrapper_returnValue(){

        MockObject mock = new MockObject();
        mock.setIntegerField(1);

        Integer value = GeneralUtil.getValue(mock,MockObject::getIntegerField);
        Assertions.assertEquals(1,value);
    }

    @Test
    public void getValue_WrapperNull_returnNull(){

        MockObject mock = null;

        Integer value = GeneralUtil.getValue(mock,MockObject::getIntegerField);
        Assertions.assertNull(value);
    }

    @Test
    public void toInt_doubleNotNullToInt_returnIntValue(){

        Integer value = GeneralUtil.toInt(100.23D);
        Assertions.assertEquals(100,value);
    }

    @Test
    public void toInt_doubleNullToInt_returnZero(){

        Double doubleValue = null;
        Integer value = GeneralUtil.toInt(doubleValue);
        Assertions.assertEquals(0,value);
    }

    @Test
    public void setNullIfEquals_differentValues_returnNotNull(){

        Assertions.assertNotNull(GeneralUtil.setNullIfEquals("value","other"));
    }

    @Test
    public void setNullIfEquals_mainValueAreNull_returnNull(){

        Assertions.assertNull(GeneralUtil.setNullIfEquals(null,"other"));
    }

    @Test
    public void setNullIfEquals_sameValues_returnNull(){

        Assertions.assertNull(GeneralUtil.setNullIfEquals("value","value"));
    }

    @Test
    public void getType_castValue_returnCastedValue(){

        Integer value = 12;
        Integer result = GeneralUtil.getType(value,Integer.class);
        Assertions.assertEquals(value,result);
    }

    @Test
    public void paramNotNull_valueNotNull_noRaiseException(){

        Assertions.assertDoesNotThrow(()->{
            GeneralUtil.paramNotNull("param","value");
        });
    }

    @Test
    public void paramNotNull_valueNull_raiseException(){

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            GeneralUtil.paramNotNull("param",null);
        });
    }

    @Test
    public void convertOrNull_integerString_convertValue(){

        String value = "12";
        Integer result = GeneralUtil.convertOrNull(value,Integer.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(12,result);
    }

    @Test
    public void convertOrNull_longString_convertValue(){

        String value = "12";
        Long result = GeneralUtil.convertOrNull(value,Long.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(12,result);
    }

    @Test
    public void convertOrNull_doubleString_convertValue(){

        String value = "12.90";
        Double result = GeneralUtil.convertOrNull(value,Double.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(12.90,result);
    }

    @Test
    public void convertOrNull_floatString_convertValue(){

        String value = "12.0";
        Float result = GeneralUtil.convertOrNull(value,Float.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(12f,result);
    }

    @Test
    public void convertOrNull_badValue_returnNull(){

        String value = "aa";
        Integer result = GeneralUtil.convertOrNull(value,Integer.class);
        Assertions.assertNull(result);
    }

    @Test
    public void convertOrNull_nullValue_returnNull(){

        String value = null;
        Integer result = GeneralUtil.convertOrNull(value,Integer.class);
        Assertions.assertNull(result);
    }

    @Test
    public void mapStringValue_convertMap_returningNewMap(){

        Map<String,Object> map = new HashMap<>();
        map.put("key","value");
        map.put("key2",20);
        map.put("key3",new MockObject());
        map.put("key4",null);

        Map<String,String> result = GeneralUtil.mapStringValue(map);

        Assertions.assertEquals(map.keySet(),result.keySet());

        List<String> expectedValues = map.values().stream().map(value -> Objects.toString(value,null))
                .collect(Collectors.toList());

        Assertions.assertEquals(expectedValues, new ArrayList<>(result.values()));
    }

    @Test
    public void mapStringValue_nullMap_returningNewMap(){

        Map<String,Object> map = null;

        Map<String,String> result = GeneralUtil.mapStringValue(map);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }



}
