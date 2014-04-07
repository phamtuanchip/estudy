
package org.estudy.ui.view;

import java.util.Collection;

import org.estudy.learning.model.ESession;
import org.estudy.learning.storage.DataStorage;
import org.estudy.ui.form.UILessonForm;
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
                 template =  "app:/templates/estudy/webui/UILessonList.gtmpl", 
                 events = {
                     @EventConfig(listeners = UILessonList.AddLessonActionListener.class),
                     @EventConfig(listeners = UILessonList.AddQuestionActionListener.class),
                     @EventConfig(listeners = UILessonList.AddMediaActionListener.class),
                     @EventConfig(listeners = UILessonList.TestActionListener.class),
                     @EventConfig(listeners = UILessonList.ResourceActionListener.class)
                 }
    )

public class UILessonList extends UIContainer {
  private Collection<ESession> list;

  public UILessonList() {
    DataStorage service = EStudyPortlet.getDataService();
    try {
      list = service.getSessions();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Collection<ESession> getList() throws Exception {
	return  EStudyPortlet.getDataService().getSessions();
}

public void setList(Collection<ESession> list) {
	this.list = list;
}

static public class AddLessonActionListener extends EventListener<UILessonList> {
    @Override
    public void execute(Event<UILessonList> event) throws Exception {
      UILessonList listview = event.getSource() ;
      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      UILessonForm uiLessionForm = uiPopupContainer.addChild(UILessonForm.class, null, null) ;
      portlet.addPopup(uiLessionForm, 800, 600);
    }
  }

  static public class AddQuestionActionListener extends EventListener<UILessonList> {
    @Override
    public void execute(Event<UILessonList> event) throws Exception {
      UILessonList listview = event.getSource() ;
      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      UIQuestionForm uiQuestionForm = uiPopupContainer.addChild(UIQuestionForm.class, null, null) ;
      portlet.addPopup(uiQuestionForm, 800, 600);
    }
  }

  static public class AddMediaActionListener extends EventListener<UILessonList> {
    @Override
    public void execute(Event<UILessonList> event) throws Exception {
      UILessonList listview = event.getSource() ;
      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      UIMediaUpload uiQuestionForm = uiPopupContainer.addChild(UIMediaUpload.class, null, null) ;
      portlet.addPopup(uiQuestionForm, 600, 300);
    }
  }

  static public class TestActionListener extends EventListener<UILessonList> {
    @Override
    public void execute(Event<UILessonList> event) throws Exception {
      UILessonList listview = event.getSource() ;
      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
      view.setRenderedChild(UITest.class);
    }
  }
  
  static public class ResourceActionListener extends EventListener<UILessonList> {
	    @Override
	    public void execute(Event<UILessonList> event) throws Exception {
	      UILessonList listview = event.getSource() ;
	      EStudyPortlet portlet = listview.getAncestorOfType(EStudyPortlet.class);
	      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
	      view.setRenderedChild(UIResourceViewer.class);
	    }
	  }
}
