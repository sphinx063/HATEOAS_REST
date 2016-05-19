/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.model;

import com.mlenka.hateoas.appeals.representations.Representation;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sphinx
 */
@XmlRootElement(name="student", namespace = Representation.MYGRADES_NAMESPACE)
public class Student {
    
    private String id;
    private String name;
    public Student(){
        
    }
    public Student(String id,String name){
        this.id=id;
        this.name = name;
    }

    /**
     * @return the id
     */
    @XmlElement(name="id",namespace = Representation.MYGRADES_NAMESPACE)
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    @XmlElement(name="name",namespace = Representation.MYGRADES_NAMESPACE)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
