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
import com.mlenka.hateoas.appeals.representations.Link;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;
import com.mlenka.hateoas.appeals.representations.Representation;

/**
 *
 * @author sphinx
 */
public class CreateGradeActivity {
    public GradeRepresentation create(Grade grade, MyGradesUri uri){
       grade.setAppealStatus(AppealStatus.NOT_APPEALED);
       Identifier id = GradeRepository.getGradeRepository().saveGrade(grade);
       MyGradesUri gradeUri = new MyGradesUri(uri.getBaseUri()+"/grade/"+id.toString());
       MyGradesUri appealUri = new MyGradesUri(uri.getBaseUri()+"/appeal/"+id.toString());
       GradeRepresentation gradeRepresentation = new GradeRepresentation(grade,
               new Link(Representation.RELATIONS_URI+"appeal",appealUri),
               new Link(Representation.RELATIONS_URI+"update",gradeUri),
               new Link(Representation.RELATIONS_URI+"delete",gradeUri),
               new Link(Representation.SELF_REL_VALUE, gradeUri));
       return gradeRepresentation;
    }
            
}
