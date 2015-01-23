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
 * This is an singleton class that maps classes, that can be parsed
 * with JAXB parser, to Strings.
 * If you want to parse new class with the application, you must add it
 * to the ObjectHolder's constructor, to add it dynamically with addElement
 * method.
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

    public boolean containsKey(String s){
        return map.containsKey(s);
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
