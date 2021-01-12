package com.example.jetvideo;

import com.alibaba.fastjson.JSONObject;
import com.example.jetvideo.data.model.PageDestination;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class Jtest {

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 5; i++) {
            map.put("k+" + i, "value+" + i);
        }



        return map;
    }

    @Test
    public void test(){
        JSONObject o = new JSONObject();
        o.put("code", 1002);
        o.put("msg", "信息--");
        o.put("t", "泛型信息");
        JsonCallback<String> callback = new JsonCallback<String>(){
            @Override
            public void onCacheSuccess(Dto<String> response) {

            }
        };
        parseDto(o.toJSONString(), callback);
    }

    <T> void parseDto(String s, JsonCallback<T> tCallable){
        ParameterizedType type = (ParameterizedType) tCallable.getClass().getGenericSuperclass();
        System.out.println(type);
    }
}

class Dto<T>{
    public int code;
    public String msg;
    public T t;
}

abstract class JsonCallback<T> {
    abstract void onCacheSuccess(Dto<T> response) ;
}
