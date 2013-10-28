package org.estudy.ui.core;

import org.estudy.learning.storage.DataStorage;
import org.estudy.learning.storage.impl.JcrDataStorage;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;

import javax.portlet.PortletPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: tuanp
 * Date: 10/28/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */

public class Loader {
  public static final int DEFAULT_VALUE_UPLOAD_PORTAL = -1;
  public static final int DEFAULT_MAX_UPLOAD_FIELD = 10;

  public static DataStorage loadDataService(){
    return (DataStorage) PortalContainer.getInstance().getComponentInstanceOfType(JcrDataStorage.class);
  }

  public static int getLimitUploadSize() {
    PortletRequestContext pcontext = (PortletRequestContext) RequestContext.getCurrentInstance();
    PortletPreferences portletPref = pcontext.getRequest().getPreferences();
    int limitMB;
    try {
      limitMB = Integer.parseInt(portletPref.getValue("uploadFileSizeLimitMB", "").trim());
    } catch (Exception e) {
      limitMB = DEFAULT_VALUE_UPLOAD_PORTAL;
    }
    return limitMB;
  }
}
