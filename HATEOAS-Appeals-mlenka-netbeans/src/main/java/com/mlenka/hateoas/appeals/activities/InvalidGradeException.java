/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.activities;

/**
 *
 * @author sphinx
 */
public class InvalidGradeException extends RuntimeException {
    public InvalidGradeException(){
        
    }
    public InvalidGradeException(Exception ex){
        super(ex);
    }
}
