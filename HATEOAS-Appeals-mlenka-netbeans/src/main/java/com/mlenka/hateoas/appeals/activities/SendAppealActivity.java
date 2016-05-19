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
public class SendAppealActivity {
    public AppealRepresentation appeal(Appeal appealItem,MyGradesUri appealUri){
        Identifier id = appealUri.getId();
        if(!GradeRepository.getGradeRepository().hasGrade(id)){
            System.out.println("No apeal item found");
            throw new NoSuchGradeException();
        }
        if(AppealRepository.getAppealRepository().hasAppeal(id) 
                && GradeRepository.getGradeRepository().getGrade(id).getAppealStatus() != AppealStatus.NOT_APPEALED){
            System.out.print("Already appealed");
            throw new UpdateException();
        }
        
        GradeRepository.getGradeRepository().getGrade(id).setAppealStatus(AppealStatus.APPEALED);
        AppealRepository.getAppealRepository().storeAppeal(id,appealItem);
        MyGradesUri gradeUri = new MyGradesUri(appealUri.getBaseUri()+"/grade/"+id.toString());
        MyGradesUri receiptUri = new MyGradesUri(appealUri.getBaseUri()+"/receipt/"+id.toString());
        MyGradesUri followUpUri = new MyGradesUri(appealUri.getBaseUri()+"/appeal/"+id.toString());
        AppealRepresentation appealRepresentation = new AppealRepresentation(appealItem,
                                                     new Link(Representation.RELATIONS_URI+"grade",gradeUri),
                                                      new Link(Representation.RELATIONS_URI+"receipt",receiptUri),
                                                        new Link(Representation.RELATIONS_URI+"followup",followUpUri));
        return appealRepresentation;
    }
    
}
