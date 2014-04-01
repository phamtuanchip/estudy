package org.estudy.webservice;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.services.security.Identity;

/**
 * Created with IntelliJ IDEA.
 * User: pham
 * Date: 11/4/13
 * Time: 11:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WebService extends ResourceContainer {
	 
    public Response checkPermission(Identity currentIdentity) throws Exception;

    public Response getQuestions() throws Exception;

    public Response getAnswer(@PathParam("questionid") String questionid )throws Exception;
    
    public Response getLessons() throws Exception ;
}
