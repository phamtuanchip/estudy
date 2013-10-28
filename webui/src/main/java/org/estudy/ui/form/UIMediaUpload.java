package org.estudy.ui.form;

import org.estudy.learning.model.Attachment;
import org.estudy.learning.storage.DataStorage;
import org.estudy.ui.core.Loader;
import org.estudy.ui.popup.UIPopupComponent;
import org.estudy.ui.portlet.EStudyPortlet;
import org.exoplatform.upload.UploadResource;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.input.UIUploadInput;

/**
 * Created with IntelliJ IDEA.
 * User: tuanp
 * Date: 10/28/13
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */

@ComponentConfig(
        lifecycle = UIFormLifecycle.class,
        template = "system:/groovy/webui/form/UIForm.gtmpl",
        events = {
                @EventConfig(listeners = UIMediaUpload.SaveActionListener.class),
                @EventConfig(listeners = UIMediaUpload.OnchangeActionListener.class, phase = Event.Phase.DECODE),
                @EventConfig(listeners = UIMediaUpload.CancelActionListener.class, phase = Event.Phase.DECODE)
        }
)
public class UIMediaUpload extends UIForm implements UIPopupComponent {

  public UIMediaUpload(){
    addChild(new UIUploadInput("upload", "upload"));
  }
  @Override
  public void activate() throws Exception {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void deActivate() throws Exception {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String[] getActions() {
    return new String[]{"Search","Cancel"} ;
  }

  static  public class SaveActionListener extends EventListener<UIMediaUpload> {
    @Override
    public void execute(Event<UIMediaUpload> event) throws Exception {
      UIMediaUpload uiForm = event.getSource() ;
      UIUploadInput input = uiForm.getUIInput("upload");
      UploadResource[] resource = input.getUploadResources();
      if(resource == null) {
        event.getRequestContext().getUIApplication().addMessage(new ApplicationMessage("UIMediaUpload.msg.file-name-error", null));
        return;
      }
      DataStorage dataStorage = Loader.loadDataService() ;
      Attachment att = new Attachment();
      String mediaUrl =  dataStorage.uploadMedia(att) ;
    }
  }
  static  public class OnchangeActionListener extends EventListener<UIMediaUpload> {
    @Override
    public void execute(Event<UIMediaUpload> event) throws Exception {
      UIMediaUpload uiForm = event.getSource() ;
    }
  }
  static  public class CancelActionListener extends EventListener<UIMediaUpload> {
    @Override
    public void execute(Event<UIMediaUpload> event) throws Exception {
      UIMediaUpload uiForm = event.getSource() ;
      EStudyPortlet calendarPortlet = uiForm.getAncestorOfType(EStudyPortlet.class) ;
      calendarPortlet.closePopup();
    }
  }
}
