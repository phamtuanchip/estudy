 
package org.estudy.ui.portlet;
 
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;


@ComponentConfig(
		lifecycle = UIApplicationLifecycle.class,
		template  = "app:/templates/webui/eportlet.gtmpl"
		)
public class Eportlet extends UIPortletApplication 
{ 
	public Eportlet() throws Exception 
	{
		 
	}


	 
}
