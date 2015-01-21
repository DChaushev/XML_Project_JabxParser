/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.xml.courses;

import com.fmi.xml.parsable.JaxbParsable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dimitar
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="schedule")
public class Schedule implements JaxbParsable{

    @XmlElement(name = "semester", type=Semester.class)
    private List<Semester> semesters;

    public Schedule() {
    }

    
    public Schedule(List<Semester> semesters) {
        this.semesters = semesters;
    }
    
    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        for (Semester semester : semesters) {
            result.append(String.format("%s\n", semester));
        }
        
        return result.toString();
    }
    
}
