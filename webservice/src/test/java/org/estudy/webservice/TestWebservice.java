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

import org.estudy.learning.model.ETesting;
import org.estudy.learning.storage.DataStorage;
import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.services.rest.impl.ContainerResponse;
import org.exoplatform.services.rest.impl.MultivaluedMapImpl;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.rest.tools.ByteArrayContainerResponseWriter;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.MembershipEntry;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.RuntimeDelegate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by The eXo Platform SARL Author : Volodymyr Krasnikov
 * volodymyr.krasnikov@exoplatform.com.ua
 */

public class TestWebservice extends AbstractResourceTest {

  DataStorage dataService;
  EStudyWebservice webservice;
  private Collection<MembershipEntry> membershipEntries = new ArrayList<MembershipEntry>();

  static final String             baseURI = "";
  String username = "root";
  
  MultivaluedMap<String, String> h = new MultivaluedMapImpl();
  

  public void setUp() throws Exception {
    RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
    super.setUp();
      webservice = (EStudyWebservice) container.getComponentInstanceOfType(EStudyWebservice.class);
      dataService = (DataStorage) container.getComponentInstanceOfType(MockJcrDataStorage.class);
    binder.addResource(webservice, null);
    login() ;
    h.putSingle("username", username);
  }

  public void tearDown() throws Exception {
    super.tearDown();
    
  }



  public void testcheckPermission() throws Exception {


      //assert false ;
      ETesting test = new ETesting();
      test.setTime(0);
      test.setNote("this is final test");


    String extURI = "/estudy/api/checkPermission/" + username + "/" + test.getId() + "/0";


    ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

    ContainerResponse response = service("GET", extURI, baseURI, h, null, writer);
    assertNotNull(response);
    assertEquals(HTTPStatus.OK, response.getStatus());

    response = service("GET",extURI, baseURI, h, null, writer);
    assertNotNull(response);
    assertEquals(HTTPStatus.OK, response.getStatus());
    
  }

  
  private void login() {
    
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


}
