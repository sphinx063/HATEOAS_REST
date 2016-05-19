/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.resources;

import com.mlenka.hateoas.appeals.activities.NoSuchGradeException;
import com.mlenka.hateoas.appeals.activities.ReadAppealReceiptActivity;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;
import com.mlenka.hateoas.appeals.representations.ReceiptRepresentation;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("receipt")
public class ReceiptResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ReceiptResource
     */
    public ReceiptResource() {
    }

    /**
     * Retrieves representation of an instance of com.mlenka.hateoas.appeals.resources.ReceiptResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{gradeId}")
    @Produces("application/vnd-cse564-appeals+xml")
    public Response getAppealReceipt() {
        Response response =null;
        try{
            ReceiptRepresentation responseRep = new ReadAppealReceiptActivity().readAppeal(new MyGradesUri(context.getRequestUri()));
            response = Response.status(Response.Status.OK).entity(responseRep).build();
        }catch(NoSuchGradeException sge){
            response = Response.status(Response.Status.NOT_FOUND).build();
        }catch(Exception e){
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    /**
     * PUT method for updating or creating an instance of ReceiptResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
