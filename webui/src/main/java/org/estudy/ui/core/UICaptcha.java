/******************************************************************************
 * JBoss by Red Hat                                                           *
 * Copyright 2010, Red Hat Middleware, LLC, and individual                    *
 * contributors as indicated by the @authors tag. See the                     *
 * copyright.txt in the distribution for a full listing of                    *
 * individual contributors.                                                   *
 *                                                                            *
 * This is free software; you can redistribute it and/or modify it            *
 * under the terms of the GNU Lesser General Public License as                *
 * published by the Free Software Foundation; either version 2.1 of           *
 * the License, or (at your option) any later version.                        *
 *                                                                            *
 * This software is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU           *
 * Lesser General Public License for more details.                            *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public           *
 * License along with this software; if not, write to the Free                *
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA         *
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.                   *
 ******************************************************************************/
package org.estudy.ui.core;

import java.util.Calendar;

import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.form.UIFormStringInput;

/**
 * @author <a href="mailto:theute@redhat.com">Thomas Heute</a>
 * @version $Revision$
 */
public class UICaptcha extends UIFormStringInput {
	

    public UICaptcha(String name, String bindingExpression, String value) {
        super(name, bindingExpression, value);
    }

    public void processRender(WebuiRequestContext context) throws Exception {

        RenderResponse resp = context.getResponse();

        //
        ResourceURL url = resp.createResourceURL();

        // context.getPortalContextPath() + "/captcha?v=" + Calendar.getInstance().getTimeInMillis()

        String random = "&v=" + Calendar.getInstance().getTimeInMillis();

        context.getWriter().write("<div id='" + getId() + "'><img src=\"" + url.toString() + random + "\" /><br/>");
        super.processRender(context);
        context.getWriter().write("</div>");
    }

}
