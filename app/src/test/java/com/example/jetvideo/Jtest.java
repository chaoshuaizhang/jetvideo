package com.example.jetvideo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.jetvideo.model.PageDestination;

import java.util.HashMap;
import java.util.Map;

public class Jtest {
    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put("k+" + i, "value+" + i);
        }
        return map;
    }
}
