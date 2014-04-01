
package org.estudy.webservice;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.estudy.learning.model.ECategory;
import org.estudy.learning.model.EQuestion;
import org.estudy.learning.model.ESession;
import org.estudy.learning.storage.DataStorage;
import org.estudy.learning.storage.impl.JcrDataStorage;
import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;

@Path("/estudy/api")
public class EStudyWebservice implements ResourceContainer{
	public final static String PRIVATE = "/private";
	public final static String BASE_URL = "/estudy/api";
	public final static String BASE_RSS_URL = BASE_URL + "/feed";
	public final static String BASE_EVENT_URL = BASE_URL + "/event";
	final public static String BASE_URL_PUBLIC = "/estudy/api/subscribe/";
	final public static String BASE_URL_PRIVATE = PRIVATE + BASE_URL + "/";
	private Log log = ExoLogger.getExoLogger("estudy.webservice");

	static CacheControl cc = new CacheControl();
	static {
		cc.setNoCache(true);
		cc.setNoStore(true);
	}

	private DataStorage dataService = null;
	private Object getJcrDataStorage() {
		dataService = (DataStorage)ExoContainerContext.getCurrentContainer()
				.getComponentInstanceOfType(JcrDataStorage.class);
		if(dataService == null){
			return Response.status(HTTPStatus.UNAVAILABLE).cacheControl(cc).build();
		}
		return dataService;
	}

	public EStudyWebservice() {
		dataService = (DataStorage)ExoContainerContext.getCurrentContainer()
				.getComponentInstanceOfType(JcrDataStorage.class);
	}



	@POST
	@RolesAllowed("users")
	@Path("/checkPermission/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkPermission(String currentIdentity) throws Exception {
		try {
			Identity instance = ConversationState.getCurrent().getIdentity();
			if(instance == null || !currentIdentity.equals(instance.getUserId())) Response.status(HTTPStatus.UNAUTHORIZED).cacheControl(cc).build();
			return Response.status(HTTPStatus.ACCEPTED).cacheControl(cc).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(HTTPStatus.INTERNAL_ERROR).cacheControl(cc).build();
		}


	}

	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/categories/{datatype}")
	public Response getCategories() throws Exception {
		Collection<ECategory> question = dataService.getCategories();
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}
	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/categories/{id}/{datatype}")
	public Response getCategory(@PathParam("id") String id) throws Exception {
		ECategory question = dataService.getCategory(id);
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}

	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/{datatype}")
	public Response getQuestions() throws Exception {
		Collection<EQuestion> question = dataService.getQuestions();
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}
	
	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/{id}/{datatype}")
	public Response getQuestionByid(@PathParam("id") String id) throws Exception {
		EQuestion question = dataService.getQuestion(id);
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}

	@POST
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/{datatype}")
	public Response createQuestion(EQuestion q) throws Exception {
		EQuestion question = dataService.saveQuestion(q, true);
		return Response.ok(question, MediaType.APPLICATION_JSON).status(HTTPStatus.CREATED).cacheControl(cc).build();
	}
	@PUT
	@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/id/{datatype}")
	public Response upcateQuestion(@PathParam("id") String id ,EQuestion q) throws Exception {
		EQuestion question = dataService.saveQuestion(q, false);
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}
	@DELETE
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/{id}/{datatype}")
	public Response deleteQuestion(@PathParam("id") String id) throws Exception {
		dataService.removeQuestion(id);
		return Response.ok().cacheControl(cc).build();
	}

	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lessons/{datatype}")
	public Response getLessons() throws Exception {
		Collection<ESession> lesson = dataService.getSessions();
		return Response.ok(lesson, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}
	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lessons/{id}/{datatype}")
	public Response getLesson(@PathParam("id") String id) throws Exception {
		ESession lesson = dataService.getSession(id);
		return Response.ok(lesson, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}

}
