/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.activities;

import com.mlenka.hateoas.appeals.model.AppealStatus;
import com.mlenka.hateoas.appeals.model.Grade;
import com.mlenka.hateoas.appeals.model.Identifier;
import com.mlenka.hateoas.appeals.repositories.GradeRepository;
import com.mlenka.hateoas.appeals.representations.GradeRepresentation;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;

/**
 *
 * @author sphinx
 */
public class UpdateGradeActivity {
    
    public GradeRepresentation updateGrade(Grade grade,MyGradesUri gradeUri){
        Identifier id = gradeUri.getId();
        GradeRepository gradeStore = GradeRepository.getGradeRepository();
        if(!gradeStore.hasGrade(id)){
            throw new NoSuchGradeException();
        }
        Grade gradeItem = gradeStore.getGrade(id);
        if(canGradeBeChanged(id)){
            gradeItem.setAppealStatus(grade.getAppealStatus());
            gradeItem.setFeedback(grade.getFeedback());
            gradeItem.setScore(grade.getScore());
            gradeStore.updateGrade(id, gradeItem);
            return GradeRepresentation.createGradeRepresentation(gradeItem, gradeUri);
        }
        else{
           throw new UpdateException();
        }
    }
    private boolean canGradeBeChanged(Identifier id){
        Grade currentGrade = GradeRepository.getGradeRepository().getGrade(id);
        if(currentGrade.getAppealStatus() == AppealStatus.ACCEPTED || currentGrade.getAppealStatus() == AppealStatus.REJECTED)
            return false;
        else
            return true;
    }
    
}
