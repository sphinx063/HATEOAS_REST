/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.representations;

import com.mlenka.hateoas.appeals.model.Appeal;
import com.mlenka.hateoas.appeals.model.AppealType;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sphinx
 */
@XmlRootElement(name = "receipt", namespace = Representation.MYGRADES_NAMESPACE)
public class ReceiptRepresentation extends Representation{
    private String appealComment;
    private String appealReply;
    private String followUp;
    private AppealType appealType;
    public ReceiptRepresentation(){
        
    }
    public ReceiptRepresentation(Appeal appeal,Link gradeLink){
        this.appealComment = appeal.getAppealComment();
        this.appealReply = appeal.getAppealReply();
        this.followUp = appeal.getFollowup();
        this.appealType = appeal.getAppealType();
        this.links = new ArrayList<Link>();
        this.links.add(gradeLink);
    }

    /**
     * @return the appealComment
     */
    @XmlElement(name="comment",namespace=Representation.MYGRADES_NAMESPACE)
    public String getAppealComment() {
        return appealComment;
    }

    /**
     * @param appealComment the appealComment to set
     */
    public void setAppealComment(String appealComment) {
        this.appealComment = appealComment;
    }
    public String toString(){
        try{
            JAXBContext context = JAXBContext.newInstance(ReceiptRepresentation.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(this,writer);
            return writer.toString();
            
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }
    public Link getGradeLink(){
        return getLinkByName(Representation.RELATIONS_URI+"grade");
    }
     public Link getUpdateLink(){
        return getLinkByName(Representation.RELATIONS_URI+"update");
    }

    /**
     * @return the appealReply
     */
     @XmlElement(name="reply",namespace=Representation.MYGRADES_NAMESPACE)
    public String getAppealReply() {
        return appealReply;
    }

    /**
     * @param appealReply the appealReply to set
     */
    public void setAppealReply(String appealReply) {
        this.appealReply = appealReply;
    }

    /**
     * @return the followUp
     */
    @XmlElement(name="followUp",namespace=Representation.MYGRADES_NAMESPACE)
    public String getFollowUp() {
        return followUp;
    }

    /**
     * @param followUp the followUp to set
     */
    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    /**
     * @return the appealType
     */
    @XmlElement(name="type",namespace=Representation.MYGRADES_NAMESPACE)
    public AppealType getAppealType() {
        return appealType;
    }

    /**
     * @param appealType the appealType to set
     */
    public void setAppealType(AppealType appealType) {
        this.appealType = appealType;
    }
     public Appeal getAppeal(){
        return new Appeal(this.appealComment,this.appealReply,this.followUp,this.appealType);
    }
    
}
