/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.client;

import com.mlenka.hateoas.appeals.model.Appeal;
import com.mlenka.hateoas.appeals.model.AppealType;
import com.mlenka.hateoas.appeals.model.Grade;
import com.mlenka.hateoas.appeals.model.Student;
import com.mlenka.hateoas.appeals.representations.AppealRepresentation;
import com.mlenka.hateoas.appeals.representations.GradeRepresentation;
import com.mlenka.hateoas.appeals.representations.Link;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;
import com.mlenka.hateoas.appeals.representations.ReceiptRepresentation;
import com.mlenka.hateoas.appeals.representations.Representation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import java.net.URI;

/**
 *
 * @author sphinx
 */
public class Test {
    private static final String MYGRADES_MEDIA_TYPE = "application/vnd-cse564-appeals+xml";
    private static final String ENTRY_POINT_URI = "http://localhost:8080/HATEOAS-Appeals-mlenka-netbeans/webresources/grade";
    
    public static void main(String[] args)throws Exception{
        URI enrtyUri = new URI(ENTRY_POINT_URI);
        System.out.println("%%%%%%%%%%%%%%%%%%%%Executing Happy Case%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        executeHappyCase(enrtyUri);
        System.out.println("%%%%%%%%%%%%%%%%%%%%Executing Abandon Case%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        executeAbandonCase(enrtyUri);
        System.out.println("%%%%%%%%%%%%%%%%%%%%Executing Forgotten Case%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        executeForgottenCase(enrtyUri);
        System.out.println("%%%%%%%%%%%%%%%%%%%%Executing Bad Start Case%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        executeBadStartCase(new URI(ENTRY_POINT_URI+"bad"));
        System.out.println("%%%%%%%%%%%%%%%%%%%%Executing Bad ID Case%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        executeBadIDCase(enrtyUri);
        
    }

    private static void executeHappyCase(URI inputUri)throws Exception{
        System.out.println("1. Create Random Grade");
        System.out.println("Creating Grade through POST with "+inputUri.toString());
        Grade grade = new Grade(new Student("345","Carl"),67 , "Average");
        Client client = new Client();
        GradeRepresentation gradeRepresentation = client.resource(inputUri).accept(MYGRADES_MEDIA_TYPE).type(MYGRADES_MEDIA_TYPE).post(GradeRepresentation.class, new ClientGrade(grade));
        System.out.println(String.format("Grade created at [%s]", gradeRepresentation.getSelfLink().getUri().toString()));
        
        System.out.println("2. Update Grade");
        System.out.println(String.format("About to update grade resource at [%s] via PUT",gradeRepresentation.getUpdateLink().getUri().toString()));
        Link updateLink = gradeRepresentation.getUpdateLink();
        Grade newGrade = new Grade(new Student("345","Carl"),90,"Excellent");
        GradeRepresentation newGradeRep = new GradeRepresentation(newGrade);
        GradeRepresentation updatedRep = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).put(GradeRepresentation.class, newGradeRep);
        System.out.println(String.format("Grade updated at [%s]", updatedRep.getSelfLink().getUri().toString()));
        
        System.out.println("3. Read Grade");
        System.out.println(String.format("About to Read Grade resource at [%s] via GET",updatedRep.getSelfLink().getUri().toString()));
        Link readLink = updatedRep.getSelfLink();
        GradeRepresentation readRep = client.resource(readLink.getUri()).accept(readLink.getMediaType()).get(GradeRepresentation.class);
        Grade readGrade = readRep.getGrade();
        System.out.println("Studnet Name : "+readGrade.getStudent().getName());
        System.out.println("Studnet Id : "+readGrade.getStudent().getId());
        System.out.println("Student Score : "+readGrade.getScore());
        System.out.println("Studnet feedback : "+readGrade.getFeedback());
        System.out.println("Studnet Appeal Status : "+readGrade.getAppealStatus().toString());
        
        
        System.out.println("4. Compose Appeal for Grade");
        System.out.println(String.format("About to create an appeal resource at [%s] via PUT",gradeRepresentation.getAppealLink().getUri().toString()));
        Link appealLink = gradeRepresentation.getAppealLink();
        System.out.println("Media Type : = "+appealLink.getMediaType());
        Appeal appeal = new Appeal("I did not do any mistake","","",AppealType.COMPOSE_APPEAL);
        AppealRepresentation appealRepresentation = client.resource(appealLink.getUri()).accept(appealLink.getMediaType()).type(appealLink.getMediaType()).put(AppealRepresentation.class,new AppealRepresentation(appeal));
        System.out.println(String.format("Appeal sent, receipt at [%s]", appealRepresentation.getReceiptLink().getUri().toString()));
        System.out.println("Appeal Comment:= "+appealRepresentation.getAppeal().getAppealComment());
        System.out.println("Appeal Reply:= "+appealRepresentation.getAppeal().getAppealReply());
        System.out.println("Appeal Type:= "+appealRepresentation.getAppeal().getAppealType().toString());
        
        System.out.println("5. Rephrase Appeal Message for Grade");
        System.out.println(String.format("About to update an appeal resource at [%s] via PUT",appealRepresentation.getUpdateLink().getUri().toString()));
        Link appealUpdateLink = appealRepresentation.getUpdateLink();
        Appeal updatedAppeal = new Appeal("I did do mistake in q.1","","",AppealType.SEND_APPEAL);
        AppealRepresentation updatedAppealRep = client.resource(appealUpdateLink.getUri()).accept(appealUpdateLink.getMediaType()).type(appealUpdateLink.getMediaType()).put(AppealRepresentation.class,new AppealRepresentation(updatedAppeal));
        System.out.println(String.format("Appeal sent, receipt at [%s]", updatedAppealRep.getReceiptLink().getUri().toString()));
        System.out.println("Appeal Comment:= "+updatedAppealRep.getAppeal().getAppealComment());
        System.out.println("Appeal Reply:= "+updatedAppealRep.getAppeal().getAppealReply());
        System.out.println("Appeal Type:= "+updatedAppealRep.getAppeal().getAppealType().toString());
        
        System.out.println("6. Read Appeal Status");
        System.out.println(String.format("About to Read Grade resource at [%s] via GET",updatedAppealRep.getGradeLink().getUri().toString()));
        Link readStatusLink = updatedAppealRep.getGradeLink();
        GradeRepresentation readStatusGrade = client.resource(readStatusLink.getUri()).accept(readStatusLink.getMediaType()).get(GradeRepresentation.class);
        Grade readStatus = readStatusGrade.getGrade();
        System.out.println("Studnet Name : "+readStatus.getStudent().getName());
        System.out.println("Studnet Id : "+readStatus.getStudent().getId());
        System.out.println("Student Score : "+readStatus.getScore());
        System.out.println("Studnet feedback : "+readStatus.getFeedback());
        System.out.println("Studnet Appeal Status : "+readStatus.getAppealStatus().toString());
        
        System.out.println("7. Reply appeal for Grade");
        System.out.println(String.format("About to send reply to an appeal resource at [%s] via PUT",appealRepresentation.getUpdateLink().getUri().toString()));
        Link replyLink = appealRepresentation.getUpdateLink();
        Appeal replyAppeal = new Appeal("I","Your appeal is absurd","",AppealType.SEND_REJECT_REPLY);
        AppealRepresentation replyAppealRep = client.resource(replyLink.getUri()).accept(replyLink.getMediaType()).type(replyLink.getMediaType()).put(AppealRepresentation.class,new AppealRepresentation(replyAppeal));
        System.out.println(String.format("Appeal sent, receipt at [%s]", replyAppealRep.getReceiptLink().getUri().toString()));
        System.out.println("Appeal Comment:= "+replyAppealRep.getAppeal().getAppealComment());
        System.out.println("Appeal Reply:= "+replyAppealRep.getAppeal().getAppealReply());
        System.out.println("Appeal Type:= "+replyAppealRep.getAppeal().getAppealType().toString());
        
        
        System.out.println("7. Read Appeal Status");
        System.out.println(String.format("About to Read Grade resource at [%s] via GET",updatedAppealRep.getGradeLink().getUri().toString()));
        Link readStatusLink1 = updatedAppealRep.getGradeLink();
        GradeRepresentation readStatusGrade1 = client.resource(readStatusLink1.getUri()).accept(readStatusLink1.getMediaType()).get(GradeRepresentation.class);
        Grade readStatus1 = readStatusGrade1.getGrade();
        System.out.println("Studnet Name : "+readStatus1.getStudent().getName());
        System.out.println("Studnet Id : "+readStatus1.getStudent().getId());
        System.out.println("Student Score : "+readStatus1.getScore());
        System.out.println("Studnet feedback : "+readStatus1.getFeedback());
        System.out.println("Studnet Appeal Status : "+readStatus1.getAppealStatus().toString());
        
    }
    private static void executeAbandonCase(URI inputUri)throws Exception{
         System.out.println("1. Create Random Grade");
        System.out.println("Creating Grade through POST with "+inputUri.toString());
        Grade grade = new Grade(new Student("890","James"),85 , "Good");
        Client client = new Client();
        GradeRepresentation gradeRepresentation = client.resource(inputUri).accept(MYGRADES_MEDIA_TYPE).type(MYGRADES_MEDIA_TYPE).post(GradeRepresentation.class, new ClientGrade(grade));
        System.out.println(String.format("Grade created at [%s]", gradeRepresentation.getSelfLink().getUri().toString()));
        System.out.println("2. Sending Appeal Message for Grade");
        System.out.println(String.format("About to update an appeal resource at [%s] via PUT",gradeRepresentation.getUpdateLink().getUri().toString()));
        Link appealUpdateLink = gradeRepresentation.getAppealLink();
        Appeal updatedAppeal = new Appeal("I did do mistake in q.1","","",AppealType.SEND_APPEAL);
        AppealRepresentation updatedAppealRep = client.resource(appealUpdateLink.getUri()).accept(appealUpdateLink.getMediaType()).type(appealUpdateLink.getMediaType()).put(AppealRepresentation.class,new AppealRepresentation(updatedAppeal));
        System.out.println(String.format("Appeal sent, receipt at [%s]", updatedAppealRep.getReceiptLink().getUri().toString()));
        System.out.println("Appeal Comment:= "+updatedAppealRep.getAppeal().getAppealComment());
        System.out.println("Appeal Reply:= "+updatedAppealRep.getAppeal().getAppealReply());
        System.out.println("Appeal Type:= "+updatedAppealRep.getAppeal().getAppealType().toString());
        System.out.println("3. Abandon Appeal.");
        System.out.println(String.format("About to  delete appeal resource at [%s] via DELETE",appealUpdateLink.getUri().toString()));
        ClientResponse clientResponse = client.resource(appealUpdateLink.getUri()).delete(ClientResponse.class);
        if(clientResponse.getStatus() == 200){
            AppealRepresentation responseRepresentation = clientResponse.getEntity(AppealRepresentation.class);
            System.out.println("Appeal Comment:= "+responseRepresentation.getAppeal().getAppealComment());
            System.out.println("Appeal Reply:= "+responseRepresentation.getAppeal().getAppealReply());
            System.out.println("Appeal Type:= "+responseRepresentation.getAppeal().getAppealType().toString()); 
            System.out.println(String.format("Appeal deleted, grade at [%s]", responseRepresentation.getGradeLink().getUri().toString()));
        }
        else{
            System.out.println("Could Not Delete Appeal");
        }
    }
    private static void executeForgottenCase(URI inputUri)throws Exception{
        System.out.println("1. Create Random Grade");
        System.out.println("Creating Grade through POST with "+inputUri.toString());
        Grade grade = new Grade(new Student("900","Sam"),97 , "Excellent");
        Client client = new Client();
        GradeRepresentation gradeRepresentation = client.resource(inputUri).accept(MYGRADES_MEDIA_TYPE).type(MYGRADES_MEDIA_TYPE).post(GradeRepresentation.class, new ClientGrade(grade));
        System.out.println(String.format("Grade created at [%s]", gradeRepresentation.getSelfLink().getUri().toString()));
        System.out.println("2. Sending Appeal Message for Grade");
        System.out.println(String.format("About to update an appeal resource at [%s] via PUT",gradeRepresentation.getUpdateLink().getUri().toString()));
        Link appealUpdateLink = gradeRepresentation.getAppealLink();
        Appeal updatedAppeal = new Appeal("Please review question 2.","","",AppealType.SEND_APPEAL);
        AppealRepresentation updatedAppealRep = client.resource(appealUpdateLink.getUri()).accept(appealUpdateLink.getMediaType()).type(appealUpdateLink.getMediaType()).put(AppealRepresentation.class,new AppealRepresentation(updatedAppeal));
        System.out.println(String.format("Appeal sent, receipt at [%s]", updatedAppealRep.getReceiptLink().getUri().toString()));
        System.out.println("Appeal Comment:= "+updatedAppealRep.getAppeal().getAppealComment());
        System.out.println("Appeal Reply:= "+updatedAppealRep.getAppeal().getAppealReply());
        System.out.println("Appeal Type:= "+updatedAppealRep.getAppeal().getAppealType().toString());
        System.out.println("3. Follow Up Appeal.");
        System.out.println(String.format("About to  follow up appeal resource at [%s] via PUT",appealUpdateLink.getUri().toString()));
        //Client client = new Client();
        Appeal appeal = new Appeal("","","Please process my appeal", AppealType.FOLLOW_UP_APPEAL);
        AppealRepresentation updatedAppealRep1 = client.resource(appealUpdateLink.getUri()).accept(MYGRADES_MEDIA_TYPE).type(MYGRADES_MEDIA_TYPE).put(AppealRepresentation.class,new AppealRepresentation(appeal));
        System.out.println(String.format("Followed Up Appeal , receipt at [%s]", updatedAppealRep1.getReceiptLink().getUri().toString()));
        System.out.println("Appeal Comment:= "+updatedAppealRep1.getAppeal().getAppealComment());
        System.out.println("Appeal Reply:= "+updatedAppealRep1.getAppeal().getAppealReply());
        System.out.println("Appeal FollowUp:= "+updatedAppealRep1.getAppeal().getFollowup());
        System.out.println("Appeal Type:= "+updatedAppealRep1.getAppeal().getAppealType().toString());
        
    }
    private static void executeBadStartCase(URI inputUri)throws Exception{
        System.out.println("1. Bad Start URI");
        System.out.println(String.format("About to  create a grade resource with BAD Uri at [%s] via POST",inputUri.toString()));
        Client client = new Client();
        Grade grade = new Grade(new Student("898","Jack"),75 , "Average");
        ClientResponse clientResponse = client.resource(inputUri).accept(MYGRADES_MEDIA_TYPE).type(MYGRADES_MEDIA_TYPE).post(ClientResponse.class, new GradeRepresentation(grade));
        System.out.println(String.format("Grade creation failed with Bad Uri at [%s] and status code [%d] via POST",inputUri.toString(),clientResponse.getStatus()));
    }
    private static void executeBadIDCase(URI inputUri)throws Exception{
        System.out.println("1. Create Random Grade");
        System.out.println("Creating Grade through POST with "+inputUri.toString());
        Grade grade = new Grade(new Student("678","Alex"),78 , "Poor");
        Client client = new Client();
        GradeRepresentation gradeRepresentation = client.resource(inputUri).accept(MYGRADES_MEDIA_TYPE).type(MYGRADES_MEDIA_TYPE).post(GradeRepresentation.class, new ClientGrade(grade));
        System.out.println(String.format("Grade created at [%s]", gradeRepresentation.getSelfLink().getUri().toString()));
        System.out.println("2. Bad Appeal ID");
        URI appealUri = gradeRepresentation.getAppealLink().getUri();
        String badIdUri = new MyGradesUri(appealUri).getBaseUri()+"/appeal/"+"345badId";
        System.out.println(String.format("About to  follow up appeal resource at Bad uri [%s] via PUT",badIdUri));
        //Client client = new Client();
        Appeal appeal = new Appeal("","","Please process my appeal", AppealType.FOLLOW_UP_APPEAL);
        ClientResponse clientResponse= client.resource(new URI(badIdUri)).accept(MYGRADES_MEDIA_TYPE).type(MYGRADES_MEDIA_TYPE).put(ClientResponse.class,new AppealRepresentation(appeal));
        System.out.println(String.format("Follow Up Appeal Failed at [%s] with status code [%d] ",badIdUri,clientResponse.getStatus() ));
        
        
    }
}
