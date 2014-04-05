
package org.estudy.webservice;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.estudy.learning.model.Attachment;
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
	/** The Constant LAST_MODIFIED_PROPERTY. */
	private static final String LAST_MODIFIED_PROPERTY = "Last-Modified";

	/** The Constant IF_MODIFIED_SINCE_DATE_FORMAT. */
	private static final String IF_MODIFIED_SINCE_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

	static CacheControl cc = new CacheControl();
	static {
		cc.setNoCache(true);
		cc.setNoStore(true);
	}

	private DataStorage storage_ = null;
	private Object getJcrDataStorage() {
		storage_ = (DataStorage)ExoContainerContext.getCurrentContainer()
				.getComponentInstanceOfType(JcrDataStorage.class);
		if(storage_ == null){
			return Response.status(HTTPStatus.UNAVAILABLE).cacheControl(cc).build();
		}
		return storage_;
	}

	public EStudyWebservice() {
		storage_ = (DataStorage)ExoContainerContext.getCurrentContainer()
				.getComponentInstanceOfType(JcrDataStorage.class);
	}

	@GET
	//@RolesAllowed("users")
	@Path("/media/{id}/")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getImageThumnail(@QueryParam("type") String type, @QueryParam("w") String width, @QueryParam("h") String height, @PathParam("id") String id) throws Exception {
		try {
			Attachment evData = storage_.getMediaById(id);
			DateFormat dateFormat = new SimpleDateFormat(IF_MODIFIED_SINCE_DATE_FORMAT);
			InputStream inputStream = evData.getInputStream();
			if("thumbnail".equals(type)){
				BufferedImage imageBuffer = ImageIO.read(inputStream);
				int maxWidth = Integer.parseInt(width);
				int maxHeight = Integer.parseInt(height);
				InputStream thumbnail = ImageUtils.scaleImage(imageBuffer, maxWidth, maxHeight);
				return Response.ok(thumbnail, "image").header(LAST_MODIFIED_PROPERTY,
						dateFormat.format(new Date())).build();
			} else if ("full".equals(type)) {
				return Response.ok(inputStream, "image").header(LAST_MODIFIED_PROPERTY,
						dateFormat.format(new Date())).build();
			} else {
				return Response.ok(inputStream, "application").header(LAST_MODIFIED_PROPERTY,
						dateFormat.format(new Date())).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(HTTPStatus.INTERNAL_ERROR).cacheControl(cc).build();
		}


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
		Collection<ECategory> question = storage_.getCategories();
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}
	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/categories/{id}/{datatype}")
	public Response getCategoryById(@PathParam("id") String id) throws Exception {
		ECategory question = storage_.getCategory(id);
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}

	@PUT
	@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/categories/{id}/{datatype}")
	public Response updateCategory(@PathParam("id")String id, ECategory c) throws Exception {
		ECategory question = storage_.saveCategory(c, false);
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}
	@DELETE
	@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/categories/{id}/{datatype}")
	public Response deleteCategory(@PathParam("id")String id) throws Exception {
		storage_.removeCategory(id);
		return Response.status(HTTPStatus.OK).cacheControl(cc).build();
	}

	@POST
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/categories/{datatype}")
	public Response createCategory(ECategory c) throws Exception {
		ECategory question = storage_.saveCategory(c, true);
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}


	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/{datatype}")
	public Response getQuestions() throws Exception {
		Collection<EQuestion> question = storage_.getQuestions();
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}

	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/{id}/{datatype}")
	public Response getQuestionByid(@PathParam("id") String id) throws Exception {
		EQuestion question = storage_.getQuestion(id);
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}

	@POST
	@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/{datatype}")
	public Response createQuestion(EQuestion q) throws Exception {
		EQuestion question = storage_.saveQuestion(q, true);
		return Response.ok(question, MediaType.APPLICATION_JSON).status(HTTPStatus.CREATED).cacheControl(cc).build();
	}
	@PUT
	@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/id/{datatype}")
	public Response upcateQuestion(@PathParam("id") String id ,EQuestion q) throws Exception {
		EQuestion question = storage_.saveQuestion(q, false);
		return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}
	@DELETE
	@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/questions/{id}/{datatype}")
	public Response deleteQuestion(@PathParam("id") String id) throws Exception {
		storage_.removeQuestion(id);
		return Response.ok().cacheControl(cc).build();
	}

	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lessons/{datatype}")
	public Response getLessons() throws Exception {
		Collection<ESession> lesson = storage_.getSessions();
		return Response.ok(lesson, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}
	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lessons/{id}/{datatype}")
	public Response getLesson(@PathParam("id") String id) throws Exception {
		ESession lesson = storage_.getSession(id);
		return Response.ok(lesson, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}

}
