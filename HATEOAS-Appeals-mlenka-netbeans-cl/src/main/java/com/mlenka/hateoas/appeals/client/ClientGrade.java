/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.client;

import com.mlenka.hateoas.appeals.model.AppealStatus;
import com.mlenka.hateoas.appeals.model.Grade;
import com.mlenka.hateoas.appeals.model.Student;
import com.mlenka.hateoas.appeals.representations.Representation;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sphinx
 */
@XmlRootElement(name="grade",namespace=Representation.MYGRADES_NAMESPACE)
public class ClientGrade {
    @XmlElement(name="student",namespace=Representation.MYGRADES_NAMESPACE)
    private Student student;
    @XmlElement(name="score",namespace=Representation.MYGRADES_NAMESPACE)
    private int score;
    @XmlElement(name="feedback",namespace=Representation.MYGRADES_NAMESPACE)
    private String feedback;
    @XmlElement(name="status",namespace=Representation.MYGRADES_NAMESPACE)
    private AppealStatus appealStatus;
    public ClientGrade(){
        
    }
    public ClientGrade(Grade grade){
        this.student = grade.getStudent();
        this.score = grade.getScore();
        this.feedback = grade.getFeedback();
        this.appealStatus = grade.getAppealStatus();
    }
    public Grade getGrade(){
        return new Grade(this.getStudent(), this.getScore(), this.getFeedback(), this.getAppealStatus());
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @return the appealStatus
     */
    public AppealStatus getAppealStatus() {
        return appealStatus;
    }
    
    
}
