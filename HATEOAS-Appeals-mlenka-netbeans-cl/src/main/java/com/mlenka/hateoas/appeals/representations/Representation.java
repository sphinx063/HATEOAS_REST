/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlenka.hateoas.appeals.representations;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author sphinx
 */
public class Representation {
    public static final String RELATIONS_URI = "http://relations.mygrades.com/";
    public static final String MYGRADES_NAMESPACE = "http://schemas.mygrades.com";
    public static final String DAP_NAMESPACE = MYGRADES_NAMESPACE + "/dap";
    public static final String MYGRADES_MEDIA_TYPE = "application/vnd-cse564-appeals+xml";
    public static final String SELF_REL_VALUE = "self";
    @XmlElement(name = "link", namespace = DAP_NAMESPACE)
    protected List<Link> links;
    public Link getLinkByName(String uriName){
        if(uriName==null)
            return null;
        for(int i=0;i<links.size();i++){
            if(links.get(i).getRel().toLowerCase().equals(uriName.toLowerCase())){
                return links.get(i);
            }
        }
        return null;
    }
}
