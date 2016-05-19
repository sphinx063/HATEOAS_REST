/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.repositories;

import com.mlenka.hateoas.appeals.model.Appeal;
import com.mlenka.hateoas.appeals.model.Identifier;
import java.util.HashMap;

/**
 *
 * @author sphinx
 */
public class AppealRepository {
    private static AppealRepository appealRepository = new AppealRepository();
    private HashMap<String,Appeal> appealStore = new HashMap<String,Appeal>();
    
    public static AppealRepository getAppealRepository(){
        return appealRepository;
    }
    private AppealRepository(){
        
    }
    public Identifier storeAppeal(Appeal appeal){
        Identifier id = new Identifier();
        appealStore.put(id.toString(), appeal);
        return id;
    }
    public Identifier storeAppeal(Identifier id, Appeal appeal){
        appealStore.put(id.toString(), appeal);
        return id;
    }
    public Appeal getAppeal(Identifier id){
        return appealStore.get(id.toString());
    }
    public Appeal removeAppeal(Identifier id){
        return appealStore.remove(id.toString());
    }
    public boolean hasAppeal(Identifier id){
        return appealStore.containsKey(id.toString());
    }
    public Identifier storeReply(Identifier id,Appeal appeal){
        appealStore.get(id.toString()).setAppealReply(appeal.getAppealReply());
        appealStore.get(id.toString()).setAppealType(appeal.getAppealType());
        return id;
        //appealStore.put(id.toString(), appeal);
    }
    public Identifier storeFollowUp(Identifier id,Appeal appeal){
        appealStore.get(id.toString()).setFollowup(appeal.getFollowup());
        appealStore.get(id.toString()).setAppealType(appeal.getAppealType());
        return id;
        //appealStore.put(id.toString(), appeal);
    }
}
