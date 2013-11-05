
package org.estudy.webservice;

import org.estudy.learning.model.EQuestion;
import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.estudy.learning.storage.DataStorage;
import org.estudy.learning.storage.impl.JcrDataStorage;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;

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
  
  public EStudyWebservice() {}


  /**
   * Checks permission of the currently logged-in user on any test by the given test Id.
   * The input parameters will be in the URL of the test.
   * @param username The given user's Id, or the currently logged-in user.
   * @param testid The given test Id on which the permission is checked.
   * @param type The test type: _private_, _public_ or _shared_.
   * @return The JSON data value will be returned.
   * @throws Exception
   * 
   * @anchor EStudyApplication.checkPermission
   * @LevelAPI Experimental
   */
  @GET
  @RolesAllowed("users")
  @Path("/checkPermission/{username}/{testid}/{type}/")
  public Response checkPermission(@PathParam("username")
                                  String username, @PathParam("testid")
                                  String calendarId, @PathParam("type")
                                  String type) throws Exception {
    JSONData data = new JSONData();
    CacheControl cacheControl = new CacheControl();
    cacheControl.setNoCache(true);
    cacheControl.setNoStore(true);


    return Response.ok(data, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }


  @GET
  //@RolesAllowed("users")
  @Path("/questions/{datatype}")
  public Response getQuestions() throws Exception {
    //JSONData data = new JSONData();
    EQuestion question = new EQuestion();
    question.setPoint(10);
    question.setTitle("what is HTML5");
    String list[] = {"It is the new version of HTML"} ;
    question.setCorrect(Arrays.asList(list));
    String lists[] = {"It is the new version of HTML", "It is the new way of HTML"} ;
    question.setAnswers(Arrays.asList(lists));
    question.setAnswered("It is the new version of HTML");
    CacheControl cacheControl = new CacheControl();
    cacheControl.setNoCache(true);
    cacheControl.setNoStore(true);


    return Response.ok(question, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }


}
