/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.representations;

import com.mlenka.hateoas.appeals.model.Identifier;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author sphinx
 */
public class MyGradesUri {
    private URI uri;
    public MyGradesUri(String uri){
        try{
            this.uri = new URI(uri);
        }catch(URISyntaxException ux){
            throw new RuntimeException(ux);
        }
    }
    public MyGradesUri(URI uri){
        this.uri = uri;
    }
    public MyGradesUri(URI uri, Identifier identifier) {
        this(uri.toString() + "/" + identifier.toString());
    }

    public Identifier getId() {
        String path = uri.getPath();
        return new Identifier(path.substring(path.lastIndexOf("/") + 1, path.length()));
    }

    public URI getFullUri() {
        return uri;
    }
    
    @Override
    public String toString() {
        return uri.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MyGradesUri) {
            return ((MyGradesUri)obj).uri.equals(uri);
        }
        return false;
    }

    public String getBaseUri() {
        String uriString = uri.toString();
        String baseURI   = uriString.substring(0, uriString.lastIndexOf("webresources/")+"webresources".length());
        
        return baseURI;
    }
}
