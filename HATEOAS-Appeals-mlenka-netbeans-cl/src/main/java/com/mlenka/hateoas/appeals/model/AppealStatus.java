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
public enum AppealStatus {
    @XmlEnumValue(value="not_appealed")
    NOT_APPEALED,
    @XmlEnumValue(value="appealed")
    APPEALED, 
    @XmlEnumValue(value="accepted")
    ACCEPTED, 
    @XmlEnumValue(value="rejected")
    REJECTED
}
