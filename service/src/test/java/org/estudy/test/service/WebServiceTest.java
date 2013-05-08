/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.estudy.test.service;

import java.io.ByteArrayInputStream;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;

import junit.framework.TestCase;

import org.estudy.learning.service.EStudyWebService;
import org.estudy.learning.storage.DataStorage;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.component.RequestLifeCycle;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.ContainerResponseWriter;
import org.exoplatform.services.rest.RequestHandler;
import org.exoplatform.services.rest.impl.ContainerRequest;
import org.exoplatform.services.rest.impl.ContainerResponse;
import org.exoplatform.services.rest.impl.EnvironmentContext;
import org.exoplatform.services.rest.impl.InputHeadersMap;
import org.exoplatform.services.rest.impl.MultivaluedMapImpl;
import org.exoplatform.services.rest.impl.ResourceBinder;
import org.exoplatform.services.rest.tools.ByteArrayContainerResponseWriter;

public class WebServiceTest extends TestCase
{
  private static Log log = ExoLogger.getExoLogger(WebServiceTest.class);
  
  private static ExoContainer eContainer;
  
  private static EStudyWebService webService;
  
  
  private static RequestHandler requestHandler;
  
  private static String baseURI = "";

  private static ResourceBinder binder;
  
  
  public void testSearch()
  {
    log.info("--- test search ---\n");
    
    String restURI = "/bookstore/rest/estudy/search/d";
    MultivaluedMap<String, String> values = new MultivaluedMapImpl();
    values.putSingle("username", "root");
    ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();
    
    try 
    {
      ContainerResponse response = service("GET", restURI, baseURI, values, null, writer);
      log.info("response status: " + response.getStatus());
      log.info("content type: " + response.getContentType().toString());
      
      //assertEquals(new Integer(response.getStatus()), new Integer(200));
      assertNotNull(response.getEntity());
    } 
    catch (Exception e) 
    {
      log.error("exception test search: " + e.getMessage());
    }
    
    log.info("--- test search: OK ---\n");
  }
  
  
  private ContainerResponse service(String method,
                                    String requestURI,
                                    String baseURI,
                                    MultivaluedMap<String, String> headers,
                                    byte[] data,
                                    ContainerResponseWriter writer) throws Exception {
     RequestLifeCycle.begin(eContainer);
     if (headers == null) {
       headers = new MultivaluedMapImpl();
     }

     ByteArrayInputStream in = null;
     if (data != null) {
       in = new ByteArrayInputStream(data);
     }

     EnvironmentContext envctx = new EnvironmentContext();
     HttpServletRequest httpRequest = new MockHttpServletRequest(in,
                                                                 in != null ? in.available() : 0,
                                                                 method,
                                                                 new InputHeadersMap(headers));
     envctx.put(HttpServletRequest.class, httpRequest);
     EnvironmentContext.setCurrent(envctx);
     ContainerRequest request = new ContainerRequest(method,
                                                     new URI(requestURI),
                                                     new URI(baseURI),
                                                     in,
                                                     new InputHeadersMap(headers));
     ContainerResponse response = new ContainerResponse(writer);
     requestHandler.handleRequest(request, response);
     RequestLifeCycle.end();
     return response;
   }
  
  
   
}
