
package org.estudy.ui.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.estudy.learning.model.ESession;
import org.estudy.ui.core.UICaptcha;
import org.estudy.ui.core.UIDateInput;
import org.estudy.ui.core.UIFormRichtextInput;
import org.estudy.ui.core.UISearchInput;
import org.estudy.ui.popup.UIPopupComponent;
import org.estudy.ui.portlet.EStudyPortlet;
import org.estudy.ui.view.UILessonList;
import org.exoplatform.portal.webui.CaptchaValidator;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.core.model.SelectItemOption;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.Event.Phase;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormSelectBox;
import org.exoplatform.webui.form.UIFormStringInput;
import org.exoplatform.webui.form.validator.MandatoryValidator;
import org.exoplatform.webui.form.validator.SpecialCharacterValidator;
@ComponentConfig(
		lifecycle = UIFormLifecycle.class,
		template = "system:/groovy/webui/form/UIForm.gtmpl",
		events = {
			@EventConfig(listeners = UILessonForm.SaveActionListener.class),
			@EventConfig(listeners = UILessonForm.OnchangeActionListener.class, phase = Phase.DECODE),
			@EventConfig(listeners = UILessonForm.CancelActionListener.class, phase = Phase.DECODE)
		}
		)
public class UILessonForm extends UIForm implements UIPopupComponent{
	
	public UILessonForm() throws Exception {
		addChild(new UIFormStringInput("title", "title", "").addValidator(SpecialCharacterValidator.class).addValidator(MandatoryValidator.class)) ;
	    List<SelectItemOption<String>> types = new ArrayList<SelectItemOption<String>>() ;
	    types.add(new SelectItemOption<String>("select category", "0")) ;
	    types.add(new SelectItemOption<String>("category1","1")) ;
	    types.add(new SelectItemOption<String>("category2","2")) ;
	    UIFormSelectBox type =  new UIFormSelectBox("category", "category", types) ;
	    type.setOnChange("Onchange") ;
	    addChild(type);
	    //addChild(new UICaptcha("simpleCaptcha", "simpleCaptcha", null));
	    addUIFormInput(new UICaptcha("simpleCaptcha", "simpleCaptcha", null).addValidator(MandatoryValidator.class).addValidator(CaptchaValidator.class));
	    addChild(new UIDateInput("date", "date", new Date()));     
	    addChild(new UIFormRichtextInput("description", "description", ""));
	    addChild(new UISearchInput("search", "search", null));
	}

	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deActivate() throws Exception {
		// TODO Auto-generated method stub

	}
	
	@Override
	  public String[] getActions() {
	    return new String[]{"Save","Cancel"} ;
	  }
	
	static  public class SaveActionListener extends EventListener<UILessonForm> {
		@Override
		public void execute(Event<UILessonForm> event) throws Exception {
			UILessonForm uiForm = event.getSource() ;
			ESession es = new ESession();
			es.setTitle( 
			uiForm.getUIStringInput("title").getValue());
			es.setCat(uiForm.getUIFormSelectBox("category").getValue());
			es.setDec(((UIFormRichtextInput)uiForm.getUIInput("description")).getValue());
			es.setQuest(new ArrayList<String>());
			EStudyPortlet.getDataService().saveSession(es, true);
			EStudyPortlet calendarPortlet = uiForm.getAncestorOfType(EStudyPortlet.class) ;
			calendarPortlet.closePopup();
			event.getRequestContext().addUIComponentToUpdateByAjax(
			uiForm.getAncestorOfType(EStudyPortlet.class).findFirstComponentOfType(UILessonList.class));
			
		}
	}
	static  public class OnchangeActionListener extends EventListener<UILessonForm> {
		@Override
		public void execute(Event<UILessonForm> event) throws Exception {
			UILessonForm uiForm = event.getSource() ;
		}
	}
	static  public class CancelActionListener extends EventListener<UILessonForm> {
		@Override
		public void execute(Event<UILessonForm> event) throws Exception {
			UILessonForm uiForm = event.getSource() ;
			EStudyPortlet calendarPortlet = uiForm.getAncestorOfType(EStudyPortlet.class) ;
			calendarPortlet.closePopup();
		}
	}
}
