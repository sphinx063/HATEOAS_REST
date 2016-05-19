/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.representations;

import com.mlenka.hateoas.appeals.model.*;
import com.mlenka.hateoas.appeals.representations.MyGradesUri;
import com.mlenka.hateoas.appeals.representations.Representation;
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sphinx
 */
@XmlRootElement(namespace=Representation.DAP_NAMESPACE)
public class Link {
    @XmlAttribute(name="rel")
    private String rel;
    @XmlAttribute(name="uri")
    private String uri;
    @XmlAttribute(name="mediaType")
    private String mediaType;
    public Link(){
        
    }
    public Link(String rel,MyGradesUri uri,String mediaType){
        this.rel = rel;
        this.uri = uri.getFullUri().toString();
        this.mediaType = mediaType;
    }
    public String getRel(){
        return this.rel;
    }
    public Link(String name, MyGradesUri uri) {
        this(name, uri, Representation.MYGRADES_MEDIA_TYPE);
    }
    public URI getUri() {
        
        try {
            URI local_uri = new URI(uri);
            return local_uri;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMediaType() {
        return mediaType;
    }
}
