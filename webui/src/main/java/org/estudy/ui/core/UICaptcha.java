
package org.estudy.ui.core;

import java.util.Calendar;

import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.form.UIFormStringInput;


public class UICaptcha extends UIFormStringInput {
	

    public UICaptcha(String name, String bindingExpression, String value) {
        super(name, bindingExpression, value);
    }

    public void processRender(WebuiRequestContext context) throws Exception {
        RenderResponse resp = context.getResponse();
        ResourceURL url = resp.createResourceURL();
        String random = "&v=" + Calendar.getInstance().getTimeInMillis();
        context.getWriter().write("<div id='" + getId() + "'><img src=\"" + url.toString() + random + "\" /><br/>");
        super.processRender(context);
        context.getWriter().write("</div>");
    }

}
