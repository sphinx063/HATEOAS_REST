/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.resources;

import com.mlenka.hateoas.appeals.activities.CreateGradeActivity;
import com.mlenka.hateoas.appeals.activities.InvalidGradeException;
import com.mlenka.hateoas.appeals.activities.NoSuchGradeException;
import com.mlenka.hateoas.appeals.activities.ReadGradeActivity;
import com.mlenka.hateoas.appeals.activities.UpdateException;
import com.mlenka.hateoas.appeals.activities.UpdateGradeActivity;
import com.mlenka.hateoas.appeals.model.Grade;
import com.mlenka.hateoas.appeals.representations.GradeRepresentation;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

/**
 * REST Web Service
 *
 * @author sphinx
 */
@Path("grade")
public class GradeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GradeResource1
     */
    public GradeResource() {
    }
    @GET
    @Path("/{gradeId}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response getGrade(){
        Response response = null;
        try{
            GradeRepresentation gradeRepresentation = new ReadGradeActivity().getGradeByUri(new MyGradesUri(context.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(gradeRepresentation).build();
        }catch(NoSuchGradeException ge){
            response = Response.status(Response.Status.NOT_FOUND).build();
        }catch(Exception e){
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        return response;
    }
    @POST
    @Produces("application/vnd-cse564-appeals+xml")
    @Consumes("application/vnd-cse564-appeals+xml")
    public Response createGrade(String gradeContent){
        System.out.println("In create Grade");
        Response response = null;
        try {
            CreateGradeActivity createActivity = new CreateGradeActivity();
            GradeRepresentation gradeRepresentation = createActivity.create(GradeRepresentation.fromXmlString(gradeContent).getGrade(),new MyGradesUri(context.getRequestUri()));
            if(gradeRepresentation==null){
                System.out.println("what the fuck!!");
            }
            response = Response.created(gradeRepresentation.getUpdateLink().getUri()).entity(gradeRepresentation).build();
        } catch (InvalidGradeException ex) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
            //ex.printStackTrace();
        }catch(Exception e){
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }
    @PUT
    @Path("/{gradeId}")
    @Produces("application/vnd-cse564-appeals+xml")
    @Consumes("application/vnd-cse564-appeals+xml")
    public Response updateGrade(String gradeRepresentaion){
        Response response = null;
       
        try {
             MyGradesUri gradeUri = new MyGradesUri(context.getRequestUri());
             Grade modifiedGrade = GradeRepresentation.fromXmlString(gradeRepresentaion).getGrade();
             GradeRepresentation responseRepresentation = new UpdateGradeActivity().updateGrade(modifiedGrade,gradeUri);
             response = Response.status(Response.Status.OK).entity(responseRepresentation).build();
        } catch(InvalidGradeException ige) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }catch(NoSuchGradeException sge){
            response = Response.status(Response.Status.NOT_FOUND).build();
        }catch(UpdateException ue){
            response = Response.status(Response.Status.CONFLICT).build();
        }catch(Exception e){
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        return response;
    }
}
