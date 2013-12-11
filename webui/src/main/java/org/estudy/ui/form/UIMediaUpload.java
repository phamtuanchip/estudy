package org.estudy.ui.form;

import org.estudy.learning.model.Attachment;
import org.estudy.learning.storage.DataStorage;
import org.estudy.ui.core.Loader;
import org.estudy.ui.popup.UIPopupComponent;
import org.estudy.ui.portlet.EStudyPortlet;
import org.estudy.ui.view.UIContentViewer;
import org.exoplatform.upload.UploadResource;
import org.exoplatform.web.application.AbstractApplicationMessage;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIComponent;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormMultiValueInputSet;
import org.exoplatform.webui.form.UIFormUploadInput;
import org.exoplatform.webui.form.input.UIUploadInput;

import java.util.Arrays;
import java.util.List;

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

    public UIMediaUpload() {
        UIFormMultiValueInputSet uploads = new UIFormMultiValueInputSet("uploads", "uploads");
        uploads.setType(UIUploadInput.class);
        addChild(uploads);
        //addChild(new UIUploadInput("upload", "upload", Loader.DEFAULT_MAX_UPLOAD_FIELD, Loader.getLimitUploadSize()));
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
        return new String[]{"Save", "Cancel"};
    }

    static public class SaveActionListener extends EventListener<UIMediaUpload> {
        @Override
        public void execute(Event<UIMediaUpload> event) throws Exception {
            UIMediaUpload uiForm = event.getSource();
            UIFormMultiValueInputSet uploads = uiForm.getUIInput("uploads");
            List<UIComponent> children = uploads.getChildren();
            List values = uploads.getValue();
            for (UIComponent com : children) {
                UIUploadInput input = (UIUploadInput) com;
                UploadResource[] uploadResource = input.getUploadResources();
                if (uploadResource == null) {
                    event.getRequestContext().getUIApplication().addMessage(new ApplicationMessage("UIMediaUpload.msg.file-name-error", null));
                    return;
                }
                DataStorage dataStorage = Loader.loadDataService();
                long size = 0;
                for (UploadResource upl : uploadResource) {
                    if (upl != null) {
                        long fileSize = ((long) upl.getUploadedSize());
                        size = size + fileSize;
                        if (size >= 10 * 1024 * 1024) {
                            event.getRequestContext()
                                    .getUIApplication()
                                    .addMessage(new ApplicationMessage("UIMediaUpload.msg.total-attachts-size-over10M",
                                            null,
                                            AbstractApplicationMessage.WARNING));
                            return;
                        }
                        Attachment attachfile = new Attachment();
                        attachfile.setName(upl.getFileName());
                        attachfile.setInputStream(input.getUploadDataAsStream(upl.getUploadId()));
                        attachfile.setMimeType(upl.getMimeType());
                        attachfile.setSize(fileSize);
                        attachfile.setResourceId(upl.getUploadId());
                        String mediaUrl = dataStorage.uploadMedia(attachfile);
                        EStudyPortlet portlet = uiForm.getAncestorOfType(EStudyPortlet.class);
                        UIContentViewer view = (UIContentViewer) portlet.findFirstComponentOfType(UIContentViewer.class);
                        view.refresh();
                        portlet.closePopup();
                    }
                }
            }

        }
    }

    static public class OnchangeActionListener extends EventListener<UIMediaUpload> {
        @Override
        public void execute(Event<UIMediaUpload> event) throws Exception {
            UIMediaUpload uiForm = event.getSource();
        }
    }

    static public class CancelActionListener extends EventListener<UIMediaUpload> {
        @Override
        public void execute(Event<UIMediaUpload> event) throws Exception {
            UIMediaUpload uiForm = event.getSource();
            EStudyPortlet calendarPortlet = uiForm.getAncestorOfType(EStudyPortlet.class);
            calendarPortlet.closePopup();
        }
    }
}
