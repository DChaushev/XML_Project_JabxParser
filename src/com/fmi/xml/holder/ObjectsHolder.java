/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.xml.holder;

import com.fmi.xml.courses.Schedule;
import com.fmi.xml.others.TestObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dimitar
 */
public class ObjectsHolder {

    private static ObjectsHolder holder;

    private static Map<String, Class> map;

    private ObjectsHolder() {
        map = new HashMap<>();
        map.put("Schedule", Schedule.class);
        map.put("TestObject", TestObject.class);
    }

    public static ObjectsHolder getInstance() {
        if (holder == null) {
            synchronized (ObjectsHolder.class) {
                if (holder == null) {
                    holder = new ObjectsHolder();
                }
            }
        }
        return holder;
    }

    public Class getValue(String s) {
        if (map.containsKey(s)) {
            return map.get(s);
        }
        return null;
    }

    public void addElement(String s, Class c) {
        if (!map.containsKey(s)) {
            map.put(s, c);
        }
        throw new UnsupportedOperationException("Class already exists!");
    }

}
