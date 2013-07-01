 
package org.estudy.ui.portlet;
 
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;
import org.exoplatform.container.PortalContainer;
import org.estudy.service.DataStorage;
 import org.estudy.service.impl.JcrDataStorage;
 


@ComponentConfig(
		lifecycle = UIApplicationLifecycle.class,
		template  = "app:/templates/webui/eportlet.gtmpl"
		)
public class Eportlet extends UIPortletApplication 
{ 
	public Eportlet() throws Exception 
	{
		DataStorage dataService = (DataStorage) PortalContainer.getInstance().getComponentInstanceOfType(JcrDataStorage.class);
		dataService.saveData();
		dataService.getData();
	}


	 
}
