/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.activities;

import com.mlenka.hateoas.appeals.model.Appeal;
import com.mlenka.hateoas.appeals.model.AppealStatus;
import com.mlenka.hateoas.appeals.model.Identifier;
import com.mlenka.hateoas.appeals.repositories.AppealRepository;
import com.mlenka.hateoas.appeals.repositories.GradeRepository;
import com.mlenka.hateoas.appeals.representations.AppealRepresentation;
import com.mlenka.hateoas.appeals.representations.Link;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;
import com.mlenka.hateoas.appeals.representations.Representation;

/**
 *
 * @author sphinx
 */
public class SendFollowUpActivity {
    public AppealRepresentation followUp(Appeal appealItem,MyGradesUri appealUri){
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&");
        Identifier id = appealUri.getId();
        if(!GradeRepository.getGradeRepository().hasGrade(id)){
            System.out.println("No apeal item found");
            throw new NoSuchGradeException();
        }
        if(AppealRepository.getAppealRepository().hasAppeal(id) 
                && GradeRepository.getGradeRepository().getGrade(id).getAppealStatus() != AppealStatus.APPEALED){
            System.out.print("Not allowed to follow up");
            throw new UpdateException();
        }
        
        AppealRepository.getAppealRepository().storeFollowUp(id,appealItem);
        MyGradesUri gradeUri = new MyGradesUri(appealUri.getBaseUri()+"/grade/"+id.toString());
        MyGradesUri receiptUri = new MyGradesUri(appealUri.getBaseUri()+"/receipt/"+id.toString());
        AppealRepresentation appealRepresentation = new AppealRepresentation( AppealRepository.getAppealRepository().getAppeal(id),
                                                     new Link(Representation.RELATIONS_URI+"grade",gradeUri),
                                                      new Link(Representation.RELATIONS_URI+"receipt",receiptUri));
        return appealRepresentation;
    }
    
}
