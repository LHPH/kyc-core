package com.kyc.core.util;

import com.kyc.core.model.MockObject;
import com.kyc.core.model.MockObject2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
