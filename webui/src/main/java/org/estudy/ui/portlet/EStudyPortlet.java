 
package org.estudy.ui.portlet;

import org.estudy.learning.storage.DataStorage;
import org.estudy.learning.storage.impl.JcrDataStorage;
import org.estudy.ui.popup.UIPopupAction;
import org.estudy.ui.view.UIContentViewer;
import org.estudy.ui.view.UILessionList;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.web.application.ApplicationMessage;
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
        addChild(UIContentViewer.class, null, null) ;
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
	
	public static void showMessage(String message, int messageType, Object params []){
		WebuiRequestContext context = RequestContext.getCurrentInstance() ;
		context.getUIApplication()
        .addMessage(new ApplicationMessage(message, params, messageType));
	}

}
