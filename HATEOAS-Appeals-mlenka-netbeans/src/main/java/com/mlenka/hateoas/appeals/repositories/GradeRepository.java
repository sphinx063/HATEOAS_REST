/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.repositories;

import com.mlenka.hateoas.appeals.model.Grade;
import com.mlenka.hateoas.appeals.model.Identifier;
import java.util.HashMap;

/**
 *
 * @author sphinx
 */
public class GradeRepository {
    private static final GradeRepository gradeRepository = new GradeRepository();
    private static HashMap<String,Grade> gradeStore = new HashMap<String,Grade>();
    public GradeRepository(){
        
    }
    public static GradeRepository getGradeRepository(){
        return gradeRepository;
    }
    public Identifier saveGrade(Grade grade){
        Identifier id = new Identifier();
        gradeStore.put(id.toString(),grade);
        return id;
    }
    public void updateGrade(Identifier id, Grade grade){
        gradeStore.put(id.toString(), grade);
    }
    public Grade getGrade(Identifier id){
        return gradeStore.get(id.toString());
    }    
    public boolean hasGrade(Identifier id){
        return gradeStore.containsKey(id.toString());
    }
    public void removeGrade(Identifier id){
        gradeStore.remove(id.toString());
    }
    public synchronized void clear() {
        gradeStore = new HashMap<>();
    }

    public int size() {
        return gradeStore.size();
    }
}
