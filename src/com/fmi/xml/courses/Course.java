/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.xml.courses;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dimitar
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "course")
public class Course {

    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "type")
    private String type;

    @XmlElement(name = "dependancy_course", type = Course.class)
    private List<Course> dependencies = new ArrayList<>();

    public Course() {
    }

    public Course(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Course> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Course> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        if (dependencies != null && dependencies.size() > 0) {
            result.append("\n\tDependencies: \n");
            for (Course dependency : dependencies) {
                result.append(String.format("\t-%s\n", dependency.getName()));
            }
        }

        return String.format("Course: %s %s", name, result.toString());

    }

}
