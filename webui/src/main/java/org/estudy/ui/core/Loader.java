package org.estudy.ui.core;

import org.estudy.learning.storage.DataStorage;
import org.estudy.learning.storage.impl.JcrDataStorage;
import org.exoplatform.container.PortalContainer;

/**
 * Created with IntelliJ IDEA.
 * User: tuanp
 * Date: 10/28/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Loader {
  public static DataStorage loadDataService(){
    return (DataStorage) PortalContainer.getInstance().getComponentInstanceOfType(JcrDataStorage.class);
  }
}
