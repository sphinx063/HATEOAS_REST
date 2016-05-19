/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.model;

import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sphinx
 */
public class Grade {
   // private int gradeId;
    private Student student;
    private int score;
    private String feedback;
    @XmlTransient
    private AppealStatus appealStatus = AppealStatus.NOT_APPEALED;
    public Grade(Student student,int score,String feedback,AppealStatus appealStatus){
        this.student = student;
        this.score = score;
        this.feedback = feedback;
       // this.gradeId = gradeId;
        this.appealStatus = appealStatus;
    }
    public Grade(Student student,int score,String feedback){
        this(student,score,feedback,AppealStatus.NOT_APPEALED);
    }

    /**
     * @return the gradeId
     */
    /*
    public int getGradeId() {
        return gradeId;
    }

    /**
     * @param gradeId the gradeId to set
     */
    /*
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * @return the appealStatus
     */
    public AppealStatus getAppealStatus() {
        return appealStatus;
    }

    /**
     * @param appealStatus the appealStatus to set
     */
    public void setAppealStatus(AppealStatus appealStatus) {
        this.appealStatus = appealStatus;
    }
    
}
