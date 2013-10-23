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

package org.estudy.service.impl;

import javax.jcr.Node;
import javax.jcr.Session;

import org.estudy.service.DataStorage;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;
 
public class JcrDataStorage implements DataStorage {
  
  private NodeHierarchyCreator           nodeHierarchyCreator_;
  private RepositoryService repositoryService;
  
  public JcrDataStorage(InitParams params, NodeHierarchyCreator nodeHierarchyCreator) {
    nodeHierarchyCreator_ = nodeHierarchyCreator;
  }
    
  public void saveData() {
    System.out.println("save data here ........");
  }
  public void getData() {
    System.out.println("geting data from here ........");
  }
  
  private Session getSession(SessionProvider sessionProvider) {
    Session session = null;
    try {
      if (repositoryService == null) {
        repositoryService = (RepositoryService) PortalContainer.getInstance().getComponentInstanceOfType(RepositoryService.class);
      }
      ManageableRepository repository = repositoryService.getCurrentRepository();
      session = sessionProvider.getSession("portal-test", repository);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return session;
  }

  private Node getNodeByPath(String nodePath, SessionProvider sessionProvider) throws Exception {
    return (Node) getSession(sessionProvider).getItem(nodePath);
  }

  private Node getCategoryNode(SessionProvider sProvider) throws Exception {
    Node publicApp = getNodeByPath(nodeHierarchyCreator_.getJcrPath("eXoApplications"), sProvider);
    Node appHome, categoryNode;
    try {
      appHome = publicApp.getNode("estudyHome");
    } catch (Exception e) {
      appHome = publicApp.addNode("estudyHome", "exo:category");
      publicApp.getSession().save();
    }
    try {
      categoryNode = appHome.getNode("estudyHome");
    } catch (Exception e) {
      categoryNode = appHome.addNode("estudyHome", "exo:category");
      publicApp.getSession().save();
    }
    return categoryNode;
  }
}
