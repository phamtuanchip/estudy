
package org.estudy.ui.view;

import java.util.Collection;

import org.estudy.learning.model.ESession;
import org.estudy.learning.storage.DataStorage;
import org.estudy.ui.form.UILessionForm;
import org.estudy.ui.form.UIMediaUpload;
import org.estudy.ui.form.UIQuestionForm;
import org.estudy.ui.popup.UIPopupContainer;
import org.estudy.ui.portlet.EStudyPortlet;
import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;

@ComponentConfig(
                 template =  "app:/templates/estudy/webui/UILessionList.gtmpl", 
                 events = {
                     @EventConfig(listeners = UILessionList.AddLessionActionListener.class),
                     @EventConfig(listeners = UILessionList.AddQuestionActionListener.class),
                     @EventConfig(listeners = UILessionList.AddMediaActionListener.class),
                     @EventConfig(listeners = UILessionList.TestActionListener.class)
                 }
    )

public class UILessionList extends UIContainer {
  Collection<ESession> list;

  public UILessionList() {
    DataStorage service = EStudyPortlet.getDataService();
    try {
      list = service.getSessions();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static public class AddLessionActionListener extends EventListener<UILessionList> {
    @Override
    public void execute(Event<UILessionList> event) throws Exception {
      UILessionList listview = event.getSource() ;
      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      UILessionForm uiLessionForm = uiPopupContainer.addChild(UILessionForm.class, null, null) ;
      portlet.addPopup(uiLessionForm, 600, 311);
    }
  }

  static public class AddQuestionActionListener extends EventListener<UILessionList> {
    @Override
    public void execute(Event<UILessionList> event) throws Exception {
      UILessionList listview = event.getSource() ;
      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      UIQuestionForm uiQuestionForm = uiPopupContainer.addChild(UIQuestionForm.class, null, null) ;
      portlet.addPopup(uiQuestionForm, 800, 600);
    }
  }

  static public class AddMediaActionListener extends EventListener<UILessionList> {
    @Override
    public void execute(Event<UILessionList> event) throws Exception {
      UILessionList listview = event.getSource() ;
      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      UIMediaUpload uiQuestionForm = uiPopupContainer.addChild(UIMediaUpload.class, null, null) ;
      portlet.addPopup(uiQuestionForm, 600, 311);
    }
  }

  static public class TestActionListener extends EventListener<UILessionList> {
    @Override
    public void execute(Event<UILessionList> event) throws Exception {
      UILessionList listview = event.getSource() ;
      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
      view.getChild(UITest.class).setRendered(!view.getChild(UITest.class).isRendered());

    }
  }
}
