/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.activities;

import com.mlenka.hateoas.appeals.model.Appeal;
import com.mlenka.hateoas.appeals.model.AppealStatus;
import com.mlenka.hateoas.appeals.model.Grade;
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
public class RemoveAppealActivity {
    public AppealRepresentation remove(MyGradesUri appealUri){
        Identifier id = appealUri.getId();
        GradeRepository gradeRepository = GradeRepository.getGradeRepository();
        if(!gradeRepository.hasGrade(id)){
            throw new NoSuchGradeException();
        }
        Grade grade = gradeRepository.getGrade(id);
        if(AppealRepository.getAppealRepository().hasAppeal(id) && 
                (grade.getAppealStatus()==AppealStatus.APPEALED || grade.getAppealStatus()==AppealStatus.NOT_APPEALED)){
            Appeal appeal = AppealRepository.getAppealRepository().getAppeal(id);
            AppealRepository.getAppealRepository().removeAppeal(id);
            gradeRepository.getGrade(id).setAppealStatus(AppealStatus.NOT_APPEALED);
            MyGradesUri gradeUri = new MyGradesUri(appealUri.getBaseUri()+"/grade/"+id.toString());
            AppealRepresentation appealRepresentation = new AppealRepresentation(appeal,
                                                     new Link(Representation.RELATIONS_URI+"grade",gradeUri));
            return appealRepresentation;
        }
        else{
            throw new AppealDeleteException();
        }
    }
}
