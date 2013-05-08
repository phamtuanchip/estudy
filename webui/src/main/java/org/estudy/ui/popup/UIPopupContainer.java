/**
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 **/
package org.estudy.ui.popup;

import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIContainer;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.lifecycle.UIContainerLifecycle;

/**
 * Created by The eXo Platform SARL
 * Author : Pham Tuan
 *          tuan.pham@exoplatform.com
 * Aug 29, 2007  
 */
@ComponentConfig(lifecycle = UIContainerLifecycle.class)
public class UIPopupContainer extends UIContainer implements UIPopupComponent {

  public static String UITASKPOPUP = "UIPopupAddTaskContainer".intern() ;
  public static String UIEVENTPOPUP = "UIPopupAddEventContainer".intern();
  public static String UICALENDARPOPUP = "uiPopupAddCalendarContainer".intern();
  public static String UICALENDAR_SETTING_POPUP = "UIPopupCalendarSettingContainer".intern();
  public UIPopupContainer() throws Exception {
    UIPopupAction uiPopupAction = addChild(UIPopupAction.class, null, "UICalendarChildPopup");
    uiPopupAction.getChild(UIPopupWindow.class).setId("UICalendarChildPopupWindow") ;
    
  }
  @Override
  public void activate() throws Exception {
  }

  @Override
  public void deActivate() throws Exception {
    UIPopupAction uiPopupAction = getChild(UIPopupAction.class) ;
    uiPopupAction.deActivate() ;
    WebuiRequestContext context = RequestContext.getCurrentInstance() ;
    context.addUIComponentToUpdateByAjax(uiPopupAction) ;
  }
}
