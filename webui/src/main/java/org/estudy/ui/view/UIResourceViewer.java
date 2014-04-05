package org.estudy.ui.view;

import java.util.ArrayList;
import java.util.Collection;

import org.estudy.learning.model.Attachment;
import org.estudy.learning.storage.DataStorage;
import org.estudy.ui.portlet.EStudyPortlet;
import org.exoplatform.portal.webui.container.UIContainer;
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
	public Collection<Attachment> getMediaList(){
		DataStorage service = EStudyPortlet.getDataService();
		try {
			mediaList = service.getMedias();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mediaList;
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
