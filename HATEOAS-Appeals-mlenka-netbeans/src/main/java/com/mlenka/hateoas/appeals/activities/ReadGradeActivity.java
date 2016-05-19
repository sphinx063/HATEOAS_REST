/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.activities;

import com.mlenka.hateoas.appeals.model.Grade;
import com.mlenka.hateoas.appeals.model.Identifier;
import com.mlenka.hateoas.appeals.repositories.GradeRepository;
import com.mlenka.hateoas.appeals.representations.GradeRepresentation;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;

/**
 *
 * @author sphinx
 */
public class ReadGradeActivity {
    public GradeRepresentation getGradeByUri(MyGradesUri gradeUri){
        GradeRepresentation gradeRepresentation = null;
        Identifier id = gradeUri.getId();
        Grade grade = GradeRepository.getGradeRepository().getGrade(id);
        if(grade!=null){
            return GradeRepresentation.createGradeRepresentation(grade,gradeUri);
        }
        else{
            throw new NoSuchGradeException();
        }
    }
}
