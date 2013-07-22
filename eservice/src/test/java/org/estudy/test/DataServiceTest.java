/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
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
package org.estudy.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.ItemExistsException;

import org.estudy.service.DataStorage;
import org.estudy.service.impl.JcrDataStorage;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.organization.User;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.MembershipEntry;


/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * May 2, 2013  
 */
public class DataServiceTest extends BaseServiceTestCase {

  private RepositoryService repositoryService_ ;
  private DataStorage  storage_;
  private static String   username = "root";
  public Collection<MembershipEntry> membershipEntries = new ArrayList<MembershipEntry>();
  private OrganizationService organizationService_;

  public void setUp() throws Exception {
    super.setUp();
    repositoryService_ = getService(RepositoryService.class);
    organizationService_ = (OrganizationService) getService(OrganizationService.class);
    storage_ = getService(JcrDataStorage.class);
  }
 
  //mvn test -Dtest=EstudyServiceTest#testInitServices
  public void testInitServices() throws Exception{
    assertNotNull(repositoryService_) ;
    assertEquals(repositoryService_.getDefaultRepository().getConfiguration().getName(), "repository");
    assertEquals(repositoryService_.getDefaultRepository().getConfiguration().getDefaultWorkspaceName(), "portal-test");
    assertNotNull(organizationService_) ;
    assertNotNull(storage_);

  }
  public void testCheckServiceAPI() throws Exception{
    storage_.getData() ;
    storage_.saveData();
  }
}
