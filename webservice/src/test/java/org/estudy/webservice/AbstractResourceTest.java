/**
 * Copyright (C) 2003-2008 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */

package org.estudy.webservice;

import org.exoplatform.container.component.RequestLifeCycle;
import org.exoplatform.services.rest.ContainerResponseWriter;
import org.exoplatform.services.rest.impl.*;
import org.exoplatform.services.rest.tools.DummyContainerResponseWriter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.io.ByteArrayInputStream;
import java.net.URI;

/**
 * @author <a href="mailto:andrew00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
public abstract class AbstractResourceTest extends BaseTest {


  public ContainerResponse service(String method,
                                   String requestURI,
                                   String baseURI,
                                   MultivaluedMap<String, String> headers,
                                   byte[] data,
                                   ContainerResponseWriter writer) throws Exception {
	  try {
		
	
  RequestLifeCycle.begin(container);
    if (headers == null)
      headers = new MultivaluedMapImpl();

    ByteArrayInputStream in = null;
    if (data != null)
      in = new ByteArrayInputStream(data);

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
	  } catch (Exception e) {
		  e.printStackTrace();
		return null;
	}
  }

  public ContainerResponse service(String method,
                                   String requestURI,
                                   String baseURI,
                                   MultivaluedMap<String, String> headers,
                                   byte[] data) throws Exception {
    return service(method, requestURI, baseURI, headers, data, new DummyContainerResponseWriter());

  }

}
