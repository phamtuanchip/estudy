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
package org.estudy.learning.storage;

import java.util.Collection;

import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.estudy.learning.model.ECategory;
import org.estudy.learning.model.EQuestion;
import org.estudy.learning.model.ESession;
import org.estudy.learning.model.ETesting;
import org.exoplatform.services.organization.User;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * May 2, 2013  
 */
public interface DataStorage {
  public Node getEStorageHome() throws RepositoryException, Exception;
  
  public void saveCategory(ECategory category, boolean isNew) throws ItemExistsException, Exception;
  public Collection<ECategory> getCategories() throws Exception;
  public ECategory getCategory(String i) throws ItemNotFoundException;
  public void removeCategory(String id) throws Exception;
  
  public void saveSession(ESession session, boolean isNew) throws ItemExistsException, Exception;
  public Collection<ESession> getSessions() throws Exception;
  public ESession getSession(String id) throws ItemNotFoundException;
  public void removeSession(String id) throws Exception;
  
  public void saveQuestion(EQuestion qestion, boolean isNew) throws ItemExistsException, Exception;
  public Collection<EQuestion> getQuestions() throws Exception;
  public EQuestion getQuestion(String id) throws ItemNotFoundException;
  public void removeQuestion(String id) throws Exception;
  
  public void saveTesting(User user, ETesting test, boolean isNew) throws Exception;
  public Collection<ETesting> getTestingScore(String uId, Collection<String> qIds) throws RepositoryException;
  public Collection<ETesting> getTestingScore(String uId) throws RepositoryException;
  public void removeTesting(String uid, String id) throws Exception;
  
  
}
