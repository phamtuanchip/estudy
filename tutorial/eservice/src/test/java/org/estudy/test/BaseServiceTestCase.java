/*
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
package org.estudy.test;

import java.util.ArrayList;
import java.util.Collection;

import org.exoplatform.component.test.AbstractKernelTest;
import org.exoplatform.component.test.ConfigurationUnit;
import org.exoplatform.component.test.ConfiguredBy;
import org.exoplatform.component.test.ContainerScope;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.MembershipEntry;


@ConfiguredBy({
  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/exo.portal.component.portal-configuration.xml"),
  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/exo.portal.component.test.jcr-configuration.xml"),
  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/exo.portal.component.identity-configuration.xml"),
  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/portal/test-portal-configuration.xml"),
  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/portal/estudy.component.core.test.configuration.xml"),
  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/portal/estudy.test.jcr-configuration.xml"),
  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/portal/estudy.test.portal-configuration.xml")
})

public abstract class BaseServiceTestCase extends AbstractKernelTest {

  protected static Log LOG = ExoLogger.getLogger(BaseServiceTestCase.class);
  protected Collection<MembershipEntry> membershipEntries = new ArrayList<MembershipEntry>();
 
  
  @Override
  public void setUp() throws Exception {
    begin();
  }

  @Override
  public void tearDown() throws Exception {
    //
    removeAllData();
    end();
  }
  

  protected void removeAllData() {
    // TODO Auto-generated method stub
    
  }

  @SuppressWarnings("unchecked")
  protected <T> T getService(Class<T> clazz) {
    return (T) getContainer().getComponentInstanceOfType(clazz);
  }
  
  protected void setMembershipEntry(String group, String membershipType, boolean isNew) {
    MembershipEntry membershipEntry = new MembershipEntry(group, membershipType);
    if (isNew) {
      membershipEntries.clear();
    }
    membershipEntries.add(membershipEntry);
  }

  protected void loginUser(String userId) {
    Identity identity = new Identity(userId, membershipEntries);
    ConversationState state = new ConversationState(identity);
    ConversationState.setCurrent(state);
  }
}
