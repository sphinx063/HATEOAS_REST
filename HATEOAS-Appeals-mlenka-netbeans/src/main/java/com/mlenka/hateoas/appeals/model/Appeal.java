/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.model;

/**
 *
 * @author sphinx
 */
public class Appeal {
    private String appealComment;
    private String appealReply;
    private String followup;
    private AppealType appealType;
    public Appeal(){
        
    }
    public Appeal(String appealComment, String appealReply,String followup,AppealType appealType) {
        this.appealComment = appealComment;
        this.appealReply = appealReply;
        this.followup = followup;
        this.appealType = appealType;
    }
    

    /**
     * @return the appealComment
     */
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
     * @return the appealType
     */
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
