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
package org.estudy.ui.portlet;

import org.estudy.learning.storage.DataStorage;
import org.estudy.learning.storage.impl.JcrDataStorage;
import org.estudy.ui.popup.UIPopupAction;
import org.estudy.ui.view.UILessionList;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIComponent;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;


@ComponentConfig(
		lifecycle = UIApplicationLifecycle.class,
		template  = "app:/templates/estudy/webui/EStudyPortlet.gtmpl"
		)
public class EStudyPortlet extends UIPortletApplication 
{
	public static Log log = ExoLogger.getExoLogger(EStudyPortlet.class);

	public EStudyPortlet() throws Exception 
	{
		addChild(UILessionList.class, null, null) ;
		UIPopupAction uiPopup =  addChild(UIPopupAction.class, null, null) ;
		uiPopup.setId("UIEPopupAction") ;
		uiPopup.getChild(UIPopupWindow.class).setId("UIEPopupWindow") ;
	}


	public void addPopup(UIComponent type, int width, int height) throws Exception{
		WebuiRequestContext context = RequestContext.getCurrentInstance() ;
		UIPopupAction popupAction = getChild(UIPopupAction.class);
		popupAction.deActivate();
		popupAction.activate(type, width, height);
		context.addUIComponentToUpdateByAjax(popupAction) ;
	}
	public void closePopup()throws Exception{
		WebuiRequestContext context = RequestContext.getCurrentInstance() ;
		UIPopupAction popupAction = getChild(UIPopupAction.class) ;
		popupAction.deActivate() ;
		context.addUIComponentToUpdateByAjax(popupAction) ;

	}
	public static DataStorage getDataService() {
		return (DataStorage) PortalContainer.getInstance().getComponentInstanceOfType(JcrDataStorage.class);
	}

}
