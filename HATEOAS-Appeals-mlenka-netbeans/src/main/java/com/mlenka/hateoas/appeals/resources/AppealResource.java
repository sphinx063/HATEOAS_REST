/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.resources;

import com.mlenka.hateoas.appeals.activities.AppealDeleteException;
import com.mlenka.hateoas.appeals.activities.ComposeAppealActivity;
import com.mlenka.hateoas.appeals.activities.ComposeReplyActivity;
import com.mlenka.hateoas.appeals.activities.SendAppealActivity;
import com.mlenka.hateoas.appeals.activities.NoSuchGradeException;
import com.mlenka.hateoas.appeals.activities.RemoveAppealActivity;
import com.mlenka.hateoas.appeals.activities.SendAcceptReplyActivity;
import com.mlenka.hateoas.appeals.activities.SendFollowUpActivity;
import com.mlenka.hateoas.appeals.activities.SendRejectReplyActivity;
import com.mlenka.hateoas.appeals.activities.UpdateException;
import com.mlenka.hateoas.appeals.model.Appeal;
import com.mlenka.hateoas.appeals.model.AppealType;
import com.mlenka.hateoas.appeals.representations.AppealRepresentation;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author sphinx
 */
@Path("appeal/{appealId}")
public class AppealResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AppealResource
     */
    public AppealResource() {
    }

    /**
     * PUT method for updating or creating an instance of AppealResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/vnd-cse564-appeals+xml")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response appeal(AppealRepresentation appealRepresentation) {
        System.out.println("Creating an appeal");
        Response response = null;
        try{
            MyGradesUri requestUri = new MyGradesUri(context.getRequestUri());
            Appeal appealItem= appealRepresentation.getAppeal();
            AppealType appealType = appealItem.getAppealType();
            if(appealType == AppealType.COMPOSE_APPEAL){
                AppealRepresentation responseRepresentation = new ComposeAppealActivity().compose(appealRepresentation.getAppeal(),requestUri);
                response = Response.created(context.getRequestUri()).entity(responseRepresentation).build();
            }
            else if(appealType == AppealType.COMPOSE_REPLY){
                AppealRepresentation responseRepresentation = new ComposeReplyActivity().compose(appealRepresentation.getAppeal(),requestUri);
                response = Response.created(context.getRequestUri()).entity(responseRepresentation).build();
            }
            else if(appealType == AppealType.SEND_APPEAL){
                AppealRepresentation responseRepresentation = new SendAppealActivity().appeal(appealRepresentation.getAppeal(),requestUri);
                response = Response.created(context.getRequestUri()).entity(responseRepresentation).build();
            }
            else if(appealType == AppealType.SEND_ACCEPT_REPLY){
                AppealRepresentation responseRepresentation = new SendAcceptReplyActivity().reply(appealRepresentation.getAppeal(),requestUri);
                response = Response.created(context.getRequestUri()).entity(responseRepresentation).build();
            }
            else if(appealType == AppealType.SEND_REJECT_REPLY){
                AppealRepresentation responseRepresentation = new SendRejectReplyActivity().reply(appealRepresentation.getAppeal(),requestUri);
                response = Response.created(context.getRequestUri()).entity(responseRepresentation).build();
            }
            else if(appealType == AppealType.FOLLOW_UP_APPEAL){
                AppealRepresentation responseRepresentation = new SendFollowUpActivity().followUp(appealRepresentation.getAppeal(),requestUri);
                response = Response.created(context.getRequestUri()).entity(responseRepresentation).build();
            }
            else{
                response = Response.status(Response.Status.NOT_FOUND).build();
            }
        }catch(NoSuchGradeException sge){
            response = Response.status(Response.Status.NOT_FOUND).build();
        }catch(UpdateException ue){
            response = Response.status(Response.Status.CONFLICT).build();
        }catch(Exception e){
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        System.out.println("Created an appeal activity");
        return response;
    }
    @DELETE
    @Produces("application/vnd.restbucks+xml")
    public Response deleteAppeal(){
        Response response = null;
        try{
            AppealRepresentation responseRepresentation = new RemoveAppealActivity().remove(new MyGradesUri(context.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(responseRepresentation).build();
        }catch(NoSuchGradeException soe){
            response = Response.status(Response.Status.NOT_FOUND).build();
        }catch(AppealDeleteException ade){
            response = Response.status(Response.Status.METHOD_NOT_ALLOWED).header("Allow", "GET").build();
        }catch(Exception e){
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

}
