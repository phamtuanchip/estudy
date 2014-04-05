/**
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */

package org.estudy.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.estudy.learning.model.Attachment;
import org.estudy.learning.model.ECategory;
import org.estudy.learning.model.EQuestion;
import org.estudy.learning.model.ESession;
import org.estudy.learning.model.ETesting;
import org.estudy.learning.storage.DataStorage;
import org.estudy.learning.storage.impl.JcrDataStorage;
import org.exoplatform.common.http.HTTPMethods;
import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.services.rest.impl.ContainerResponse;
import org.exoplatform.services.rest.impl.MultivaluedMapImpl;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.rest.tools.ByteArrayContainerResponseWriter;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.MembershipEntry;
import org.exoplatform.ws.frameworks.json.impl.JsonGeneratorImpl;
import org.exoplatform.ws.frameworks.json.value.JsonValue;
import org.json.JSONObject;

/**
 * Created by The eXo Platform SARL Author : Volodymyr Krasnikov
 * volodymyr.krasnikov@exoplatform.com.ua
 */

public class TestWebservice extends AbstractResourceTest {

	DataStorage storage_;
	EStudyWebservice webservice;
	private Collection<MembershipEntry> membershipEntries = new ArrayList<MembershipEntry>();

	static final String             baseURI = "";
	String username = "root";

	MultivaluedMap<String, String> h = new MultivaluedMapImpl();


	public void setUp() throws Exception {
		RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
		super.setUp();
		webservice = (EStudyWebservice) container.getComponentInstanceOfType(EStudyWebservice.class);
		storage_ = (DataStorage) container.getComponentInstanceOfType(JcrDataStorage.class);
		binder.addResource(webservice, null);
		login(username) ;
		h.putSingle("username", username);
	}

	public void tearDown() throws Exception {
		super.tearDown();
		for (ECategory cal : storage_.getCategories()) {
			storage_.removeCategory(cal.getId());
		}
		for (EQuestion cal : storage_.getQuestions()) {
			storage_.removeQuestion(cal.getId());
		}
		for (ESession cal : storage_.getSessions()) {
			storage_.removeSession(cal.getId());
		}
		for(Attachment att : storage_.getMedias()) {
			storage_.removeMedia(att.getId());
		}
		//    for (ETesting cal : dataService.getT()) {
		//    dataService.removeTesting(username, cal.getId());
		//    }

	}

	public void testGetThumbnail() throws Exception {
		InputStream mediaInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("students.jpg");

		Attachment att = new Attachment(mediaInputStream);
		att.setName("image_uploaded");
		att.setMimeType("image/jpg");
		String url = storage_.uploadMedia(att) ;
		String atId = URLEncoder.encode(att.getId().toString(), "ISO-8859-1");
		String query = "?type=thumbnail&w=15&h=15";
		String extURI = "/estudy/api/media/"+att.getId() + query ;//+ username + "/" + test.getId() + "/0";

		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

		ContainerResponse response = service(HTTPMethods.GET, extURI, baseURI, h, null, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.OK, response.getStatus());
		InputStream in = (InputStream) response.getEntity();
		assertNotNull(in);
	}

	public void testGetCategories() throws Exception {
		ECategory e = new ECategory("Test category");
		storage_.saveCategory(e, true);

		String extURI = "/estudy/api/categories/data.json" ;//+ username + "/" + test.getId() + "/0";


		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

		ContainerResponse response = service(HTTPMethods.GET, extURI, baseURI, h, null, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.OK, response.getStatus());

		Collection<ECategory> results = (Collection<ECategory>)response.getEntity();
		assertNotNull(results);
		assertEquals(1, results.size());



	}
	public void testGetCategoryBtId() throws Exception {
		ECategory e = new ECategory("Test category");
		storage_.saveCategory(e, true);

		String extURI = "/estudy/api/categories/"+e.getId()+"/data.json" ;//+ username + "/" + test.getId() + "/0";


		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

		ContainerResponse response = service(HTTPMethods.GET, extURI, baseURI, h, null, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.OK, response.getStatus());

		ECategory results = (ECategory)response.getEntity();
		assertNotNull(results);


	}
	public void testCreateCategory() throws Exception {
		ECategory e = new ECategory("Test category");


		JsonGeneratorImpl generatorImpl = new JsonGeneratorImpl();
		JsonValue json = generatorImpl.createJsonObject(e);
		byte[] data = json.toString().getBytes("UTF-8");

		h.putSingle("content-type", "application/json");
		h.putSingle("content-length", "" + data.length);

		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();
		String extURI = "/estudy/api/categories/data.json";
		ContainerResponse response = service(HTTPMethods.POST, extURI, baseURI, h, data, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.OK, response.getStatus());
		ECategory results = (ECategory)response.getEntity();
		assertNotNull(results);
		Collection<ECategory> results2 = storage_.getCategories();
		assertEquals(1, results2.size());
	}
	public void testUpdateCategory() throws Exception {
		ECategory e = new ECategory("Test category");
		storage_.saveCategory(e, true);
		JsonGeneratorImpl generatorImpl = new JsonGeneratorImpl();
		JsonValue json = generatorImpl.createJsonObject(e);
		byte[] data = json.toString().getBytes("UTF-8");
		h.putSingle("content-type", "application/json");
		h.putSingle("content-length", "" + data.length);

		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();
		String extURI = "/estudy/api/categories/"+e.getId()+"/data.json";
		ContainerResponse response = service(HTTPMethods.PUT, extURI, baseURI, h, data, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.OK, response.getStatus());
		extURI = "/estudy/api/categories/"+e.getId()+"/data.json";
		response = service(HTTPMethods.GET, extURI, baseURI, h, null, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.OK, response.getStatus());
		ECategory results = (ECategory)response.getEntity();
		assertNotNull(results);
	}
	public void testDeleteCategory() throws Exception {
		ECategory e = new ECategory("Test category");
		storage_.saveCategory(e, true);
		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();
		String extURI = "/estudy/api/categories/"+e.getId()+"/data.json";
		ContainerResponse response = service(HTTPMethods.DELETE, extURI, baseURI, h, null, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.OK, response.getStatus());

	}


	public void testGetLessons() throws Exception {


		try {
			EQuestion question = new EQuestion();
			question.setPoint(10);
			question.setTitle("what is HTML5");
			String list[] = {"It is the new version of HTML"} ;
			question.setCorrect(new ArrayList(Arrays.asList(list)));
			String lists[] = {"It is the new version of HTML", "It is the new way of HTML"} ;
			question.setAnswers(new ArrayList(Arrays.asList(lists)));
			question.setAnswered("It is the new version of HTML");
			storage_.saveQuestion(question, true);
			ECategory category = new ECategory();
			category.setName("web, internet");
			ESession lesson = new ESession();
			lesson.setCat(category.getId());
			lesson.setQuest(question.getTitle());
			lesson.setTitle("How to start with html");
			lesson.setDec("<h1>hello html </h1>");

			storage_.saveSession(lesson, true);

			String extURI = "/estudy/api/lessons/data.json" ;//+ username + "/" + test.getId() + "/0";


			ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

			ContainerResponse response = service(HTTPMethods.GET, extURI, baseURI, h, null, writer);
			assertNotNull(response);
			assertEquals(HTTPStatus.OK, response.getStatus());
			Collection<ESession> results = (Collection<ESession>)response.getEntity();
			assertNotNull(results);

			assertEquals(lesson.getTitle(), results.iterator().next().getTitle());

		}  catch (Exception e) {
			e.printStackTrace();

		}


	}

	public void testGetQuestions() throws Exception {


		try {
			EQuestion question = new EQuestion();
			question.setPoint(10);
			question.setTitle("what is HTML5");
			String list[] = {"It is the new version of HTML"} ;
			question.setCorrect(new ArrayList(Arrays.asList(list)));
			String lists[] = {"It is the new version of HTML", "It is the new way of HTML"} ;
			question.setAnswers(new ArrayList(Arrays.asList(lists)));
			question.setAnswered("It is the new version of HTML");
			storage_.saveQuestion(question, true);

			String extURI = "/estudy/api/questions/data.json" ;//+ username + "/" + test.getId() + "/0";


			ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

			ContainerResponse response = service(HTTPMethods.GET, extURI, baseURI, h, null, writer);
			assertNotNull(response);
			assertEquals(HTTPStatus.OK, response.getStatus());

			String jsonString = buildResponse(extURI);
			JSONObject jsonObject = new JSONObject(jsonString);
			assertNotNull(jsonObject);

			Collection<EQuestion> results = (Collection<EQuestion>)response.getEntity();
			assertNotNull(results);

			assertEquals(jsonObject.getString("title"), results.iterator().next().getTitle());


		}  catch (Exception e) {
			//e.printStackTrace();

		}


	}

	public void testGetQuestionById() throws Exception {


		try {
			EQuestion question = new EQuestion();
			question.setPoint(10);
			question.setTitle("what is HTML5");
			String list[] = {"It is the new version of HTML"} ;
			question.setCorrect(new ArrayList(Arrays.asList(list)));
			String lists[] = {"It is the new version of HTML", "It is the new way of HTML"} ;
			question.setAnswers(new ArrayList(Arrays.asList(lists)));
			question.setAnswered("It is the new version of HTML");
			storage_.saveQuestion(question, true);

			String extURI = "/estudy/api/questions/"+question.getId()+"/data.json" ;//+ username + "/" + test.getId() + "/0";


			ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

			ContainerResponse response = service(HTTPMethods.GET, extURI, baseURI, h, null, writer);
			assertNotNull(response);
			assertEquals(HTTPStatus.OK, response.getStatus());

			EQuestion results = (EQuestion)response.getEntity();
			assertNotNull(results);

			assertEquals(question.getTitle(), results.getTitle());


		}  catch (Exception e) {
			//e.printStackTrace();

		}


	}

	//  public void testCreateQuestion() throws Exception {
	//
	//
	//	    try {
	//	      EQuestion question = new EQuestion();
	//	      question.setPoint(10);
	//	      question.setTitle("what is HTML5");
	//	      String list[] = {"It is the new version of HTML"} ;
	//	      question.setCorrect(new ArrayList(Arrays.asList(list)));
	//	      String lists[] = {"It is the new version of HTML", "It is the new way of HTML"} ;
	//	      question.setAnswers(new ArrayList(Arrays.asList(lists)));
	//	      question.setAnswered("It is the new version of HTML");
	//	      //dataService.saveQuestion(question, true);
	//	      
	//	      String extURI = "/estudy/api/questions/data.json" ;//+ username + "/" + test.getId() + "/0";
	//	      
	//	      JsonGeneratorImpl generatorImpl = new JsonGeneratorImpl();
	//	      JsonValue json = generatorImpl.createJsonObject(question);
	//	      byte[] data = json.toString().getBytes("UTF-8");
	//	      
	//	      h.putSingle("content-type", "application/json");
	//	      h.putSingle("content-length", "" + data.length);
	//
	//	      ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();
	//
	//	      ContainerResponse response = service(HTTPMethods.POST, extURI, baseURI, h, data, writer);
	//	      assertNotNull(response);
	//	      assertEquals(HTTPStatus.CREATED, response.getStatus());
	//	      EQuestion results = (EQuestion)response.getEntity();
	//	      assertNotNull(results);
	//	    }  catch (Exception e) {
	//	      //e.printStackTrace();
	//
	//	    }
	//
	//
	//	  }
	//
	//
	//  public void testupdateQuestion() throws Exception {
	//
	//
	//	    try {
	//	      EQuestion question = new EQuestion();
	//	      question.setPoint(10);
	//	      question.setTitle("what is HTML5");
	//	      String list[] = {"It is the new version of HTML"} ;
	//	      question.setCorrect(new ArrayList(Arrays.asList(list)));
	//	      String lists[] = {"It is the new version of HTML", "It is the new way of HTML"} ;
	//	      question.setAnswers(new ArrayList(Arrays.asList(lists)));
	//	      question.setAnswered("It is the new version of HTML");
	//	      dataService.saveQuestion(question, true);
	//	      
	//	      String extURI = "/estudy/api/questions/"+question.getId()+"/data.json" ;//+ username + "/" + test.getId() + "/0";
	//	      
	//	      JsonGeneratorImpl generatorImpl = new JsonGeneratorImpl();
	//	      JsonValue json = generatorImpl.createJsonObject(question);
	//	      byte[] data = json.toString().getBytes("UTF-8");
	//	      
	//	      h.putSingle("content-type", "application/json");
	//	      h.putSingle("content-length", "" + data.length);
	//
	//	      ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();
	//
	//	      ContainerResponse response = service(HTTPMethods.PUT, extURI, baseURI, h, data, writer);
	//	      assertNotNull(response);
	//	      assertEquals(HTTPStatus.OK, response.getStatus());
	//	      Collection<EQuestion> results = (Collection<EQuestion>)response.getEntity();
	//	      assertNotNull(results);
	//	    }  catch (Exception e) {
	//	      //e.printStackTrace();
	//
	//	    }
	//
	//
	//	  }
	public void testDeleteQuestion() throws Exception {


		try {
			EQuestion question = new EQuestion();
			question.setPoint(10);
			question.setTitle("what is HTML5");
			String list[] = {"It is the new version of HTML"} ;
			question.setCorrect(new ArrayList(Arrays.asList(list)));
			String lists[] = {"It is the new version of HTML", "It is the new way of HTML"} ;
			question.setAnswers(new ArrayList(Arrays.asList(lists)));
			question.setAnswered("It is the new version of HTML");
			storage_.saveQuestion(question, true);

			String extURI = "/estudy/api/questions/"+question.getId()+"/data.json" ;//+ username + "/" + test.getId() + "/0";


			ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

			ContainerResponse response = service(HTTPMethods.DELETE, extURI, baseURI, h, null, writer);
			assertNotNull(response);
			assertEquals(HTTPStatus.OK, response.getStatus());

		}  catch (Exception e) {
			//e.printStackTrace();

		}


	}
	public void testCheckPermission() throws Exception {


		//assert false ;
		ETesting test = new ETesting();
		test.setTime(0);
		test.setNote("this is final test");


		String extURI = "/estudy/api/checkPermission/";

		JsonGeneratorImpl generatorImpl = new JsonGeneratorImpl();
		JsonValue json = generatorImpl.createJsonObject(ConversationState.getCurrent().getIdentity().getUserId());
		byte[] data = json.toString().getBytes("UTF-8");

		h.putSingle("content-type", "application/json");
		h.putSingle("content-length", "" + data.length);

		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

		ContainerResponse response = service(HTTPMethods.POST, extURI, baseURI, h, data, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.ACCEPTED, response.getStatus());


	}


	private void login(String username) {

		setMembershipEntry("/platform/users", "member", true);
		Identity identity = new Identity(username, membershipEntries);
		ConversationState state = new ConversationState(identity);
		ConversationState.setCurrent(state);
	}

	private void setMembershipEntry(String group, String membershipType, boolean isNew) {
		MembershipEntry membershipEntry = new MembershipEntry(group, membershipType);
		if (isNew) {
			membershipEntries.clear();
		}
		membershipEntries.add(membershipEntry);
	}


	public static String buildResponse(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		String message ;
		httpGet.setHeader("Authorization", "Basic " + "root:gtn");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			switch (statusCode) {
			case 404:
				//Log.e(Home.class.toString(), "Service not found: " + statusCode);
				message = "Service not found: " + statusCode;
				break;
			case 401:
				// Log.e(Home.class.toString(), "Need to login: " + statusCode);
				message = "Need to login: " + statusCode;
				break;
			case 200:
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				break;
			default:
				//Log.e(Home.class.toString(), "Error : " + statusCode);
				message = "Error: " + statusCode;
				break;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}


}
