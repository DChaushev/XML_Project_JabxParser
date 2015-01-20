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
@XmlRootElement(name="semester")
public class Semester {

    @XmlAttribute(name = "number")
    private int semesterNumber;

    @XmlElement(name = "course", type=Course.class)
    private List<Course> courses = new ArrayList<>();

    public Semester() {
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public List<Course> getCourses() {
        return courses;
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    
    public void addCourse(Course c){
        courses.add(c);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        result.append(String.format("Semester: %s\n", semesterNumber));
        
        for (Course course : courses) {
            result.append(course);
            result.append("\n");
        }
    
        return result.toString();
    }
    
}
