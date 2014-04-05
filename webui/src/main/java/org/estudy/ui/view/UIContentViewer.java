package org.estudy.ui.view;

import org.estudy.learning.model.Attachment;
import org.estudy.learning.model.ESession;
import org.estudy.learning.storage.DataStorage;
import org.estudy.ui.form.UILessonForm;
import org.estudy.ui.popup.UIPopupContainer;
import org.estudy.ui.portlet.EStudyPortlet;
import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: tuanp
 * Date: 10/22/13
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
@ComponentConfig(
        template =  "app:/templates/estudy/webui/UIContentViewer.gtmpl",
        events = {
                @EventConfig(listeners = UIContentViewer.AddLessonActionListener.class)

        }
)

public class UIContentViewer extends UIContainer {
  Collection<ESession> list;
  Collection<Attachment> mediaList = new ArrayList<Attachment>() ;
  public UIContentViewer() throws Exception {
    addChild(UITest.class, null, null).setRendered(false) ;
    addChild(UIResourceViewer.class, null, null).setRendered(false) ;
    refresh();
  }

  public void refresh(){
    DataStorage service = EStudyPortlet.getDataService();
    try {
      list = service.getSessions();
      mediaList.addAll(service.getMedias());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  static public class AddLessonActionListener extends EventListener<UIContentViewer> {
    @Override
    public void execute(Event<UIContentViewer> event) throws Exception {
      UIContentViewer view = event.getSource() ;
      EStudyPortlet portlet = view.getAncestorOfType(EStudyPortlet.class);
      //UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      //UILessionForm uiLessionForm = uiPopupContainer.addChild(UILessionForm.class, null, null) ;
      //portlet.addPopup(uiLessionForm, 600, 311);
    }
  }
}
