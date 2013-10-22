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
package org.estudy.webservice;

import org.estudy.learning.Util;
import org.estudy.learning.model.ECategory;
import org.estudy.learning.model.EQuestion;
import org.estudy.learning.model.ESession;
import org.estudy.learning.model.ETesting;
import org.estudy.learning.storage.DataStorage;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.organization.User;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import java.util.*;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * May 2, 2013  
 */
public class MockJcrDataStorage implements DataStorage {

    @Override
    public Node getEStorageHome() throws RepositoryException, Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void saveCategory(ECategory category, boolean isNew) throws ItemExistsException, Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<ECategory> getCategories() throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ECategory getCategory(String i) throws ItemNotFoundException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeCategory(String id) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void saveSession(ESession session, boolean isNew) throws ItemExistsException, Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<ESession> getSessions() throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ESession getSession(String id) throws ItemNotFoundException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeSession(String id) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void saveQuestion(EQuestion qestion, boolean isNew) throws ItemExistsException, Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<EQuestion> getQuestions() throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EQuestion getQuestion(String id) throws ItemNotFoundException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeQuestion(String id) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void saveTesting(User user, ETesting test, boolean isNew) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<ETesting> getTestingScore(String uId, Collection<String> qIds) throws RepositoryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<ETesting> getTestingScore(String uId) throws RepositoryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeTesting(String uid, String id) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
