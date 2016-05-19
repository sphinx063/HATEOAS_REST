/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.activities;

import com.mlenka.hateoas.appeals.model.AppealStatus;
import com.mlenka.hateoas.appeals.model.Identifier;
import com.mlenka.hateoas.appeals.repositories.AppealRepository;
import com.mlenka.hateoas.appeals.repositories.GradeRepository;
import com.mlenka.hateoas.appeals.representations.Link;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;
import com.mlenka.hateoas.appeals.representations.ReceiptRepresentation;
import com.mlenka.hateoas.appeals.representations.Representation;

/**
 *
 * @author sphinx
 */
public class ReadAppealReceiptActivity {
    public ReceiptRepresentation readAppeal(MyGradesUri appealUri){
        Identifier id = appealUri.getId();
        GradeRepository gradeRepository = GradeRepository.getGradeRepository();
        if(!gradeRepository.hasGrade(id)){
            throw new NoSuchGradeException();
        }
        MyGradesUri updateUri = new MyGradesUri(appealUri.getBaseUri()+"/appeal/"+id.toString());
        MyGradesUri gradeUri = new MyGradesUri(appealUri.getBaseUri()+"/grade/"+id.toString());
        if(AppealRepository.getAppealRepository().hasAppeal(id) 
                && (gradeRepository.getGrade(id).getAppealStatus() == AppealStatus.NOT_APPEALED ||
                gradeRepository.getGrade(id).getAppealStatus() == AppealStatus.APPEALED)){
            return new ReceiptRepresentation(AppealRepository.getAppealRepository().getAppeal(id),new Link(Representation.RELATIONS_URI+"update",updateUri));
        }
        else{
            return new ReceiptRepresentation(AppealRepository.getAppealRepository().getAppeal(id),new Link(Representation.RELATIONS_URI+"grade",gradeUri));
        }
    }
    
}
