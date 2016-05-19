/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.representations;

import com.mlenka.hateoas.appeals.model.Appeal;
import com.mlenka.hateoas.appeals.model.AppealType;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sphinx
 */
@XmlRootElement(name="appeal",namespace=Representation.MYGRADES_NAMESPACE)
public class AppealRepresentation extends Representation{
    private String appealComment;
    private String appealReply;
    private String followup;
    private AppealType appealType;
    public AppealRepresentation() {
    }

    public AppealRepresentation(Appeal appeal, Link... links) {
        this.appealComment = appeal.getAppealComment();
        this.appealReply = appeal.getAppealReply();
        this.followup = appeal.getFollowup();
        this.appealType = appeal.getAppealType();
        this.links = Arrays.asList(links);
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
    public Appeal getAppeal(){
        return new Appeal(this.appealComment,this.appealReply,this.followup, this.getAppealType());
    }
    
    public Link getReceiptLink() {
        return getLinkByName(Representation.RELATIONS_URI + "receipt");
    }
    
    public Link getGradeLink() {
        return getLinkByName(Representation.RELATIONS_URI + "grade");
    }
     public Link getUpdateLink() {
        return getLinkByName(Representation.RELATIONS_URI + "update");
    }
    public Link getFollowUpLink() {
        return getLinkByName(Representation.RELATIONS_URI + "followup");
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

    /**
     * @return the followup
     */
    @XmlElement(name="followup",namespace=Representation.MYGRADES_NAMESPACE)
    public String getFollowup() {
        return followup;
    }

    /**
     * @param followup the followup to set
     */
    public void setFollowup(String followup) {
        this.followup = followup;
    }
 
    
}
