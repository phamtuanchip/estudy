/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
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
package org.estudy.ui.form;

import java.util.ArrayList;
import java.util.List;

import org.estudy.ui.popup.UIPopupComponent;
import org.estudy.ui.portlet.EStudyPortlet;
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
import org.exoplatform.webui.form.validator.SpecialCharacterValidator;
@ComponentConfig(
		lifecycle = UIFormLifecycle.class,
		template = "system:/groovy/webui/form/UIForm.gtmpl",
		events = {
			@EventConfig(listeners = UILessionForm.SaveActionListener.class),
			@EventConfig(listeners = UILessionForm.OnchangeActionListener.class, phase = Phase.DECODE),
			@EventConfig(listeners = UILessionForm.CancelActionListener.class, phase = Phase.DECODE)
		}
		)
public class UILessionForm extends UIForm implements UIPopupComponent{
	
	public UILessionForm() throws Exception {
		addChild(new UIFormStringInput("title", "title", "").addValidator(SpecialCharacterValidator.class)) ;
	    List<SelectItemOption<String>> types = new ArrayList<SelectItemOption<String>>() ;
	    types.add(new SelectItemOption<String>("", "")) ;
	    types.add(new SelectItemOption<String>("1","category1")) ;
	    types.add(new SelectItemOption<String>("2","category2")) ;
	    UIFormSelectBox type =  new UIFormSelectBox("category", "category", types) ;
	    type.setOnChange("Onchange") ;
	    addChild(type);
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
	    return new String[]{"Search","Cancel"} ;
	  }
	
	static  public class SaveActionListener extends EventListener<UILessionForm> {
		@Override
		public void execute(Event<UILessionForm> event) throws Exception {
			UILessionForm uiForm = event.getSource() ;
		}
	}
	static  public class OnchangeActionListener extends EventListener<UILessionForm> {
		@Override
		public void execute(Event<UILessionForm> event) throws Exception {
			UILessionForm uiForm = event.getSource() ;
		}
	}
	static  public class CancelActionListener extends EventListener<UILessionForm> {
		@Override
		public void execute(Event<UILessionForm> event) throws Exception {
			UILessionForm uiForm = event.getSource() ;
			EStudyPortlet calendarPortlet = uiForm.getAncestorOfType(EStudyPortlet.class) ;
			calendarPortlet.closePopup();
		}
	}
}
