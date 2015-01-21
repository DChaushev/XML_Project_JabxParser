/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fmi.xml.others;

import com.fmi.xml.parsable.JaxbParsable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vankata
 */
@XmlRootElement
public class TestObject implements JaxbParsable{
    private String name;
    private int years;
    private double balance;
    public TestObject(String name, int years, double balance){
    this.name=name;
    this.years = years;
    this.balance = balance;
    }
    public TestObject(){
        this("Vurbana",0,0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "TestObject{" + "name=" + name + ", years=" + years + ", balance=" + balance + '}';
    }
    
}
