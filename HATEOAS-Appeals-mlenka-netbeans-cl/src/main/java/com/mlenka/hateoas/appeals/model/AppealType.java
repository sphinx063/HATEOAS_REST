/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.model;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author sphinx
 */
public enum AppealType {
    @XmlEnumValue(value="composeAppeal")
    COMPOSE_APPEAL,
    @XmlEnumValue(value="sendAppeal")
    SEND_APPEAL, 
    @XmlEnumValue(value="followUp")
    FOLLOW_UP_APPEAL,
    @XmlEnumValue(value="composeReply")
    COMPOSE_REPLY,
    @XmlEnumValue(value="sendReject")
    SEND_REJECT_REPLY,
    @XmlEnumValue(value="sendAccept")
    SEND_ACCEPT_REPLY, 
    @XmlEnumValue(value="abandon")
    ABANDON
}
