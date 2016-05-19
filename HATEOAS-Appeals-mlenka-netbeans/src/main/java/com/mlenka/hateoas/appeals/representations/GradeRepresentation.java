/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.representations;

import com.mlenka.hateoas.appeals.activities.InvalidGradeException;
import com.mlenka.hateoas.appeals.model.AppealStatus;
import com.mlenka.hateoas.appeals.model.Grade;
import com.mlenka.hateoas.appeals.model.Student;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sphinx
 */
@XmlRootElement(name="grade", namespace=Representation.MYGRADES_NAMESPACE)
public class GradeRepresentation extends Representation {
    @XmlElement(name="student", namespace=Representation.MYGRADES_NAMESPACE)
    private Student student;
    @XmlElement(name="score", namespace=Representation.MYGRADES_NAMESPACE)
    private int score;
    @XmlElement(name="feedback", namespace=Representation.MYGRADES_NAMESPACE)
    private String feedback;
    @XmlElement(name="status", namespace=Representation.MYGRADES_NAMESPACE)
    private AppealStatus appealStatus;
    
    public GradeRepresentation(){
        
    }
    public GradeRepresentation(Grade grade,Link... links){
        try{
        this.student = grade.getStudent();
        this.links = Arrays.asList(links);
        this.feedback = grade.getFeedback();
        this.score = grade.getScore();
        this.appealStatus = grade.getAppealStatus();
        }catch(Exception ex){
            throw new InvalidGradeException(ex);
        }
    }
    public static GradeRepresentation fromXmlString(String xmlString)throws JAXBException{
        GradeRepresentation gradeRepresentation = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GradeRepresentation.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            gradeRepresentation = (GradeRepresentation)unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()));
        } catch (JAXBException jxb) {
            throw new InvalidGradeException(jxb);
        }catch(Exception e){
            throw e;
        }
        return gradeRepresentation;
    }
    public static GradeRepresentation createGradeRepresentation(Grade grade,MyGradesUri gradeUri){
        GradeRepresentation gradeRepresentation = null;
        MyGradesUri appealUri = new MyGradesUri(gradeUri.getBaseUri()+"/appeal/"+gradeUri.getId().toString());
        if(grade.getAppealStatus() == AppealStatus.NOT_APPEALED){
            gradeRepresentation = new GradeRepresentation(grade,
             new Link(Representation.RELATIONS_URI+"appeal",appealUri),
               new Link(Representation.RELATIONS_URI+"update",gradeUri),
               new Link(Representation.RELATIONS_URI+"delete",gradeUri),
               new Link(Representation.SELF_REL_VALUE, gradeUri));
        }
        else if(grade.getAppealStatus() == AppealStatus.APPEALED){
            gradeRepresentation = new GradeRepresentation(grade,
             new Link(Representation.RELATIONS_URI+"update",appealUri));
        }
        else if(grade.getAppealStatus() == AppealStatus.ACCEPTED){
            gradeRepresentation = new GradeRepresentation(grade,
             new Link(Representation.SELF_REL_VALUE,gradeUri));
        }
        else if(grade.getAppealStatus() == AppealStatus.REJECTED){
            gradeRepresentation = new GradeRepresentation(grade,
             new Link(Representation.SELF_REL_VALUE,gradeUri));
        }
        else{
            throw new RuntimeException("Order Status not known");
        }
        return gradeRepresentation;
    }
    @Override
    public String toString(){
        StringWriter writer = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GradeRepresentation.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(this, writer);
        } catch (JAXBException ex) {
            throw new InvalidGradeException(ex);
        }
        return writer.toString();
    }
    public Grade getGrade(){
        return new Grade(this.student, this.score, this.feedback, this.appealStatus);
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
    public Link getDeletelLink() {
        return getLinkByName(RELATIONS_URI + "delete");
    }

    public Link getAppealLink() {
        return getLinkByName(RELATIONS_URI + "appeal");
    }

    public Link getUpdateLink() {
        //LOG.info("Retrieving the Update link ");
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
        //LOG.info("Retrieving the Self link ");
        return getLinkByName("self");
    }
    
}
