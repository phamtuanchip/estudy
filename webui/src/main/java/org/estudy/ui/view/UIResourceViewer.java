package org.estudy.ui.view;

import java.util.ArrayList;
import java.util.Collection;

import javax.jcr.RepositoryException;

import org.estudy.learning.Util;
import org.estudy.learning.model.Attachment;
import org.estudy.learning.storage.DataStorage;
import org.estudy.ui.portlet.EStudyPortlet;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.portal.application.PortalRequestContext;
import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
@ComponentConfig(
		template =  "app:/templates/estudy/webui/UIResourceViewer.gtmpl",
		events = {
				@EventConfig(listeners = UIResourceViewer.DownloadActionListener.class)

		}
		)
public class UIResourceViewer extends UIContainer {
	Collection<Attachment> mediaList = new ArrayList<Attachment>()  ;
	String selectedFullUrl_ = null;
	String selectedWebdavUrl_ = null;
	String mimeType_ = null;
	public Collection<Attachment> getMediaList(){
		DataStorage service = EStudyPortlet.getDataService();
		try {
			mediaList = service.getMedias();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mediaList;
	}
	public String getResourceUrl(Attachment att, String type, int w, int h) {
		String id = att.getId().substring(att.getId().lastIndexOf(Util.SLASH) + 1);
		return "/" + PortalContainer.getInstance().getRestContextName() +  "/estudy/api/media/" + id +"?type="+type+"&w="+w+"&h="+h;
	}
	public String getWebDavUrl(Attachment att) {
		PortalRequestContext pContext = org.exoplatform.portal.webui.util.Util.getPortalRequestContext();
		pContext.getRequest().getServerName() ;
		pContext.getRequest().getProtocol();
		pContext.getRequest().getServerPort();
		RepositoryService rservice = (RepositoryService)PortalContainer.getInstance().getComponentInstanceOfType(RepositoryService.class);
		try {
			return  pContext.getRequest().getProtocol().split("/")[0].toLowerCase() + "://" +pContext.getRequest().getServerName()+ ":" + pContext.getRequest().getServerPort()+"/"+ PortalContainer.getCurrentPortalContainerName()+"/"+ PortalContainer.getInstance().getRestContextName() +"/jcr/"+ rservice.getCurrentRepository().getConfiguration().getName() + "/" + rservice.getCurrentRepository().getConfiguration().getDefaultWorkspaceName() + att.getDataPath();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return null;
	}
	static public class DownloadActionListener extends EventListener<UIResourceViewer> {
		@Override
		public void execute(Event<UIResourceViewer> event) throws Exception {
			UIResourceViewer view = event.getSource() ;
			EStudyPortlet portlet = view.getAncestorOfType(EStudyPortlet.class);
			//UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
			//UILessionForm uiLessionForm = uiPopupContainer.addChild(UILessionForm.class, null, null) ;
			//portlet.addPopup(uiLessionForm, 600, 311);
		}
	}

}
