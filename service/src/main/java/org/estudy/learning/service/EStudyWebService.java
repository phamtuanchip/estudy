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
package org.estudy.learning.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import org.estudy.learning.storage.DataStorage;
import org.estudy.learning.storage.impl.JcrDataStorage;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;


@Path("/estudy")
public class EStudyWebService implements ResourceContainer
{
  private static final Log log = ExoLogger.getLogger(EStudyWebService.class);

  private static final CacheControl cc;

  private DataStorage storage;
  
  static 
  {
    cc = new CacheControl();
    cc.setNoCache(true);
    cc.setNoStore(true);
  }

  public EStudyWebService() 
  {
    
    storage = (JcrDataStorage) ExoContainerContext.getCurrentContainer()
        .getComponentInstanceOfType(JcrDataStorage.class);
  }
  
 
  
  @GET
  @Path("/search/{keyword}")
  public Response searchBookByTitleLike(@PathParam("keyword") String keyword) throws Exception
  {
    return null;
  }
  
  
}
